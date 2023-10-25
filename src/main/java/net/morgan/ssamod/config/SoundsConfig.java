package net.morgan.ssamod.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class SoundsConfig {

    public static ForgeConfigSpec.BooleanValue PLAY_IN_CAVE;
    public static ForgeConfigSpec.BooleanValue PLAY_IN_OTHER_DIM;
    public static ForgeConfigSpec.BooleanValue SEND_MESSAGES;
    public static ForgeConfigSpec.BooleanValue SEND_MESSAGES_IN_OTHER_DIM;
    public static ForgeConfigSpec.IntValue ROOSTER;
    public static ForgeConfigSpec.IntValue WOLF;
    public static ForgeConfigSpec.IntValue MORNING_TICK;
    public static ForgeConfigSpec.IntValue EVENING_TICK;

    public static void init(ForgeConfigSpec.Builder client) {

        PLAY_IN_CAVE = client.comment("Play sound in cave").define("play_in_cave", false);
        PLAY_IN_OTHER_DIM = client.comment("Play sound in other dimensions").define("play_in_other_dim", false);
        SEND_MESSAGES = client.comment("Send messages").define("send_messages", true);
        SEND_MESSAGES_IN_OTHER_DIM = client.comment("Send messages in other dimensions").define("send_messages_other_dim", false);
        ROOSTER = client.comment("Rooster volume").defineInRange("rooster_volume", 50, 0, 100);
        WOLF = client.comment("Wolf volume").defineInRange("wolf_volume", 50, 0, 100);
        MORNING_TICK = client.comment("When should crow Rooster? (time in ticks)").defineInRange("morning_tick", 100, 10, 23999);
        EVENING_TICK = client.comment("When should howl Wolf? (time in ticks)").defineInRange("evening_tick", 13000, 10, 23999);

    }

}