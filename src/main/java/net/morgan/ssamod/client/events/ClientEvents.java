package net.morgan.ssamod.client.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.gui.OptionsScreen;
import net.morgan.ssamod.handler.TimeHandler;
import net.morgan.ssamod.util.GameUtils;
import net.morgan.ssamod.util.KeyBinding;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

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
            SSAMod.LOGGER.info("Dimension changed to" + event.getDimension());

        }

        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event) {

            if (TimeHandler.PLAYER_CURRENT_DIMENSION == null ||
                    !TimeHandler.PLAYER_CURRENT_DIMENSION.equals(event.getLevel().dimension())) {

                TimeHandler.PLAYER_CURRENT_DIMENSION = event.getLevel().dimension();
                SSAMod.LOGGER.info("Joined to" + event.getLevel().dimension());

            }

        }

    }

    @Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            event.register(KeyBinding.OPTIONS_KEY);

        }

    }


}
