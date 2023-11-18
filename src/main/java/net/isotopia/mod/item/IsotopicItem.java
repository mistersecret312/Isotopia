package net.isotopia.mod.item;

import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class IsotopicItem extends Item implements IIsotopic {

    private List<IsotopeData> data;


    public IsotopicItem(Item.Properties properties, List<IsotopeData> data) {
        super(properties);
        this.data = data;
    }

    public List<IsotopeData> getIsotopicData(){
        return data;
    }

    public void setIsotopicData(List<IsotopeData> data) {
        this.data = data;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        AtomicInteger i = new AtomicInteger();
        ((IIsotopic)stack.getItem()).getIsotopicData().forEach(iso -> {
            i.getAndIncrement();
            tooltip.add(new TranslationTextComponent("tooltip."+stack.getTranslationKey()+".isotope."+i.get()).mergeStyle(TextFormatting.DARK_GREEN).appendSibling(new StringTextComponent(": " + iso.getPercentage()).mergeStyle(TextFormatting.WHITE).appendSibling(new StringTextComponent("%"))));
        });
    }
}
