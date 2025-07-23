package com.morgan.ssamod.handler;

import com.morgan.ssamod.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import com.morgan.ssamod.SunriseSunsetAudio;
import com.morgan.ssamod.registery.ModRegistry;
import com.morgan.ssamod.util.GameUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import static net.minecraft.world.level.Level.OVERWORLD;

@EventBusSubscriber(modid = SunriseSunsetAudio.MODID)
public final class TimeHandler {

    public static ResourceKey<Level> PLAYER_CURRENT_DIMENSION;
    public static int CURRENT_MOON_PHASE;

    @SubscribeEvent
    public static void onWorldTick(final LevelTickEvent.Post event) {

        if (GameUtils.getPlayer() == null) return;

        int worldTime = (int) event.getLevel().getDayTime();
        int dayCount = (int) Math.floor((double) worldTime / 24000);

        if (worldTime == (dayCount * 24000) + Config.MORNING_TICK.get() - 10 ||
                worldTime == (dayCount * 24000) + Config.EVENING_TICK.get() - 10) {
            SoundHandler.tempCount = 0;
        }

        if (worldTime == (dayCount * 24000) + Config.MORNING_TICK.get()) {

            if (SoundHandler.tempCount == 0) {
                playSound(true);
            }

        } else if (worldTime == (dayCount * 24000) + Config.EVENING_TICK.get()) {

            if (SoundHandler.tempCount == 0) {
                CURRENT_MOON_PHASE = event.getLevel().getMoonPhase();
                SunriseSunsetAudio.LOGGER.info("" + CURRENT_MOON_PHASE);
                playSound(false);
            }

        }

    }

    private static boolean getMoonPhase() {

        if (Config.CURRENT_PHASE.get().equals("Full Moon")) return CURRENT_MOON_PHASE == 0;
        else return Config.CURRENT_PHASE.get().equals("All");

    }

    private static void playSound(boolean isRooster) {

        BlockPos blockPos;
        boolean playInCave = Config.PLAY_IN_CAVE.get();
        int chanceToPlay = isRooster ? Config.CHANCE_ROOSTER.get() : Config.CHANCE_WOLF.get();
        float volume = isRooster ? Config.ROOSTER.get() : Config.WOLF.get();

        try {

            if (GameUtils.getPlayer() == null) {
                SoundHandler.tempCount++;
                throw new NullPointerException();
            } else {
                blockPos =  GameUtils.getPlayer().getOnPos();
            }

        } catch (NullPointerException e) {
            SunriseSunsetAudio.LOGGER.error("SSA Mod can't get BlockPos, because player is null\n");
            e.fillInStackTrace();
            return;
        }

        MessageHandler.sendInformationMessage(GameUtils.getPlayer(), isRooster);

        if (PLAYER_CURRENT_DIMENSION != null && !PLAYER_CURRENT_DIMENSION.equals(OVERWORLD) && !Config.PLAY_IN_OTHER_DIM.get()) return;

        if (ModRegistry.ROOSTER_MORNING.get() != null && isRooster || ModRegistry.WOLF_EVENING.get() != null && !isRooster) {

            if (playInCave) {
                if (isRooster) SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), chanceToPlay, volume, 1f);
                else {
                    if (getMoonPhase()) SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), chanceToPlay, volume, 1f);
                }
            } else {
                if (blockPos.getY() > 50) {
                    if (isRooster) SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), chanceToPlay, volume, 1f);
                    else {
                        if (getMoonPhase()) SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), chanceToPlay, volume, 1f);
                    }
                }
            }

        } else {
            if (isRooster) SunriseSunsetAudio.LOGGER.error("SSA Mod " + ModRegistry.ROOSTER_MORNING.get() + " is missing");
            else SunriseSunsetAudio.LOGGER.error("SSA Mod " + ModRegistry.WOLF_EVENING.get() + " is missing");
        }

    }

}
