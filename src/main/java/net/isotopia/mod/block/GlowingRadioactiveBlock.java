package net.isotopia.mod.block;

import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.helper.IRadioactive;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.RangedInteger;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Random;

public class GlowingRadioactiveBlock extends IsotopicBlock {

    private static final BooleanProperty property = BooleanProperty.create("glowing");

    public GlowingRadioactiveBlock(Properties properties, List<IsotopeData> data) {
        super(properties, data);
        this.setDefaultState(stateContainer.getBaseState().with(property, false));
    }
    public void setGlow(BlockState state, World world, BlockPos pos, boolean value){
        world.setBlockState(pos, state.with(property, value));
    }

   @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(property);
    }
}
