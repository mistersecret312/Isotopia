package net.isotopia.mod.block;

import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.tile.IsotopeTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import org.jetbrains.annotations.Nullable;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class IsotopicBlock extends Block implements IIsotopic {

    public boolean hasGenned = false;
    private List<IsotopeData> data;

    public IsotopicBlock(Properties properties, List<IsotopeData> data) {
        super(properties);
        this.data = data;
    }

    public List<IsotopeData> getIsotopicData() {
        return data;
    }

    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof IsotopeTile) {
            IsotopeTile isotopeTile = (IsotopeTile) tileentity;
            if (!worldIn.isRemote && !player.isCreative()) {
                ItemStack itemstack = new ItemStack(this);
                IIsotopic stack = (IIsotopic) itemstack.getItem();
                stack.setIsotopicData(isotopeTile.getIsotopicData());

                ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickupDelay();
                worldIn.addEntity(itementity);
            }
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
        if(stack.getItem() instanceof IIsotopic){
            IIsotopic item = (IIsotopic) stack.getItem();
            item.setIsotopicData(this.getIsotopicData());
        }
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        IsotopeTile tile = new IsotopeTile();
        tile.setIsotopicData(getIsotopicData());
        return tile;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public void setIsotopicData(List<IsotopeData> data) {
        this.data = data;
    }
}
