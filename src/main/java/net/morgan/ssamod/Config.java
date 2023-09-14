package net.morgan.ssamod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;


@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue PLAY_IN_CAVE = BUILDER
            .comment("Play sound in cave?")
            .define("play_in_cave", false);

    private static final ForgeConfigSpec.IntValue ROOSTER = BUILDER
            .comment("Rooster volume")
            .defineInRange("rooster_volume", 30, 0, 100);

    private static final ForgeConfigSpec.IntValue WOLF = BUILDER
            .comment("Wolf volume")
            .defineInRange("wolf_volume", 50, 0, 100);


    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean playInCave;
    public static int rooster;
    public static int wolf;

    public static ModConfigEvent configEvent;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        playInCave = PLAY_IN_CAVE.get();
        rooster = ROOSTER.get();
        wolf = WOLF.get();

        configEvent = event;
    }

    public static void setRoosterVolume(double value) {
        ROOSTER.set((int) value);
    }
    public static void setWolfVolume(double value) {
        WOLF.set((int) value);
    }
    public static void setPlayCave(boolean value) {
        PLAY_IN_CAVE.set(value);
    }

    public static void save() {
        configEvent.getConfig().save();
    }

}
