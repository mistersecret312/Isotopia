package net.isotopia.mod.block;

import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.tile.IsotopeTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ShulkerBoxTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

public class IsotopicBlock extends TileBlock implements IIsotopic {

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
            if (!worldIn.isRemote && player.isCreative()) {
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

    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack itemstack = super.getItem(worldIn, pos, state);
        IsotopeTile isotopeTile = (IsotopeTile) worldIn.getTileEntity(pos);
        ((IIsotopic)itemstack.getItem()).setIsotopicData(isotopeTile.getIsotopicData());

        return itemstack;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return type.create();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public void setIsotopicData(List<IsotopeData> data) {
        this.data = data;
    }
}
