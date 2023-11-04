package net.isotopia.mod.item;

import net.isotopia.mod.cap.RadioactiveProperties;
import net.isotopia.mod.helper.IRadioactive;
import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;

public class RadioactiveItemBase extends Item implements IRadioactive {

    private int activityA;
    private int activityB;
    private int activityG;
    private double energyperdecayA;
    private double energyperdecayB;
    private double energyperdecayG;

    public RadioactiveItemBase(Properties properties, RadioactiveProperties radioactiveProperties) {
        super(properties);
        this.activityA = radioactiveProperties.getActivityAlpha();
        this.activityB = radioactiveProperties.getActivityBeta();
        this.activityG = radioactiveProperties.getActivityGamma();
        this.energyperdecayA = radioactiveProperties.getEnergyPerDecayAlpha();
        this.energyperdecayB = radioactiveProperties.getEnergyPerDecayBeta();
        this.energyperdecayG = radioactiveProperties.getEnergyPerDecayGamma();
    }

    @Override
    public int getActivityAlpha() {
        return activityA;
    }

    @Override
    public int getActivityBeta() {
        return activityB;
    }

    @Override
    public int getActivityGamma() {
        return activityG;
    }

    @Override
    public void setActivityAlpha(int activityA) {
        this.activityA = activityA;
    }

    @Override
    public void setActivityBeta(int activityB) {
        this.activityB = activityB;
    }

    @Override
    public void setActivityGamma(int activityG) {
        this.activityG = activityG;
    }

    @Override
    public double getEnergyPerDecayAlpha() {
        return energyperdecayA;
    }

    @Override
    public double getEnergyPerDecayBeta() {
        return energyperdecayB;
    }

    @Override
    public double getEnergyPerDecayGamma() {
        return energyperdecayG;
    }

    @Override
    public void setEnergyPerDecayAlpha(double energyperdecayA) {
        this.energyperdecayA = energyperdecayA;
    }

    @Override
    public void setEnergyPerDecayBeta(double energyperdecayB) {
        this.energyperdecayB = energyperdecayB;
    }

    @Override
    public void setEnergyPerDecayGamma(double energyperdecayG) {
        this.energyperdecayG = energyperdecayG;
    }

}
