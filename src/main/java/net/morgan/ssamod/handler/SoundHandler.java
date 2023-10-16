package net.morgan.ssamod.handler;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.util.GameUtils;

@OnlyIn(Dist.CLIENT)
public class SoundHandler {

    public static int tempCount = 0;

    public static void playSoundForPlayerOnce(SoundEvent sound, float volume, float pitch) {

        Player player = GameUtils.getPlayer();
        if (player == null) return;

        if (volume != 0)
            volume = ((volume + GameUtils.getGameSettings().getSoundSourceVolume(SoundSource.MASTER)) / 2) / 100;

        try {
            player.playSound(sound, volume, pitch);
            tempCount++;
        } catch (IllegalStateException e) {
            SSAMod.LOGGER.error("SSA mod can't get access to player");
        }

    }

}
