package net.isotopia.mod.helper;

public class RadioactiveProperties {
    private int activityA;
    private int activityB;
    private int activityG ;
    private double energyperdecayA; //MeV
    private double energyperdecayB; //MeV
    private double energyperdecayG; //Mega-Electron Volts
    
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
