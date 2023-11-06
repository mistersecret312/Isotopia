package net.isotopia.mod.item;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.isotopia.mod.helper.IsotopeData;
import net.isotopia.mod.helper.RadioactiveProperties;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.isotopia.mod.IsotopiaMod;

import java.util.Collections;
import java.util.Map;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IsotopiaMod.MOD_ID);


    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new IsotopicItem(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP),
            Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(33333, 4, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2)))));

    public static final RegistryObject<Item> URANIUM_UNREFINED = ITEMS.register("unrefined_uranium", () -> new IsotopicItem(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP),
            Lists.newArrayList(new IsotopeData(0.7, new RadioactiveProperties(10000, 2, 0, 4.2, 0.002, 0.0)), new IsotopeData(99.3, new RadioactiveProperties(555, 2, 1, 4.8, 0.2, 1.2)))));


    public static final RegistryObject<Item> DOSIMETER = ITEMS.register("dosimeter", () -> new Item(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP)));
    public static final RegistryObject<Item> GEIGER = ITEMS.register("geiger_counter", () -> new Item(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
