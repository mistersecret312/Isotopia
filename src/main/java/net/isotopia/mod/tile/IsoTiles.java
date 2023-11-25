package net.isotopia.mod.tile;

import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.block.ModBlocks;
import net.isotopia.mod.block.TileBlock;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class IsoTiles {

    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, IsotopiaMod.MOD_ID);

    public static final RegistryObject<TileEntityType<IsotopeTile>> ISO = TILES.register("isotopic", () -> registerTiles(IsotopeTile::new, ModBlocks.URANIUM_ORE.get()));

    private static <T extends TileEntity> TileEntityType<T> registerTiles(Supplier<T> tile, Block... validBlock) {
        TileEntityType<T> type = TileEntityType.Builder.create(tile, validBlock).build(null);
        for(Block block : validBlock) {
            if(block instanceof TileBlock) {
                ((TileBlock)block).setTileEntity(type);
            }
        }
        return type;
    }

}
