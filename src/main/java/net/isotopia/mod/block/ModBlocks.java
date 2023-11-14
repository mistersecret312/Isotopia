package net.isotopia.mod.block;

import com.google.common.collect.Lists;
import net.isotopia.mod.helper.IIsotopic;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.isotopia.mod.helper.IRadioactive;
import net.isotopia.mod.item.IsotopicBlockItem;
import net.isotopia.mod.item.RadioactiveBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.item.ModItemGroup;
import net.isotopia.mod.item.ModItems;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, IsotopiaMod.MOD_ID);

    public static final RegistryObject<Block> URANIUM_ORE = registerIsotopicBlock("uranium_ore",
            () -> new GlowingRadioactiveBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(5f), Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(300000, 300, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2)))), Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(300000, 300, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2))));

    public static final RegistryObject<Block> URANIUM_BLOCK = registerIsotopicBlock("uranium_block",
            () -> new IsotopicBlock(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).setRequiresTool().harvestTool(ToolType.PICKAXE).hardnessAndResistance(8f), Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(300000, 300, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2)))), Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(300000, 300, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2))));


    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<T> registerRadioactiveBlock(String name, Supplier<T> block, RadioactiveProperties radioactiveProperties) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerRadioactiveBlockItem(name, toReturn, radioactiveProperties);
        return toReturn;
    }

    private static <T extends Block>RegistryObject<T> registerIsotopicBlock(String name, Supplier<T> block, List<IsotopeData> data) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerIsotopicBlockItem(name, toReturn, data);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP)));
    }

    private static <T extends Block> void registerRadioactiveBlockItem(String name, RegistryObject<T> block, RadioactiveProperties radioactiveProperties) {
        ModItems.ITEMS.register(name, () -> new RadioactiveBlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP), radioactiveProperties));
    }

    private static <T extends Block> void registerIsotopicBlockItem(String name, RegistryObject<T> block, List<IsotopeData> data) {
        ModItems.ITEMS.register(name, () -> new IsotopicBlockItem(block.get(),
                new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP), data));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
