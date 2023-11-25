package net.isotopia.mod.block;

import net.isotopia.mod.helper.IsotopeData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

public class FissileBlock extends IsotopicBlock{

    private static final BooleanProperty property = BooleanProperty.create("fissile");
    private double strength = 1D;

    public FissileBlock(Properties properties, List<IsotopeData> data) {
        super(properties, data);
        this.setDefaultState(stateContainer.getBaseState().with(property, false));
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (worldIn.getBlockState(pos).get(property)) {
            this.getIsotopicData().forEach(iso -> {
                strength += iso.percentage * (iso.radioactiveProperties.getEnergyPerDecayBeta() + iso.radioactiveProperties.getEnergyPerDecayAlpha() + iso.radioactiveProperties.getEnergyPerDecayGamma()) * (iso.radioactiveProperties.getActivityAlpha() + iso.radioactiveProperties.getActivityBeta() + iso.radioactiveProperties.getActivityGamma());
            });
            explosionIn.clearAffectedBlockPositions();
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (5 * strength), false, Explosion.Mode.DESTROY);
            worldIn.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (float) (25 * strength), true, Explosion.Mode.BREAK);
            super.onExplosionDestroy(worldIn, pos, explosionIn);
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(property);
    }

    public void setFissile(BlockState state, World world, BlockPos pos, boolean value){
        world.setBlockState(pos, state.with(property, value));
    }
}
