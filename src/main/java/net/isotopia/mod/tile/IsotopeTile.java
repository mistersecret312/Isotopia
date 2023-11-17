package net.isotopia.mod.tile;

import com.google.common.collect.Lists;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class IsotopeTile extends TileEntity {

    private List<IsotopeData> iso_data = Lists.newArrayList(new IsotopeData(100, new RadioactiveProperties(0,0,0,0,0,0)), new IsotopeData(100, new RadioactiveProperties(0,0,0,0,0,0)));

    public IsotopeTile() {
        super(IsoTiles.ISO.get());
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);

        ListNBT isotopes = compound.getList("isotopes_initial", Constants.NBT.TAG_COMPOUND);
        this.iso_data.clear();
        for(INBT iso : isotopes) {
            this.iso_data.add(IsotopeData.deserializeNBT((CompoundNBT) iso));
        }
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ListNBT isotopes = new ListNBT();
        for(IsotopeData iso : this.iso_data){
            isotopes.add(iso.serializeNBT());
        }
        compound.put("isotopes_initial", isotopes);
        return super.write(compound);
    }

    public void setData(List<IsotopeData> data) {
        this.iso_data = data;
    }

    public List<IsotopeData> getData() {
        return this.iso_data;
    }

    public void update() {
        if(!world.isRemote)
            world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 3);
    }

}