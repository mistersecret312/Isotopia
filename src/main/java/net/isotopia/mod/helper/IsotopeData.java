package net.isotopia.mod.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class IsotopeData implements INBTSerializable<CompoundNBT> {

    public RadioactiveProperties radioactiveProperties;
    public double percentage;

    public IsotopeData(double percentage, RadioactiveProperties properties){
        this.radioactiveProperties = properties;
        this.percentage = percentage;
    }

    public IsotopeData(CompoundNBT nbt){
        this.percentage = nbt.getDouble("percentage");
        int BqA = nbt.getInt("BqA");
        int BqB = nbt.getInt("BqB");
        int BqG = nbt.getInt("BqG");
        double MeVA = nbt.getDouble("MeVA");
        double MeVB = nbt.getDouble("MeVB");
        double MeVG = nbt.getDouble("MeVG");

        this.radioactiveProperties = new RadioactiveProperties(BqA, BqB, BqG, MeVA, MeVB, MeVG);
    }

    public static IsotopeData read(CompoundNBT compound) {
        return new IsotopeData(compound);
    }

    public double getPercentage() {
        return percentage;
    }

    public RadioactiveProperties getRadioactiveProperties() {
        return radioactiveProperties;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setRadioactiveProperties(RadioactiveProperties radioactiveProperties) {
        this.radioactiveProperties = radioactiveProperties;
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("percentage", percentage);
        nbt.putInt("BqA", radioactiveProperties.getActivityAlpha());
        nbt.putInt("BqB", radioactiveProperties.getActivityBeta());
        nbt.putInt("BqG", radioactiveProperties.getActivityGamma());
        nbt.putDouble("MeVA", radioactiveProperties.getEnergyPerDecayAlpha());
        nbt.putDouble("MeVB", radioactiveProperties.getEnergyPerDecayBeta());
        nbt.putDouble("MeVG", radioactiveProperties.getEnergyPerDecayGamma());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setPercentage(nbt.getDouble("percentage"));
        int BqA = nbt.getInt("BqA");
        int BqB = nbt.getInt("BqB");
        int BqG = nbt.getInt("BqG");
        double MeVA = nbt.getDouble("MeVA");
        double MeVB = nbt.getDouble("MeVB");
        double MeVG = nbt.getDouble("MeVG");

        setRadioactiveProperties(new RadioactiveProperties(BqA, BqB, BqG, MeVA, MeVB, MeVG));
    }
}
