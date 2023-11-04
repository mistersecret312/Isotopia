package net.isotopia.mod.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.isotopia.mod.IsotopiaMod;
import net.isotopia.mod.helper.RadUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = IsotopiaMod.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void textOverlay(RenderGameOverlayEvent.Post event) {
        FontRenderer fr = Minecraft.getInstance().fontRenderer;
        int scaledWidth = Minecraft.getInstance().getMainWindow().getScaledWidth();
        int scaledHeight = Minecraft.getInstance().getMainWindow().getScaledHeight();
        MatrixStack matrixStack = event.getMatrixStack();
        PlayerEntity player = Minecraft.getInstance().player;
        if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
            RadUtils.drawPlayerCapabilityText(player, matrixStack, fr, scaledWidth, scaledHeight);
    }


}
