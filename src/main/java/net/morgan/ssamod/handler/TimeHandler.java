package net.morgan.ssamod.handler;

import net.minecraft.core.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.registery.ModRegistry;
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
                playSound(true, SoundsConfig.PLAY_IN_CAVE.get(), SoundsConfig.ROOSTER.get());
            }

        } else if (worldTime == (dayCount * 24000) + 13000) {

            if (SoundHandler.tempCount == 0) {
                playSound(false, SoundsConfig.PLAY_IN_CAVE.get(), SoundsConfig.WOLF.get());
            }

        }

    }

    private static void playSound(boolean isRooster, boolean playInCave, float volume) {

        BlockPos blockPos;

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

        MessageHandler.sendMessage(GameUtils.getPlayer(), isRooster);

    }

}
