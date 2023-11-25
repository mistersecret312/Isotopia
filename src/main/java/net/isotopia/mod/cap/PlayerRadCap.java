package net.isotopia.mod.cap;

import net.isotopia.mod.helper.*;
import net.isotopia.mod.item.IsotopicItem;
import net.isotopia.mod.network.IsoNetwork;
import net.isotopia.mod.network.RadiationPacket;
import net.isotopia.mod.tile.IsotopeTile;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import java.util.List;

public class PlayerRadCap implements IPlayerRad {

    private PlayerEntity player;
    public boolean hasCheckedDose = false;
    private double dose = 0;
    private double localDose = 0;

    public PlayerRadCap(PlayerEntity ent) {
        this.player = ent;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putDouble("dose", this.getDose());
        tag.putDouble("dose_rates", this.momentDose());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.dose = nbt.getDouble("dose");
        this.localDose = nbt.getDouble("dose_rates");

    }

    @Override
    public void tick() {
        boolean hasRadioactiveBlocks = false;
        boolean hasRadioactiveItems = false;
        World world = player.getEntityWorld();
        if(world.getGameTime() % 20 == 0) {
            if (player != null && !world.isRemote()) {

                AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ)).grow(12);

                for (BlockPos blockPos : BlockPos.getAllInBoxMutable((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ, (int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ)) {
                    if (world.getBlockState(blockPos).getBlock() instanceof IRadioactive) {
                        Block foundBlock = world.getBlockState(blockPos).getBlock();
                        RadioactiveProperties radioactiveBlock = ((IRadioactive) foundBlock).getRadProps();

                        double distance = player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));

                        dose += (radioactiveBlock.getActivityAlpha() * radioactiveBlock.getEnergyPerDecayAlpha()) / (4 * Math.PI * distance);
                        dose += (radioactiveBlock.getActivityBeta() * radioactiveBlock.getEnergyPerDecayBeta()) / (4 * Math.PI * distance);
                        dose += (radioactiveBlock.getActivityGamma() * radioactiveBlock.getEnergyPerDecayGamma()) / (4 * Math.PI * distance);

                        RadioactiveProperties fromArmor = RadUtils.getArmorProtectionFromRadiation(player);
                        dose -= (fromArmor.getActivityAlpha()* fromArmor.getEnergyPerDecayAlpha())/(4*Math.PI);
                        dose -= (fromArmor.getActivityBeta()* fromArmor.getEnergyPerDecayBeta())/(4*Math.PI);
                        dose -= (fromArmor.getActivityGamma()* fromArmor.getEnergyPerDecayGamma())/(4*Math.PI);
                        hasRadioactiveBlocks = true;
                    }
                    if(world.getBlockState(blockPos).getBlock() instanceof IIsotopic) {
                        Block foundBlock = world.getBlockState(blockPos).getBlock();
                        List<IsotopeData> data = ((IIsotopic)foundBlock).getIsotopicData();
                        data.forEach(isotopeData -> {
                            dose += ((isotopeData.radioactiveProperties.getActivityAlpha()*isotopeData.radioactiveProperties.getEnergyPerDecayAlpha())/ (8*Math.PI));
                            dose += ((isotopeData.radioactiveProperties.getActivityBeta()*isotopeData.radioactiveProperties.getEnergyPerDecayBeta())/ (8*Math.PI));
                            dose += ((isotopeData.radioactiveProperties.getActivityGamma()*isotopeData.radioactiveProperties.getEnergyPerDecayGamma())/ (8*Math.PI));
                        });
                    }
                }
                PlayerInventory playerInventory = player.inventory;
                for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
                    ItemStack item = playerInventory.getStackInSlot(slot);
                    int count = item.getCount();
                    if (item.getItem() instanceof IRadioactive) {
                        RadioactiveProperties foundItem = ((IRadioactive) item.getItem()).getRadProps();
                        dose += ((foundItem.getActivityAlpha() * foundItem.getEnergyPerDecayAlpha()) / (8 * Math.PI))*count;
                        dose += ((foundItem.getActivityBeta() * foundItem.getEnergyPerDecayBeta()) / (8 * Math.PI))*count;
                        dose += ((foundItem.getActivityGamma() * foundItem.getEnergyPerDecayGamma()) / (8 * Math.PI))*count;
                        hasRadioactiveItems = true;
                    }
                    if(item.getItem() instanceof IsotopicItem){
                        IsotopicItem itom = (IsotopicItem) item.getItem();
                        itom.getIsotopicData().forEach(isotopeData -> {
                            dose += ((isotopeData.radioactiveProperties.getActivityAlpha()*isotopeData.radioactiveProperties.getEnergyPerDecayAlpha())/ (8*Math.PI))*count;
                            dose += ((isotopeData.radioactiveProperties.getActivityBeta()*isotopeData.radioactiveProperties.getEnergyPerDecayBeta())/ (8*Math.PI))*count;
                            dose += ((isotopeData.radioactiveProperties.getActivityGamma()*isotopeData.radioactiveProperties.getEnergyPerDecayGamma())/ (8*Math.PI))*count;
                        });
                    }
                }
            }

            if(!hasRadioactiveBlocks && !hasRadioactiveItems){
                dose -= 10;
            }

            if(dose < 0){
                dose = 0;
            }

            if(this.hasCheckedDose){
                this.hasCheckedDose = false;
                this.localDose = 0;
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
        return this.dose/(20*1600);
    }

    public double momentDose(){
        World world = this.player.getEntityWorld();
        if (player != null && !world.isRemote()) {

            AxisAlignedBB aabb = new AxisAlignedBB(new BlockPos(player.lastTickPosX, player.lastTickPosY, player.lastTickPosZ)).grow(12);

            for (BlockPos blockPos : BlockPos.getAllInBoxMutable((int) aabb.minX, (int) aabb.minY, (int) aabb.minZ, (int) aabb.maxX, (int) aabb.maxY, (int) aabb.maxZ)) {
                if (world.getBlockState(blockPos).getBlock() instanceof IRadioactive) {
                    Block foundBlock = world.getBlockState(blockPos).getBlock();
                    RadioactiveProperties radioactiveBlock = ((IRadioactive) foundBlock).getRadProps();

                    double distance = player.getDistanceSq(new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));

                    localDose += (radioactiveBlock.getActivityAlpha() * radioactiveBlock.getEnergyPerDecayAlpha()) / (4 * Math.PI * distance);
                    localDose += (radioactiveBlock.getActivityBeta() * radioactiveBlock.getEnergyPerDecayBeta()) / (4 * Math.PI * distance);
                    localDose += (radioactiveBlock.getActivityGamma() * radioactiveBlock.getEnergyPerDecayGamma()) / (4 * Math.PI * distance);

                    RadioactiveProperties fromArmor = RadUtils.getArmorProtectionFromRadiation(player);
                    localDose -= (fromArmor.getActivityAlpha()* fromArmor.getEnergyPerDecayAlpha())/(4*Math.PI);
                    localDose -= (fromArmor.getActivityBeta()* fromArmor.getEnergyPerDecayBeta())/(4*Math.PI);
                    localDose -= (fromArmor.getActivityGamma()* fromArmor.getEnergyPerDecayGamma())/(4*Math.PI);
                }
                if(world.getBlockState(blockPos).getBlock() instanceof IIsotopic) {
                    Block foundBlock = world.getBlockState(blockPos).getBlock();
                    List<IsotopeData> data = ((IIsotopic)foundBlock).getIsotopicData();
                    data.forEach(isotopeData -> {
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityAlpha()*isotopeData.radioactiveProperties.getEnergyPerDecayAlpha())/ (8*Math.PI));
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityBeta()*isotopeData.radioactiveProperties.getEnergyPerDecayBeta())/ (8*Math.PI));
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityGamma()*isotopeData.radioactiveProperties.getEnergyPerDecayGamma())/ (8*Math.PI));
                    });
                }
            }
            PlayerInventory playerInventory = player.inventory;
            for (int slot = 0; slot < playerInventory.getSizeInventory(); slot++) {
                ItemStack item = playerInventory.getStackInSlot(slot);
                int count = item.getCount();
                if (item.getItem() instanceof IRadioactive) {
                    RadioactiveProperties foundItem = ((IRadioactive) item.getItem()).getRadProps();
                    localDose += ((foundItem.getActivityAlpha() * foundItem.getEnergyPerDecayAlpha()) / (8 * Math.PI))*count;
                    localDose += ((foundItem.getActivityBeta() * foundItem.getEnergyPerDecayBeta()) / (8 * Math.PI))*count;
                    localDose += ((foundItem.getActivityGamma() * foundItem.getEnergyPerDecayGamma()) / (8 * Math.PI))*count;
                }
                if(item.getItem() instanceof IIsotopic){
                    IIsotopic itom = (IIsotopic) item.getItem();
                    itom.getIsotopicData().forEach(isotopeData -> {
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityAlpha()*isotopeData.radioactiveProperties.getEnergyPerDecayAlpha())/ (8*Math.PI))*count;
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityBeta()*isotopeData.radioactiveProperties.getEnergyPerDecayBeta())/ (8*Math.PI))*count;
                        localDose += (isotopeData.percentage/100) * ((isotopeData.radioactiveProperties.getActivityGamma()*isotopeData.radioactiveProperties.getEnergyPerDecayGamma())/ (8*Math.PI))*count;
                    });
                }
            }
        }
        this.hasCheckedDose = true;
        return localDose/(20*1600);
    }
}
