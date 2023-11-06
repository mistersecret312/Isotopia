package net.isotopia.mod.item;

import net.isotopia.mod.helper.RadioactiveProperties;
import net.isotopia.mod.helper.IRadioactive;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

public class RadioactiveBlockItem extends BlockItem implements IRadioactive {

    private RadioactiveProperties radioactiveProperties;

    public RadioactiveBlockItem(Block blockIn,Properties properties, RadioactiveProperties radioactiveProperties) {
        super(blockIn,properties);
        this.radioactiveProperties = radioactiveProperties;
    }

    @Override
    public RadioactiveProperties getRadProps() {
        return radioactiveProperties;
    }
}
