package net.morgan.ssamod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
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

        assert player != null;
        if (worldTime == 0) {
            float roosterVolume = SoundsConfig.ROOSTER.get() / 100.0f;
            player.playSound(ModRegistry.ROOSTER_MORNING.get(), roosterVolume, 1f);
            //event.level.playSound(player, player.getOnPos(), ModRegistry.ROOSTER_MORNING.get(), SoundSource.AMBIENT, 0.9f, 1f);
        } else if (worldTime == 13000) {
            float wolfVolume = SoundsConfig.WOLF.get() / 100.0f;
            player.playSound(ModRegistry.WOLF_EVENING.get(), wolfVolume, 1f);
            //event.level.playSound(player, player.getOnPos(), ModRegistry.WOLF_EVENING.get(), SoundSource.AMBIENT, 0.1f, 1f);
        }

    }

}
