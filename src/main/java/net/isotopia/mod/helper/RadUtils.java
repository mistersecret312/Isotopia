package net.isotopia.mod.helper;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.isotopia.mod.cap.Capabilities;
import net.isotopia.mod.cap.IPlayerRad;
import net.isotopia.mod.item.ModItems;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;

public class RadUtils {

    public static IPlayerRad getPlayerRads(final PlayerEntity player) {
        try {
            final LazyOptional<IPlayerRad> lazy = (LazyOptional<IPlayerRad>)player.getCapability(Capabilities.PLAYER_RAD);
            return (IPlayerRad) lazy.orElseThrow(RuntimeException::new);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static void drawPlayerCapabilityText(PlayerEntity player, MatrixStack matrixStack, FontRenderer fr, int scaledWidth, int scaledHeight) {
        if (player != null) {
            if (isInEitherHand(player, ModItems.RADCOUNTER.get())) {
                fr.drawStringWithShadow(matrixStack, "Dose: " + RadUtils.formatDose(Math.round(RadUtils.getPlayerRads(player).getDose())), scaledWidth / 2 - 60, scaledHeight / 2 - 45, 0xFFFFFF);

            }
        }
    }

    public static boolean isInEitherHand(LivingEntity holder, Item item) {
        return isInMainHand(holder, item) || isInOffHand(holder, item);
    }

    public static boolean isInMainHand(LivingEntity holder, Item item) {
        return isInHand(Hand.MAIN_HAND, holder, item);
    }

    public static boolean isInOffHand(LivingEntity holder, Item item) {
        return isInHand(Hand.OFF_HAND, holder, item);
    }

    public static boolean isInHand(Hand hand, LivingEntity holder, Item item) {
        ItemStack heldItem = holder.getHeldItem(hand);
        return heldItem.getItem() == item;
    }

    public static String formatDose(double doseInMsv) {
        if (doseInMsv < 1.0) {
            return doseInMsv + " mSv";
        } else if (doseInMsv < 1000.0) {
            return doseInMsv + " mSv";
        } else if (doseInMsv < 1000000.0) {
            return doseInMsv / 1000.0 + " Sv";
        } else if (doseInMsv < 1000000000.0) {
            return doseInMsv / 1_000_000.0 + " kSv";
        } else if (doseInMsv < 1_000_000_000_000.0) {
            return doseInMsv / 1_000_000_000.0 + " MSv";
        } else {
            return doseInMsv / 1_000_000_000_000_000.0 + " GSv";
        }
    }


}
