package net.isotopia.mod.worldgen;

import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.block.ModBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IsoFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, IsotopiaMod.MOD_ID);

    public static final RegistryObject<Feature<OreFeatureConfig>> URANIUM_ORE = FEATURES.register("cinnabar_ore", () -> new OreFeature(OreFeatureConfig.CODEC));

    public static class ConfiguredFeatures{
        public static final ConfiguredFeature<?, ?> CONFIGURED_URANIUM_ORE = IsoFeatures.URANIUM_ORE.get().withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                        ModBlocks.URANIUM_ORE.get().getDefaultState(), 9)) //vein size of 9
                .withPlacement(Placement.CHANCE.configure(new ChanceConfig(15)).range(64).square().count(9));
    }

    /**
     * Registers Configured Versions of the Features in FMLCommonSetup
     * <br> Prevents mod incompatibility issues where we can accidentally delete other mod's world gen features
     */
    public static void registerConfiguredFeatures() {
        registerConfiguredFeature("uranium_ore", ConfiguredFeatures.CONFIGURED_URANIUM_ORE);
    }

    private static <T extends Feature<?>> void registerConfiguredFeature(String registryName, ConfiguredFeature<?,?> configuredFeature) {
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(IsotopiaMod.MOD_ID, registryName), configuredFeature);
    }


}
