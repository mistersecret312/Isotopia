package net.isotopia.mod.cap;

import net.isotopia.mod.block.ModBlocks;
import net.isotopia.mod.helper.IRadioactive;
import net.minecraft.block.Block;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterestData;
import net.minecraft.world.World;

public class PlayerRadCap implements IPlayerRad {

    private PlayerEntity player;
    private int dose = 0;
    private int doserateA = 0;
    private int doserateB = 0;
    private int doserateG = 0;

    public PlayerRadCap(PlayerEntity ent) {
        this.player = ent;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("dose", this.dose);
        tag.putInt("dose_rate_alpha", this.doserateA);
        tag.putInt("dose_rate_beta", this.doserateB);
        tag.putInt("dose_rate_gamma", this.doserateG);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.dose = nbt.getInt("dose");
        this.doserateA = nbt.getInt("dose_rate_alpha");
        this.doserateB = nbt.getInt("dose_rate_beta");
        this.doserateG = nbt.getInt("dose_rate_gamma");

    }

    @Override
    public void tick() {
        World world = player.getEntityWorld();
        if (player instanceof ServerPlayerEntity && !world.isRemote()) {

            AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ)).grow(8);

            for (BlockPos blockPos : BlockPos.getAllInBoxMutable((int) aabb.minX,(int) aabb.minY,(int) aabb.minZ,(int) aabb.maxX,(int) aabb.maxY,(int) aabb.maxZ)) {
                if(world.getBlockState(blockPos).getBlock() instanceof IRadioactive) {
                    Block foundblock = world.getBlockState(blockPos).getBlock();
                    doserateA = (int) ((((IRadioactive)foundblock).getActivityAlpha() * ((IRadioactive)foundblock).getEnergyPerDecayAlpha()) / (4*Math.PI*(player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ())))));
                    doserateB = (int) ((((IRadioactive)foundblock).getActivityBeta() * ((IRadioactive)foundblock).getEnergyPerDecayBeta()) / (4*Math.PI*(player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ())))));
                    doserateG = (int) ((((IRadioactive)foundblock).getActivityGamma() * ((IRadioactive)foundblock).getEnergyPerDecayGamma()) / (4*Math.PI*(player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ())))));

                    dose += doserateA * 20;
                    dose += doserateB;
                    dose += doserateG;
                }
            }

        }
    }

    @Override
    public void update() {

        if(dose > 10000 && player.experience > 100){
            player.experience -= 1;
        }
    }
}
