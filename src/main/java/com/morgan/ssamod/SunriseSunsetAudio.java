package com.morgan.ssamod;

import com.morgan.ssamod.config.Config;
import com.morgan.ssamod.registery.ModRegistry;
import net.neoforged.fml.ModList;
import net.neoforged.fml.VersionChecker;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;

@Mod(SunriseSunsetAudio.MODID)
public class SunriseSunsetAudio {
    public static final String MODID = "ssamod";
    public static final String MOD_VERSION = VersionChecker.getResult(ModList.get().getModContainerById(SunriseSunsetAudio.MODID).
            orElseThrow().getModInfo()).status().name();
    public static final Logger LOGGER = LogUtils.getLogger();

    public SunriseSunsetAudio(IEventBus modEventBus, ModContainer modContainer) {

        ModRegistry.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

}
