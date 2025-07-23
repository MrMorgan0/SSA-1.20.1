package com.morgan.ssamod.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {

    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.BooleanValue PLAY_IN_CAVE = BUILDER.comment("Play sound in cave").define("play_in_cave", false);
    public static ModConfigSpec.BooleanValue PLAY_IN_OTHER_DIM = BUILDER.comment("Play sound in other dimensions").define("play_in_other_dim", false);
    public static ModConfigSpec.BooleanValue SEND_MESSAGES = BUILDER.comment("Send messages").define("send_messages", true);
    public static ModConfigSpec.BooleanValue SEND_MESSAGES_IN_OTHER_DIM = BUILDER.comment("Send messages in other dimensions").define("send_messages_other_dim", false);
    public static ModConfigSpec.IntValue ROOSTER = BUILDER.comment("Rooster volume").defineInRange("rooster_volume", 50, 0, 100);
    public static ModConfigSpec.IntValue WOLF = BUILDER.comment("Wolf volume").defineInRange("wolf_volume", 50, 0, 100);
    public static ModConfigSpec.IntValue MORNING_TICK = BUILDER.comment("When should crow Rooster? (time in ticks)").defineInRange("morning_tick", 100, 10, 23999);
    public static ModConfigSpec.IntValue EVENING_TICK = BUILDER.comment("When should howl Wolf? (time in ticks)").defineInRange("evening_tick", 13000, 10, 23999) ;
    public static ModConfigSpec.IntValue CHANCE_ROOSTER = BUILDER.comment("Chance to play Rooster sound").defineInRange("chance_rooster", 100, 0, 100);
    public static ModConfigSpec.IntValue CHANCE_WOLF = BUILDER.comment("Chance to play Wolf sound").defineInRange("chance_wolf", 100, 0, 100);
    public static ModConfigSpec.ConfigValue<String> CURRENT_PHASE = BUILDER.comment("Current moon phase").define("moon_phase", "All");

    public static final ModConfigSpec SPEC = BUILDER.build();

}
