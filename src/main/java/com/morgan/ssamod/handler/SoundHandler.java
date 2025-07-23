package com.morgan.ssamod.handler;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import com.morgan.ssamod.SunriseSunsetAudio;
import com.morgan.ssamod.util.GameUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class SoundHandler {

    public static int tempCount = 0;

    public static void playSoundForPlayerOnce(SoundEvent sound, int chance, float volume, float pitch) {

        Player player = GameUtils.getPlayer();

        if (player == null) return;

        if (volume != 0)
            volume = ((volume + GameUtils.getGameSettings().getSoundSourceVolume(SoundSource.MASTER)) / 2) / 100;

        try {
            if (calculateChance(chance)) player.playSound(sound, volume, pitch);
            tempCount++;
        } catch (IllegalStateException e) {
            SunriseSunsetAudio.LOGGER.error("SSA mod can't get access to player");
        }

    }

    private static boolean calculateChance(int chance) {

        int randomNumber = GameUtils.generateRandomNumber(101);

        if (chance == 100) return true;
        if (chance == 0) return false;

        return randomNumber <= chance;

    }

}
