package net.isotopia.mod.helper;

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
}
