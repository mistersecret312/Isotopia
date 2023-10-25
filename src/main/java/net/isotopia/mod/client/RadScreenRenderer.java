package net.isotopia.mod.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.isotopia.mod.cap.Capabilities;
import net.isotopia.mod.cap.PlayerRadCap;
import net.isotopia.mod.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.ServerPlayerEntity;

public class RadScreenRenderer extends IngameGui {
    private final Minecraft mc;

    public RadScreenRenderer(Minecraft mcIn) {
        super(mcIn);
        this.mc = mcIn;
    }

    @Override
    public void renderIngameGui(MatrixStack matrixStack, float partialTicks) {
        this.scaledWidth = this.mc.getMainWindow().getScaledWidth();
        this.scaledHeight = this.mc.getMainWindow().getScaledHeight();
        RenderSystem.enableBlend();
        if (this.mc.player.getHeldItemMainhand() == ModItems.RADCOUNTER.get().getDefaultInstance()) {
            this.mc.player.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> {
                this.mc.getProfiler().startSection("radLevel");
                String s = "" + cap.getDose() + "mSv";
                int i1 = (this.scaledWidth - this.getFontRenderer().getStringWidth(s)) / 4;
                int j1 = this.scaledHeight - 31 - 9;
                this.getFontRenderer().drawString(matrixStack, s, (float) (i1 + 1), (float) j1, 12);

            });
        }
    }
}
