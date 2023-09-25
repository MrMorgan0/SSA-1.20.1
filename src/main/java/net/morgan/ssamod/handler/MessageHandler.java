package net.morgan.ssamod.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.morgan.ssamod.config.SoundsConfig;

public class MessageHandler {

    public static void sendMessage(Player player, boolean isMorning) {

        if (SoundsConfig.SEND_MESSAGES.get()) {
            if (isMorning) {
                player.displayClientMessage(Component.translatable("messages.ssa.morning_message").
                        withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
            } else {
                player.displayClientMessage(Component.translatable("messages.ssa.evening_message").
                        withStyle(ChatFormatting.BOLD, ChatFormatting.ITALIC), true);
            }

        }

    }

}
