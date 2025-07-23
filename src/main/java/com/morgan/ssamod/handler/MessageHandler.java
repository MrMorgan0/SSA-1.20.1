package com.morgan.ssamod.handler;

import com.morgan.ssamod.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import com.morgan.ssamod.SunriseSunsetAudio;

import javax.annotation.Nullable;

import static net.minecraft.world.level.Level.OVERWORLD;

public class MessageHandler {

    public static void sendInformationMessage(Player player, boolean isRooster) {

        if (!Config.SEND_MESSAGES_IN_OTHER_DIM.get() && !TimeHandler.PLAYER_CURRENT_DIMENSION.equals(OVERWORLD)) return;

        try {

            if (Config.SEND_MESSAGES.get()) {
                if (isRooster) {
                    player.displayClientMessage(Component.translatable("messages.ssa.morning_message").
                            withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
                } else {
                    player.displayClientMessage(Component.translatable("messages.ssa.evening_message").
                            withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
                }

            }

        } catch (Exception exception) {
            SunriseSunsetAudio.LOGGER.error("SSA Mod can't show message, ActionBar is busy");
            exception.fillInStackTrace();
        }

    }

    public static void sendMessage(@Nullable Player player, Component message) {

        if (player != null) player.displayClientMessage(message,true);
        else SunriseSunsetAudio.LOGGER.error("SSA Mod can't get Player, because player is null");

    }

}
