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
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;

import java.text.DecimalFormat;

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
            if (isInEitherHand(player, ModItems.DOSIMETER.get())) {
                DecimalFormat df = new DecimalFormat("#.#");
                player.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> fr.drawStringWithShadow(matrixStack, "Dose: " + RadUtils.formatDose(Double.parseDouble(df.format(cap.getDose()))), scaledWidth/4 - 10, scaledHeight/4 - 5, 0xFFFFFF));

            }
        }
        if (player != null) {
            if (isInEitherHand(player, ModItems.GEIGER.get())) {
                DecimalFormat df = new DecimalFormat("#.#");
                player.getCapability(Capabilities.PLAYER_RAD).ifPresent(cap -> fr.drawStringWithShadow(matrixStack, "Radiation: " + RadUtils.formatDoseRate(Double.parseDouble(df.format(cap.getDosingRate()))), scaledWidth/4 - 10, scaledHeight/4 - 5, 0xFFFFFF));

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
        DecimalFormat df = new DecimalFormat("#.#");
        if (doseInMsv < 1.0) {
            return df.format(doseInMsv) + " mSv";
        } else if (doseInMsv < 1000.0) {
            return df.format(doseInMsv) + " mSv";
        } else if (doseInMsv < 1000000.0) {
            return df.format(doseInMsv / 1000.0) + " Sv";
        } else if (doseInMsv < 1000000000.0) {
            return df.format(doseInMsv / 1_000_000.0) + " kSv";
        } else if (doseInMsv < 1_000_000_000_000.0) {
            return df.format(doseInMsv / 1_000_000_000.0) + " MSv";
        } else {
            return df.format(doseInMsv / 1_000_000_000_000_000.0) + " GSv";
        }
    }

    public static String formatDoseRate(double doseInMsv) {
        DecimalFormat df = new DecimalFormat("#.#");
        if (doseInMsv < 1.0) {
            return df.format(doseInMsv) + " mSv/s";
        } else if (doseInMsv < 1000.0) {
            return df.format(doseInMsv) + " mSv/s";
        } else if (doseInMsv < 1000000.0) {
            return df.format(doseInMsv / 1000.0) + " Sv/s";
        } else if (doseInMsv < 1000000000.0) {
            return df.format(doseInMsv / 1_000_000.0) + " kSv/s";
        } else if (doseInMsv < 1_000_000_000_000.0) {
            return df.format(doseInMsv / 1_000_000_000.0) + " MSv/s";
        } else {
            return df.format(doseInMsv / 1_000_000_000_000_000.0) + " GSv/s";
        }
    }

    public static RadioactiveProperties getArmorProtectionFromRadiation(PlayerEntity player){
        return new RadioactiveProperties((int) (100*player.getTotalArmorValue()*player.getArmorCoverPercentage()),
                (int) (30*player.getTotalArmorValue()*player.getArmorCoverPercentage()),
                (int) (5*player.getTotalArmorValue()*player.getArmorCoverPercentage()),
                -100, -3, -1);
    }


}
