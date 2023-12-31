package net.isotopia.mod.block;

import net.isotopia.mod.helper.RadioactiveProperties;
import net.isotopia.mod.helper.IRadioactive;
import net.minecraft.block.Block;

public class RadioactiveBlockBase extends Block implements IRadioactive {

    private RadioactiveProperties radioactiveProperties;

    public RadioactiveBlockBase(Properties properties, RadioactiveProperties radioactiveProperties) {
        super(properties);
        this.radioactiveProperties = radioactiveProperties;
    }

    @Override
    public RadioactiveProperties getRadProps() {
        return radioactiveProperties;
    }

}
