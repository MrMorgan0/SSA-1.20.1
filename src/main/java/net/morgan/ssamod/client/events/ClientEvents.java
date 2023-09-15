package net.morgan.ssamod.client.events;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.gui.OptionsScreen;
import net.morgan.ssamod.util.KeyBinding;



public class ClientEvents {

    @Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput (InputEvent.Key event) {

            if (KeyBinding.OPTIONS_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new OptionsScreen(null));
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
