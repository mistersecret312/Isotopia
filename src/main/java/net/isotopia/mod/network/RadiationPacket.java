package net.isotopia.mod.network;

import net.isotopia.mod.cap.Capabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import java.util.function.Supplier;


public class RadiationPacket {
    public int id;
    public CompoundNBT data;

    public RadiationPacket(int id, CompoundNBT data) {
        this.id = id;
        this.data = data;
    }

    public static void encode(RadiationPacket mes, PacketBuffer buf) {
        buf.writeInt(mes.id);
        buf.writeCompoundTag(mes.data);
    }

    public static RadiationPacket decode(PacketBuffer buf) {
        return new RadiationPacket(buf.readInt(), buf.readCompoundTag());
    }

    public static void handle(RadiationPacket mes, Supplier<NetworkEvent.Context> cont) {
        cont.get().enqueueWork(() -> {
            Entity result = Minecraft.getInstance().world.getEntityByID(mes.id);
            if(result != null)
                result.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> cap.deserializeNBT(mes.data));
        });
        cont.get().setPacketHandled(true);
    }

}
