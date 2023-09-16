package net.morgan.ssamod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.ModRegistry;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;


@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID)
public final class ServerTimeHandler {

    @SubscribeEvent
    public static void onWorldTick(final TickEvent.ServerTickEvent.LevelTickEvent event) {

        long worldTime = event.level.getLevelData().getDayTime();
        LocalPlayer player = Minecraft.getInstance().player;

        int dayCount = (int) Math.floor((double) worldTime / 24000);

        assert player != null;
        if (worldTime == (dayCount * 24000L) + 100) {

            float roosterVolume = SoundsConfig.ROOSTER.get();

            if (SoundsConfig.PLAY_IN_CAVE.get()) {

                if (SoundsConfig.SEND_MESSAGES.get()) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.morning_message"));
                }

                SoundHandler.playSoundForPlayer(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
                //player.playSound(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
            } else {
                if (player.getOnPos().getY() < 50) {

                    if (SoundsConfig.SEND_MESSAGES.get()) {
                        Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.morning_message"));
                    }

                } else {
                    SoundHandler.playSoundForPlayer(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
                }

            }

            //event.level.playSound(player, player.getOnPos(), ModRegistry.ROOSTER_MORNING.get(), SoundSource.AMBIENT, 0.9f, 1f);

        } else if (worldTime == (dayCount * 24000L) + 13000) {

            float wolfVolume = SoundsConfig.WOLF.get();

            if (SoundsConfig.PLAY_IN_CAVE.get()) {

                if (SoundsConfig.SEND_MESSAGES.get()) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.evening_message"));
                }

                SoundHandler.playSoundForPlayer(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);
               // player.playSound(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);

            } else {
                if (player.getOnPos().getY() < 50) {

                    if (SoundsConfig.SEND_MESSAGES.get()) {
                        Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.evening_message"));
                    }

                } else {
                    SoundHandler.playSoundForPlayer(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);
                }

            }


            //event.level.playSound(player, player.getOnPos(), ModRegistry.WOLF_EVENING.get(), SoundSource.AMBIENT, 0.1f, 1f);

        }

    }

}
