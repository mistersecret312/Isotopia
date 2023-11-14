package net.isotopia.mod.block;

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
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class GlowingRadioactiveBlock extends IsotopicBlock {

    private BooleanProperty property = BooleanProperty.create("glowing");

    public GlowingRadioactiveBlock(Properties properties, List<IsotopeData> data) {
        super(properties, data);
        this.setDefaultState(stateContainer.getBaseState().with(property, false));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if(!worldIn.getBlockState(pos).get(property)){
            if(this.getIsotopicData().get(0).getPercentage() > 0.007){
                worldIn.setBlockState(pos, state.with(property, true));
            }
        }
        super.randomTick(state, worldIn, pos, random);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return !state.get(property);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(property);
    }
}
