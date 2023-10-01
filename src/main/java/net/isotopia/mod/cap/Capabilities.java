package net.isotopia.mod.cap;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class Capabilities {

    @CapabilityInject(IPlayerRad.class)
    public static final Capability<IPlayerRad> PLAYER_RAD = null;
}
