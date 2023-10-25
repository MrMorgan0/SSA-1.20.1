package net.morgan.ssamod.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;

import javax.annotation.Nullable;

import static net.minecraft.world.level.Level.OVERWORLD;

public class MessageHandler {

    public static void sendInformationMessage(Player player, boolean isRooster) {

        if (!SoundsConfig.SEND_MESSAGES_IN_OTHER_DIM.get() && !TimeHandler.PLAYER_CURRENT_DIMENSION.equals(OVERWORLD)) return;

        if (SoundsConfig.SEND_MESSAGES.get()) {
            if (isRooster) {
                player.displayClientMessage(Component.translatable("messages.ssa.morning_message").
                        withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
            } else {
                player.displayClientMessage(Component.translatable("messages.ssa.evening_message").
                        withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
            }

        }

    }

    public static void sendMessage(@Nullable Player player, Component message) {

        if (player != null) player.displayClientMessage(message,true);
        else SSAMod.LOGGER.error("SSA Mod can't get Player, because player is null");

    }

}
