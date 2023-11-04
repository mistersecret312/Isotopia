package net.isotopia.mod.cap;

import net.isotopia.mod.block.ModBlocks;
import net.isotopia.mod.helper.IRadioactive;
import net.isotopia.mod.helper.RadUtils;
import net.isotopia.mod.network.IsoNetwork;
import net.isotopia.mod.network.RadiationPacket;
import net.minecraft.block.Block;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestData;
import net.minecraft.world.World;

public class PlayerRadCap implements IPlayerRad {

    private PlayerEntity player;
    private double dose = 0;
    private double doserateA = 0;
    private double doserateB = 0;
    private double doserateG = 0;

    public PlayerRadCap(PlayerEntity ent) {
        this.player = ent;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putDouble("dose", this.dose);
        tag.putDouble("dose_rate_alpha", this.doserateA);
        tag.putDouble("dose_rate_beta", this.doserateB);
        tag.putDouble("dose_rate_gamma", this.doserateG);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.dose = nbt.getDouble("dose");
        this.doserateA = nbt.getDouble("dose_rate_alpha");
        this.doserateB = nbt.getDouble("dose_rate_beta");
        this.doserateG = nbt.getDouble("dose_rate_gamma");

    }

    @Override
    public void tick() {
        boolean hasRadioactiveBlocks = false;
        World world = player.getEntityWorld();
        if(world.getGameTime() % 20 == 0) {
            if (player != null && !world.isRemote()) {

                dose += (doserateA + doserateB + doserateG) / (80 * 800);

                AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ)).grow(12);

                for (BlockPos blockPos : BlockPos.getAllInBoxMutable((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ, (int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ)) {
                    if (world.getBlockState(blockPos).getBlock() instanceof IRadioactive) {
                        Block foundBlock = world.getBlockState(blockPos).getBlock();
                        IRadioactive radioactiveBlock = (IRadioactive) foundBlock;

                        double distance = player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));

                        doserateA += (radioactiveBlock.getActivityAlpha() * radioactiveBlock.getEnergyPerDecayAlpha()) / (4 * Math.PI * distance);
                        doserateB += (radioactiveBlock.getActivityBeta() * radioactiveBlock.getEnergyPerDecayBeta()) / (4 * Math.PI * distance);
                        doserateG += (radioactiveBlock.getActivityGamma() * radioactiveBlock.getEnergyPerDecayGamma()) / (4 * Math.PI * distance);

                        RadioactiveProperties fromArmor = RadUtils.getArmorProtectionFromRadiation(player);
                        doserateA -= (fromArmor.getActivityAlpha()* fromArmor.getEnergyPerDecayAlpha())/(4*Math.PI);
                        doserateB -= (fromArmor.getActivityBeta()* fromArmor.getEnergyPerDecayBeta())/(4*Math.PI);
                        doserateG -= (fromArmor.getActivityGamma()* fromArmor.getEnergyPerDecayGamma())/(4*Math.PI);
                        hasRadioactiveBlocks = true;
                    }
                }
                for(ItemStack item : player.inventory.mainInventory){
                    if(item.getItem() instanceof IRadioactive){
                        IRadioactive foundItem = (IRadioactive) item.getItem();

                        doserateA += (foundItem.getActivityAlpha() * foundItem.getEnergyPerDecayAlpha()) / (8 * Math.PI );
                        doserateB += (foundItem.getActivityBeta() * foundItem.getEnergyPerDecayBeta()) / (8 * Math.PI );
                        doserateG += (foundItem.getActivityGamma() * foundItem.getEnergyPerDecayGamma()) / (8 * Math.PI );
                    }
                }

            }

            if(!hasRadioactiveBlocks){
                doserateA = 0;
                doserateB = 0;
                doserateB = 0;
                dose -= 10;
            }

            if(dose < 0){
                dose = 0;
            }

            update();
        }
    }

    @Override
    public void update() {
        if (!player.world.isRemote)
            IsoNetwork.sendTo(new RadiationPacket(player.getEntityId(), this.serializeNBT()), (ServerPlayerEntity) player);
    }


    @Override
    public double getDose(){
        return this.dose;
    }

    @Override
    public double getDosingRate(){
        return (this.doserateA+this.doserateB+this.doserateG)/(20*800);
    }
}
