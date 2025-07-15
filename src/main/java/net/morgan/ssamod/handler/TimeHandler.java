package net.morgan.ssamod.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.registery.ModRegistry;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;
import net.morgan.ssamod.util.GameUtils;


import static net.minecraft.world.level.Level.OVERWORLD;

@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID)
public final class TimeHandler {

    public static ResourceKey<Level> PLAYER_CURRENT_DIMENSION;
    public static int CURRENT_MOON_PHASE;

    @SubscribeEvent
    public static void onWorldTick(final TickEvent.ClientTickEvent.ServerTickEvent.LevelTickEvent event) {

        if (GameUtils.getPlayer() == null) return;

        int worldTime = (int) event.level.getDayTime();
        int dayCount = (int) Math.floor((double) worldTime / 24000);

        if (worldTime == (dayCount * 24000) + SoundsConfig.MORNING_TICK.get() - 10 ||
                worldTime == (dayCount * 24000) + SoundsConfig.EVENING_TICK.get() - 10) {
            SoundHandler.tempCount = 0;
        }

        if (worldTime == (dayCount * 24000) + SoundsConfig.MORNING_TICK.get()) {

            if (SoundHandler.tempCount == 0) {
                playSound(true);
            }

        } else if (worldTime == (dayCount * 24000) + SoundsConfig.EVENING_TICK.get()) {

            if (SoundHandler.tempCount == 0) {
                CURRENT_MOON_PHASE = event.level.getMoonPhase();
                SSAMod.LOGGER.info("" + CURRENT_MOON_PHASE);
                playSound(false);
            }

        }

    }

    private static boolean getMoonPhase() {

        if (SoundsConfig.CURRENT_PHASE.get().equals("Full Moon")) return CURRENT_MOON_PHASE == 0;
        else return SoundsConfig.CURRENT_PHASE.get().equals("All");

    }

    private static void playSound(boolean isRooster) {

        BlockPos blockPos;
        boolean playInCave = SoundsConfig.PLAY_IN_CAVE.get();
        int chanceToPlay = isRooster ? SoundsConfig.CHANCE_ROOSTER.get() : SoundsConfig.CHANCE_WOLF.get();
        float volume = isRooster ? SoundsConfig.ROOSTER.get() : SoundsConfig.WOLF.get();

        try {

            if (GameUtils.getPlayer() == null) {
                SoundHandler.tempCount++;
                throw new NullPointerException();
            } else {
                blockPos =  GameUtils.getPlayer().getOnPos();
            }

        } catch (NullPointerException e) {
            SSAMod.LOGGER.error("SSA Mod can't get BlockPos, because player is null\n");
            e.fillInStackTrace();
            return;
        }

        MessageHandler.sendInformationMessage(GameUtils.getPlayer(), isRooster);

        if (PLAYER_CURRENT_DIMENSION != null && !PLAYER_CURRENT_DIMENSION.equals(OVERWORLD) && !SoundsConfig.PLAY_IN_OTHER_DIM.get()) return;

        if (ModRegistry.ROOSTER_MORNING.isPresent() && isRooster || ModRegistry.WOLF_EVENING.isPresent() && !isRooster) {

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
            if (isRooster) SSAMod.LOGGER.error("SSA Mod " + ModRegistry.ROOSTER_MORNING.getId() + " is missing");
            else SSAMod.LOGGER.error("SSA Mod " + ModRegistry.WOLF_EVENING.getId() + " is missing");
        }

    }

}
