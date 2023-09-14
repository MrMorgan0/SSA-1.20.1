package net.morgan.ssamod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_SSA = "key.category.ssamod";
    public static final String KEY_CATEGORY_OPEN_OPTIONS = "key.category.ssamod.openoptions";

    public static final KeyMapping OPTIONS_KEY = new KeyMapping(KEY_CATEGORY_OPEN_OPTIONS, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_KP_0, KEY_CATEGORY_SSA);

}
