package net.isotopia.mod.block;

import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.minecraft.block.Block;

import java.util.List;

public class IsotopicBlock extends Block implements IIsotopic {

    private List<IsotopeData> data;

    public IsotopicBlock(Properties properties, List<IsotopeData> data) {
        super(properties);
        this.data = data;
    }

    public List<IsotopeData> getIsotopicData() {
        return data;
    }

    public void setIsotopicData(List<IsotopeData> data) {
        this.data = data;
    }
}
