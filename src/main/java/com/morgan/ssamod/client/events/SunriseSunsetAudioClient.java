package com.morgan.ssamod.client.events;

import com.morgan.ssamod.SunriseSunsetAudio;
import com.morgan.ssamod.gui.OptionsScreen;
import com.morgan.ssamod.handler.TimeHandler;
import com.morgan.ssamod.util.GameUtils;
import com.morgan.ssamod.util.KeyBinding;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

@Mod(value = SunriseSunsetAudio.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = SunriseSunsetAudio.MODID, value = Dist.CLIENT)
public class SunriseSunsetAudioClient {

    public SunriseSunsetAudioClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {


    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {

        if (KeyBinding.OPTIONS_KEY.consumeClick()) {
            GameUtils.getMC().setScreen(new OptionsScreen(null));
        }

    }

    @SubscribeEvent
    public static void onDimensionChange(EntityTravelToDimensionEvent event) {

        TimeHandler.PLAYER_CURRENT_DIMENSION = event.getDimension();
        //SSAMod.LOGGER.info("Dimension changed to" + event.getDimension()); Commented to reduce lags in long runs

    }

    @SubscribeEvent
    public static void onJoinLevel(EntityJoinLevelEvent event) {

        if (TimeHandler.PLAYER_CURRENT_DIMENSION == null ||
                !TimeHandler.PLAYER_CURRENT_DIMENSION.equals(event.getLevel().dimension())) {

            TimeHandler.PLAYER_CURRENT_DIMENSION = event.getLevel().dimension();
            //SSAMod.LOGGER.info("Joined to" + event.getLevel().dimension()); Commented to reduce lags in long runs

        }

    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {

        event.register(KeyBinding.OPTIONS_KEY);

    }

}
