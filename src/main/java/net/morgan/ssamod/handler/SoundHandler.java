package net.morgan.ssamod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundHandler {

    public static void playSoundForPlayer(SoundEvent sound, float volume, float pitch) {

        LocalPlayer player = Minecraft.getInstance().player;
        volume = ((volume +  Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MASTER)) / 2) / 100;

        if (player != null) {
            player.playSound(sound, volume, pitch);
        }

    }

}
