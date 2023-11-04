package net.isotopia.mod.helper;

import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.cap.Capabilities;
import net.isotopia.mod.cap.IPlayerRad;
import net.isotopia.mod.cap.PlayerRadCap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IsotopiaMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    public static final ResourceLocation PLAYER_DATA_CAP = new ResourceLocation(IsotopiaMod.MOD_ID, "player_data");

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        // Check if one second has passed (20 ticks in Minecraft's default tick rate)
        if (!event.world.isRemote()) {
            if(event.world.getGameTime() % 20 == 0) {
                event.world.getServer().getPlayerList().getPlayers().forEach(player -> {
                    player.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> {
                        cap.tick();
                        cap.update();
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
