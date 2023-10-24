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
                playSound(false);
            }

        }

    }

    private static void playSound(boolean isRooster) {

        BlockPos blockPos;
        boolean playInCave = SoundsConfig.PLAY_IN_CAVE.get();
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
            e.printStackTrace();
            return;
        }

        MessageHandler.sendInformationMessage(GameUtils.getPlayer(), isRooster);

        if (PLAYER_CURRENT_DIMENSION != null && !PLAYER_CURRENT_DIMENSION.equals(OVERWORLD) && !SoundsConfig.PLAY_IN_OTHER_DIM.get()) return;

        if (ModRegistry.ROOSTER_MORNING.isPresent() && isRooster || ModRegistry.WOLF_EVENING.isPresent() && !isRooster) {

            if (playInCave) {
                if (isRooster) SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), volume, 1f);
                else SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), volume, 1f);
            } else {
                if (blockPos.getY() > 50) {
                    if (isRooster) SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), volume, 1f);
                    else SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), volume, 1f);
                }
            }

        } else {
            if (isRooster) SSAMod.LOGGER.error("SSA Mod " + ModRegistry.ROOSTER_MORNING.getId() + " is missing");
            else SSAMod.LOGGER.error("SSA Mod " + ModRegistry.WOLF_EVENING.getId() + " is missing");
        }

    }

}
