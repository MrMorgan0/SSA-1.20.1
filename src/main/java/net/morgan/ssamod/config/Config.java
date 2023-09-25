package net.morgan.ssamod.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.morgan.ssamod.SSAMod;

import java.io.File;


@Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CONFIG;

    static {

        SoundsConfig.init(BUILDER);

        CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {

        SSAMod.LOGGER.info("SSA Loading Config... " + path);
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        SSAMod.LOGGER.info("SSA Built Config " + path);
        file.load();
        SSAMod.LOGGER.info("SSA Loaded Config " + path);
        config.setConfig(file);
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {

    }

}
