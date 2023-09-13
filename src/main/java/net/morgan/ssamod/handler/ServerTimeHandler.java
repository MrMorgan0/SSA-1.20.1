package net.morgan.ssamod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.ModSounds;
import net.morgan.ssamod.SSAMod;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID)
public final class ServerTimeHandler {

    @SubscribeEvent
    public static void onWorldTick(final TickEvent.ServerTickEvent.LevelTickEvent event) {

        long worldTime = event.level.getLevelData().getDayTime();
        LocalPlayer player = Minecraft.getInstance().player;

        if (worldTime == 2000) {
            event.level.playSound(player, Objects.requireNonNull(player).getOnPos(), ModSounds.ROOSTER_MORNING.get(), SoundSource.WEATHER, 0.5f, 0f);
        } else if (worldTime == 12000) {
            event.level.playSound(player, Objects.requireNonNull(player).getOnPos(), ModSounds.WOLF_EVENING.get(), SoundSource.WEATHER, 0.5f, 0f);
        }

    }

}
