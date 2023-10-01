package net.isotopia.mod.helper;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public interface IRadioactive {

    int getActivityAlpha();
    int getActivityBeta();
    int getActivityGamma();

    void setActivityAlpha(int a);
    void setActivityBeta(int b);
    void setActivityGamma(int g);

    double getEnergyPerDecayAlpha();
    double getEnergyPerDecayBeta();
    double getEnergyPerDecayGamma();

    void setEnergyPerDecayAlpha(double a);
    void setEnergyPerDecayBeta(double b);
    void setEnergyPerDecayGamma(double g);


}
