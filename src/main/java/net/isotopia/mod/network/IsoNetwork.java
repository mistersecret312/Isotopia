package net.isotopia.mod.network;

import net.isotopia.mod.IsotopiaMod;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class IsoNetwork {
    private static int ID = 0;
    private static final String PROTOCOL_VERSION = Integer.toString(1);
    private static final SimpleChannel NETWORK_CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(IsotopiaMod.MOD_ID, "main_channel"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);


    public static void init() {
        NETWORK_CHANNEL.registerMessage(nextId(), RadiationPacket.class, RadiationPacket::encode, RadiationPacket::decode, RadiationPacket::handle);
    }

    /**
     * Sends a packet to the server.<br>
     * Must be called Client side.
     */
    public static void sendToServer(Object msg) {
        NETWORK_CHANNEL.sendToServer(msg);
    }

    public static int nextId(){
        return ++ID;
    }

    public static void sendTo(Object msg, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer)) {
            NETWORK_CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), msg);
        }
    }

}
