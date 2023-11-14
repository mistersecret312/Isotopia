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

    public void setActivityA(int activityA) {
        this.activityA = activityA;
    }

    public void setActivityB(int activityB) {
        this.activityB = activityB;
    }

    public void setActivityG(int activityG) {
        this.activityG = activityG;
    }

    public void setEnergyperdecayA(double energyperdecayA) {
        this.energyperdecayA = energyperdecayA;
    }

    public void setEnergyperdecayB(double energyperdecayB) {
        this.energyperdecayB = energyperdecayB;
    }

    public void setEnergyperdecayG(double energyperdecayG) {
        this.energyperdecayG = energyperdecayG;
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
