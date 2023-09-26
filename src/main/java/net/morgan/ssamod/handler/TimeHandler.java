package net.morgan.ssamod.handler;

import net.minecraft.core.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.ModRegistry;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;
import net.morgan.ssamod.util.GameUtils;

@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID)
public final class TimeHandler {


    @SubscribeEvent
    public static void onWorldTick(final TickEvent.ClientTickEvent.ServerTickEvent.LevelTickEvent event) {

        if (GameUtils.getPlayer() == null) {
            return;
        }

        int worldTime = (int) event.level.getDayTime();
        int dayCount = (int) Math.floor((double) worldTime / 24000);

        if (worldTime == (dayCount * 24000) + 90 || worldTime == (dayCount * 24000) + 12990) {
            SoundHandler.tempCount = 0;
        }

        if (worldTime == (dayCount * 24000) + 100) {

            if (SoundHandler.tempCount == 0) {
                playRoosterSound(SoundsConfig.PLAY_IN_CAVE.get(), SoundsConfig.ROOSTER.get());
            }

        } else if (worldTime == (dayCount * 24000) + 13000) {

            if (SoundHandler.tempCount == 0) {
                playWolfSound(SoundsConfig.PLAY_IN_CAVE.get(), SoundsConfig.WOLF.get());
            }

        }

    }

    private static void playRoosterSound(boolean playInCave, float volume) {

        BlockPos blockPos = GameUtils.getPlayer().getOnPos();

        if (playInCave) {
            SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), volume, 1f);
        } else {
            if (blockPos.getY() > 50) {
                SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), volume, 1f);
            }
        }
        MessageHandler.sendMessage(GameUtils.getPlayer(), true);

    }

    private static void playWolfSound(boolean playInCave, float volume) {

        BlockPos blockPos = GameUtils.getPlayer().getOnPos();

        if (playInCave) {
            SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), volume, 1f);
        } else {
            if (blockPos.getY() > 50) {
                SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), volume, 1f);
            }
        }
        MessageHandler.sendMessage(GameUtils.getPlayer(), false);

    }

}
