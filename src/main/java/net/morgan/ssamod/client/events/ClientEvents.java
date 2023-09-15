package net.morgan.ssamod.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.gui.OptionsScreen;
import net.morgan.ssamod.util.KeyBinding;
import org.lwjgl.glfw.GLFW;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = SSAMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput (InputEvent.Key event) {

            if (KeyBinding.OPTIONS_KEY.consumeClick()) {
                Minecraft.getInstance().setScreen(new OptionsScreen(null));
            }

            if (event.getKey() == GLFW.GLFW_KEY_KP_5) {

                int dayCount = (int) Math.floor((double) Minecraft.getInstance().level.getDayTime() / 24000);

                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Dau count is: " +
                        dayCount));
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Day Time is: " +
                        Minecraft.getInstance().level.getDayTime()));
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
