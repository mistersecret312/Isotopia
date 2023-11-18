package net.isotopia.mod.helper;

import com.google.common.collect.Lists;
import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.block.IsotopicBlock;
import net.isotopia.mod.block.ModBlocks;
import net.isotopia.mod.cap.Capabilities;
import net.isotopia.mod.cap.IPlayerRad;
import net.isotopia.mod.cap.PlayerRadCap;
import net.isotopia.mod.tile.IsotopeTile;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = IsotopiaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    public static final ResourceLocation PLAYER_DATA_CAP = new ResourceLocation(IsotopiaMod.MOD_ID, "player_data");


    public void onChunkLoad(ChunkEvent.Load event) {
        Chunk chunk = (Chunk) event.getChunk();

        // Iterate through the blocks in the chunk
        BlockPos.Mutable blockPos = new BlockPos.Mutable();
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 256; y++) { // Adjust the y-range as needed
                for (int z = 0; z < 16; z++) {
                    blockPos.setPos(chunk.getPos().getXStart() + x, y, chunk.getPos().getZStart() + z);
                    TileEntity tileEntity = chunk.getTileEntity(blockPos, Chunk.CreateEntityType.CHECK);
                    if (tileEntity instanceof IsotopeTile) {
                        IsotopeTile tile = (IsotopeTile) tileEntity;
                        if(tile.getBlockState().getBlock() == ModBlocks.URANIUM_ORE.get()){
                            Random random = new Random();
                            double minValue = 0.99;
                            double maxValue = 1.0;
                            double randomValue = minValue + (maxValue - minValue) * random.nextDouble();

                            List<IsotopeData> list = Lists.newArrayList();
                            list.add(new IsotopeData((1-randomValue)*100, new RadioactiveProperties(300000, 300, 0, 4.2, 0.002, 0.0)));
                            list.add(new IsotopeData(randomValue*100, new RadioactiveProperties(5555, 2, 1, 4.8, 0.2, 1.2)));
                            tile.setIsotopicData(list);
                            IsotopicBlock block = (IsotopicBlock) tile.getBlockState().getBlock();
                            block.setIsotopicData(list);
                            block.hasGenned = true;

                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        // Check if one second has passed (20 ticks in Minecraft's default tick rate)
        if (!event.world.isRemote()) {
            if(event.world.getGameTime() % 20 == 0) {
                event.world.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> {
                        cap.tick();
                    });
                });
            }
        }
    }

    @SubscribeEvent
    public static void attachPlayerCap(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity)
            event.addCapability(PLAYER_DATA_CAP, new IPlayerRad.Provider(new PlayerRadCap((PlayerEntity) event.getObject())));
    }




}
