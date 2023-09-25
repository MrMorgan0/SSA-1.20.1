package net.morgan.ssamod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SoundsConfig {

    public static ForgeConfigSpec.BooleanValue PLAY_IN_CAVE;
    public static ForgeConfigSpec.BooleanValue SEND_MESSAGES;
    public static ForgeConfigSpec.IntValue ROOSTER;
    public static ForgeConfigSpec.IntValue WOLF;

    public static void init(ForgeConfigSpec.Builder client) {

        PLAY_IN_CAVE = client.comment("Play sound in cave").define("play_in_cave", false);
        SEND_MESSAGES = client.comment("Send messages").define("send_messages", true);
        ROOSTER = client.comment("Rooster volume").defineInRange("rooster_volume", 50, 0, 100);
        WOLF = client.comment("Wolf volume").defineInRange("wolf_volume", 50, 0, 100);

    }

}
