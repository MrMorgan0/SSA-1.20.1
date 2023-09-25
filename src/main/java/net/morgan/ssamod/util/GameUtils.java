package net.morgan.ssamod.util;

import net.minecraft.client.Options;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.CameraType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class GameUtils {
    private GameUtils() {

    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static Player getPlayer() {
        return getMC().player;
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public static ClientLevel getWorld() {
        return getMC().level;
    }

    @OnlyIn(Dist.CLIENT)
    @Nonnull
    public static Minecraft getMC() {
        return Minecraft.getInstance();
    }

    @OnlyIn(Dist.CLIENT)
    @Nonnull
    public static Options getGameSettings() {
        return getMC().options;
    }

    @OnlyIn(Dist.CLIENT)
    @Nonnull
    public static SoundManager getSoundManager() {
        return getMC().getSoundManager();
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isInGame() {
        return getWorld() != null && getPlayer() != null;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isThirdPersonView() {
        return getGameSettings().getCameraType() != CameraType.FIRST_PERSON;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isFirstPersonView() {
        return getGameSettings().getCameraType() == CameraType.FIRST_PERSON;
    }
}
