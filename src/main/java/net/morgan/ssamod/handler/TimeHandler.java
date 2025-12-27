package net.morgan.ssamod.handler;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import static net.minecraft.world.level.Level.OVERWORLD;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;
import net.morgan.ssamod.registery.ModRegistry;
import net.morgan.ssamod.util.GameUtils;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, value = Dist.CLIENT)
public final class TimeHandler {

    public static ResourceKey<Level> PLAYER_CURRENT_DIMENSION;
    public static int CURRENT_MOON_PHASE;

    // Track which day sounds were last played to ensure once-per-day
    private static int lastMorningSoundDay = -1;
    private static int lastEveningSoundDay = -1;

    // Time window in ticks to allow for missed ticks on multiplayer servers
    private static final int TRIGGER_WINDOW = 100; // 5 seconds window

    @SubscribeEvent
    public static void onWorldTick(final TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END)
            return;
        if (GameUtils.getPlayer() == null)
            return;

        ClientLevel level = (ClientLevel) GameUtils.getPlayer().level();
        if (level == null)
            return;

        long worldTime = level.getDayTime();

        // Handle negative time values (can occur on some servers with time manipulation)
        // Java's modulo can return negative values, so we normalize properly
        long timeOfDay = ((worldTime % 24000) + 24000) % 24000;
        int dayCount = (int) Math.floor((double) worldTime / 24000);

        int morningTick = SoundsConfig.MORNING_TICK.get();
        int eveningTick = SoundsConfig.EVENING_TICK.get();

        // Check morning sound - within time window and not played this day yet
        if (timeOfDay >= morningTick && timeOfDay < morningTick + TRIGGER_WINDOW) {
            if (lastMorningSoundDay != dayCount) {
                lastMorningSoundDay = dayCount;
                playSound(true);
            }
        }

        // Check evening sound - within time window and not played this day yet
        if (timeOfDay >= eveningTick && timeOfDay < eveningTick + TRIGGER_WINDOW) {
            if (lastEveningSoundDay != dayCount) {
                lastEveningSoundDay = dayCount;
                CURRENT_MOON_PHASE = level.getMoonPhase();
                playSound(false);
            }
        }

    }

    private static boolean getMoonPhase() {

        if (SoundsConfig.CURRENT_PHASE.get().equals("Full Moon"))
            return CURRENT_MOON_PHASE == 0;
        else
            return SoundsConfig.CURRENT_PHASE.get().equals("All");

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
                blockPos = GameUtils.getPlayer().getOnPos();
            }

        } catch (NullPointerException e) {
            SSAMod.LOGGER.error("SSA Mod can't get BlockPos, because player is null\n");
            e.fillInStackTrace();
            return;
        }

        MessageHandler.sendInformationMessage(GameUtils.getPlayer(), isRooster);

        if (PLAYER_CURRENT_DIMENSION != null && !PLAYER_CURRENT_DIMENSION.equals(OVERWORLD)
                && !SoundsConfig.PLAY_IN_OTHER_DIM.get())
            return;

        if (ModRegistry.ROOSTER_MORNING.isPresent() && isRooster
                || ModRegistry.WOLF_EVENING.isPresent() && !isRooster) {

            if (playInCave) {
                if (isRooster)
                    SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), chanceToPlay, volume, 1f);
                else {
                    if (getMoonPhase())
                        SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), chanceToPlay, volume, 1f);
                }
            } else {
                if (blockPos.getY() > 50) {
                    if (isRooster)
                        SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), chanceToPlay, volume,
                                1f);
                    else {
                        if (getMoonPhase())
                            SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), chanceToPlay, volume,
                                    1f);
                    }
                }
            }

        } else {
            if (isRooster)
                SSAMod.LOGGER.error("SSA Mod " + ModRegistry.ROOSTER_MORNING.getId() + " is missing");
            else
                SSAMod.LOGGER.error("SSA Mod " + ModRegistry.WOLF_EVENING.getId() + " is missing");
        }

    }

}
