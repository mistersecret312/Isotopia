package net.isotopia.mod.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class IsotopeData {

    public RadioactiveProperties radioactiveProperties;
    public double percentage;

    public IsotopeData(double percentage, RadioactiveProperties properties){
        this.radioactiveProperties = properties;
        this.percentage = percentage;
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

    public static IsotopeData deserializeNBT(CompoundNBT nbt)
    {
        IsotopeData data = new IsotopeData(nbt.getDouble("percentage"), new RadioactiveProperties(nbt.getInt("BqA"), nbt.getInt("BqB"), nbt.getInt("BqG"), nbt.getDouble("MeVA"), nbt.getDouble("MeVB"), nbt.getDouble("MeVG")));
        return data;
    }
}
