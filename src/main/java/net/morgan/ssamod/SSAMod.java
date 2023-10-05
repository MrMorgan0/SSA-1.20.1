package net.morgan.ssamod;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.NetworkConstants;
import net.morgan.ssamod.config.Config;
import net.morgan.ssamod.handler.SoundHandler;
import net.morgan.ssamod.registery.ModRegistry;
import org.slf4j.Logger;


@Mod(SSAMod.MOD_ID)
public class SSAMod {
    public static final String MOD_ID = "ssamod";
    public static final String MOD_VERSION = VersionChecker.getResult(ModList.get().getModContainerById(SSAMod.MOD_ID).
            orElseThrow().getModInfo()).status().name();
    public static final Logger LOGGER = LogUtils.getLogger();

    public SSAMod() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ModRegistry.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("ssamod-client.toml").toString());

        DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> SoundHandler::new);
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () ->
                new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));

    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

}
