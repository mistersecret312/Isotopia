package net.isotopia.mod.helper;

import net.isotopia.mod.cap.Capabilities;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WorldTickHandler {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.side.isServer() && event.phase == TickEvent.Phase.START) {
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
    }
}
