package net.isotopia.mod.cap;

public class RadioactiveProperties {
    private int activityA;
    private int activityB;
    private int activityG;
    private double energyperdecayA;
    private double energyperdecayB;
    private double energyperdecayG;
    
    public RadioactiveProperties(int activityA, int activityB, int activityG, double energyperdecayA, double energyperdecayB, double energyperdecayG){
        this.activityA = activityA;
        this.activityB = activityB;
        this.activityG = activityG;
        this.energyperdecayA = energyperdecayA;
        this.energyperdecayB = energyperdecayB;
        this.energyperdecayG = energyperdecayG;
    }
    public int getActivityAlpha() {
        return activityA;
    }
    
    public int getActivityBeta() {
        return activityB;
    }

    
    public int getActivityGamma() {
        return activityG;
    }
    
    public double getEnergyPerDecayAlpha() {
        return energyperdecayA;
    }

    public double getEnergyPerDecayBeta() {
        return energyperdecayB;
    }
    
    public double getEnergyPerDecayGamma() {
        return energyperdecayG;
    }
}