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
        int moonPhase = event.level.getMoonPhase();

        int dayCount = (int) Math.floor((double) worldTime / 24000);

        assert player != null;
        if (worldTime == (dayCount * 24000L) + 100) {

            float roosterVolume = SoundsConfig.ROOSTER.get() / 100.0f;

            if (SoundsConfig.PLAY_IN_CAVE.get()) {
                player.playSound(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
            } else {
                if (player.getOnPos().getY() < 50 && SoundsConfig.SEND_MESSAGES.get()) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.morning_message"));
                } else {
                    player.playSound(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
                }
            }

            //event.level.playSound(player, player.getOnPos(), ModRegistry.ROOSTER_MORNING.get(), SoundSource.AMBIENT, 0.9f, 1f);

        } else if (worldTime == (dayCount * 24000L) + 13000) {

            float wolfVolume = SoundsConfig.WOLF.get() / 100.0f;

            if (SoundsConfig.PLAY_IN_CAVE.get()) {
                player.playSound(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);
            } else {
                if (player.getOnPos().getY() < 50 && SoundsConfig.SEND_MESSAGES.get()) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.translatable("messages.ssa.evening_message"));
                } else {
                    player.playSound(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);
                }
            }


            //event.level.playSound(player, player.getOnPos(), ModRegistry.WOLF_EVENING.get(), SoundSource.AMBIENT, 0.1f, 1f);

        }

    }

}
