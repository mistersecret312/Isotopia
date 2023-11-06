package net.isotopia.mod.item;

import net.isotopia.mod.helper.RadioactiveProperties;
import net.isotopia.mod.helper.IRadioactive;
import net.minecraft.item.Item;

public class RadioactiveItemBase extends Item implements IRadioactive {

    private RadioactiveProperties radioactiveProperties;

    public RadioactiveItemBase(Properties properties, RadioactiveProperties radioactiveProperties) {
        super(properties);
        this.radioactiveProperties = radioactiveProperties;
    }

    @Override
    public RadioactiveProperties getRadProps() {
        return radioactiveProperties;
    }
}
