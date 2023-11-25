package net.isotopia.mod.tile;

import com.google.common.collect.Lists;
import net.isotopia.mod.block.FissileBlock;
import net.isotopia.mod.block.GlowingRadioactiveBlock;
import net.isotopia.mod.block.IsotopicBlock;
import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class IsotopeTile extends TileEntity implements ITickableTileEntity, IIsotopic {

    private List<IsotopeData> iso_data = new ArrayList<>(8);
    public IsotopeTile() {
        super(IsoTiles.ISO.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);

        ListNBT isotopes = compound.getList("isotopes", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < isotopes.size(); ++i) {
            CompoundNBT compoundnbt = isotopes.getCompound(i);
            this.iso_data.set(i, IsotopeData.read(compoundnbt));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ListNBT isotopes = new ListNBT();
        for(int i = 0; i < this.iso_data.size(); ++i) {
            CompoundNBT compoundnbt = iso_data.get(i).serializeNBT();
            isotopes.set(i, compoundnbt);
        }
        compound.put("isotopes", isotopes);
        return compound;
    }

    public void setIsotopicData(List<IsotopeData> data) {
        this.iso_data = data;
    }

    public List<IsotopeData> getIsotopicData() {
        return this.iso_data;
    }

    public void update() {
        if(!world.isRemote)
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    public void tick() {
        if (!this.getWorld().isRemote) {
            if (this.getBlockState().getBlock() instanceof IsotopicBlock) {
                this.iso_data = ((IsotopicBlock) this.getBlockState().getBlock()).getIsotopicData();
            }
            if(this.getBlockState().getBlock() instanceof GlowingRadioactiveBlock){
                GlowingRadioactiveBlock block = (GlowingRadioactiveBlock) this.getBlockState().getBlock();
                if(block.getIsotopicData().get(0).getPercentage() > 0.9f) {
                    block.setGlow(getBlockState(), world, pos, true);
                }
            }
            if(this.getBlockState().getBlock() instanceof FissileBlock){
                FissileBlock block = (FissileBlock) this.getBlockState().getBlock();
                if(block.getIsotopicData().get(0).getPercentage() > 90.0f) {
                    block.setFissile(getBlockState(), world, pos, true);
                }
            }
        }
    }
}
