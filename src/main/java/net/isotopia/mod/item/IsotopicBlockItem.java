package net.isotopia.mod.item;

import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IRadioactive;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IsotopicBlockItem extends BlockItem implements IIsotopic {

    private List<IsotopeData> data;

    public IsotopicBlockItem(Block blockIn, Properties properties, List<IsotopeData> data) {
        super(blockIn,properties);
        this.data = data;
    }



    @Override
    protected boolean onBlockPlaced(BlockPos pos, World worldIn, @Nullable PlayerEntity player, ItemStack stack, BlockState state) {
        IIsotopic block = (IIsotopic) state.getBlock();
        block.setIsotopicData(data);
        return super.onBlockPlaced(pos, worldIn, player, stack, state);
    }

    @Override
    public List<IsotopeData> getIsotopicData() {
        return data;
    }

    @Override
    public void setIsotopicData(List<IsotopeData> data) {
        this.data = data;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        AtomicInteger i = new AtomicInteger();
        this.getIsotopicData().forEach(iso -> {
            i.getAndIncrement();
            tooltip.add(new TranslationTextComponent("tooltip."+stack.getTranslationKey()+".isotope."+i.get()).mergeStyle(TextFormatting.DARK_GREEN).appendSibling(new StringTextComponent(": " + iso.getPercentage()).mergeStyle(TextFormatting.WHITE).appendSibling(new StringTextComponent("%"))));
        });
    }
}
