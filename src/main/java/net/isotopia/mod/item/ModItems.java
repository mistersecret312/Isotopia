package net.isotopia.mod.item;

import net.isotopia.mod.cap.RadioactiveProperties;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.isotopia.mod.IsotopiaMod;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, IsotopiaMod.MOD_ID);


    public static final RegistryObject<Item> AMETHYST = ITEMS.register("uranium_ingot",
            () -> new RadioactiveItemBase(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP), new RadioactiveProperties(33333, 4, 0, 4.2, 0.002, 0.0)));

    public static final RegistryObject<Item> RADCOUNTER = ITEMS.register("dosimeter", () -> new Item(new Item.Properties().group(ModItemGroup.TUTORIAL_GROUP)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
