package net.morgan.ssamod.gui;

import java.util.*;


import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.fml.VersionChecker;
import net.morgan.ssamod.handler.MessageHandler;
import net.morgan.ssamod.registery.ModRegistry;
import net.morgan.ssamod.SSAMod;
import net.morgan.ssamod.config.SoundsConfig;
import net.morgan.ssamod.handler.SoundHandler;
import net.morgan.ssamod.util.GameUtils;
import net.morgan.ssamod.util.SmoothChasingValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class OptionsScreen extends Screen {

    private final Screen parent;
    private final SmoothChasingValue titleY;
    private final SmoothChasingValue updateX;
    private final SoundManager soundManager = GameUtils.getSoundManager();

    ForgeSlider roosterVolumeSlider;
    ForgeSlider wolfVolumeSlider;
    EditBox morningTick;
    EditBox eveningTick;
    Checkbox playInCave;
    Checkbox sendMessages;

    public OptionsScreen(Screen parent) {
        super(Component.translatable("gui.ssa.options"));
        this.parent = parent;
        titleY = new SmoothChasingValue().start(8).target(32).withSpeed(0.1F);
        updateX = new SmoothChasingValue().start(8).target(32).withSpeed(0.2F);

    }

    @Override
    protected void init() {
        Objects.requireNonNull(minecraft);

        GameUtils.getMC().pauseGame(true);
        if (GameUtils.getMC().isSingleplayer()) soundManager.pause();

        roosterVolumeSlider = new ForgeSlider(width / 2 + 30, height / 2 - 10, 80, 20, Component.translatable("gui.ssa.options_slider_rooster"),
                Component.empty(), 0d, 100d, SoundsConfig.ROOSTER.get(), true);

        wolfVolumeSlider = new ForgeSlider(width / 2 + 30, height / 2 - 40, 80, 20, Component.translatable("gui.ssa.options_slider_wolf"),
                Component.empty(), 0d, 100d, SoundsConfig.WOLF.get(), true);

        morningTick = new EditBox(font, width / 2 + 30, height / 2 + 20, 105, 20,
                Component.empty());

        eveningTick = new EditBox(font, width / 2 + 30, height / 2 + 50, 105, 20,
                Component.empty());

        playInCave = new Checkbox(width / 2 - 130, height / 2 - 10, 130, 20, Component.translatable("gui.ssa.options_play_cave"),
                SoundsConfig.PLAY_IN_CAVE.get(), true);

        sendMessages = new Checkbox(width / 2 - 130, height / 2 - 40, 130, 20, Component.translatable("gui.ssa.options_send_messages"),
                SoundsConfig.SEND_MESSAGES.get(), true);

        morningTick.setValue(SoundsConfig.MORNING_TICK.get().toString());
        eveningTick.setValue(SoundsConfig.EVENING_TICK.get().toString());

        //Sliders
        addRenderableWidget(roosterVolumeSlider);
        addRenderableWidget(wolfVolumeSlider);
        //EditBox
        addRenderableWidget(morningTick);
        addRenderableWidget(eveningTick);
        //CheckBoxes
        addRenderableWidget(playInCave);
        addRenderableWidget(sendMessages);

        //Buttons
        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_reset"), w -> {

            roosterVolumeSlider.setValue(SoundsConfig.ROOSTER.getDefault());
            wolfVolumeSlider.setValue(SoundsConfig.WOLF.getDefault());
            morningTick.setValue(SoundsConfig.MORNING_TICK.getDefault().toString());
            eveningTick.setValue(SoundsConfig.EVENING_TICK.getDefault().toString());

            saveConfig();

        }).bounds(width / 2 - 120, height / 2 + 80, 100, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, w -> {
            onClose();
        }).bounds(width / 2 + 30, height / 2 + 80, 100, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_play"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), roosterVolumeSlider.getValueInt(), 1f);
        }).bounds(width / 2 + 120, height / 2 - 10, 20, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_play"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), wolfVolumeSlider.getValueInt(), 1f);
        }).bounds(width / 2 + 120, height / 2 - 40, 20, 20).build());

    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

        if (pKeyCode == GLFW.GLFW_KEY_ENTER || pKeyCode == GLFW.GLFW_KEY_KP_ENTER) onClose();

        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void onClose() {
        saveConfig();

        GameUtils.getMC().pauseGame(false);

        soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
        soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);

        if (GameUtils.getMC().isSingleplayer()) soundManager.resume();

        Objects.requireNonNull(minecraft).setScreen(parent);

    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int x, int y, float partialTicks) {
        Objects.requireNonNull(minecraft);
        renderBackground(guiGraphics);
        boolean smallUI = minecraft.getWindow().getGuiScale() < 3;
        int left = width / 2 - 105;
        int top = height / 2 - 150;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(left, top, 0);

        float scale = smallUI ? 2F : 1.5F;
        guiGraphics.pose().scale(scale, scale, scale);

        titleY.tick(partialTicks);
        drawFancyText(guiGraphics, I18n.get("gui.ssa.options"), Math.min(titleY.value, 20F), 0, 0, 0);

        guiGraphics.pose().popPose();

        guiGraphics.drawString(font, Component.translatable("gui.ssa.options_morning_tick"), width / 2 - 130, height / 2 + 25, 0xFFFFFFFF);
        guiGraphics.drawString(font, Component.translatable("gui.ssa.options_evening_tick"), width / 2 - 130, height / 2 + 55, 0xFFFFFFFF);

        if (isModUpdateAvailable()) {
            updateX.tick(partialTicks);
            drawFancyText(guiGraphics, I18n.get("gui.ssa.options_update"), 0, Math.min(updateX.value, 50F), height / 2 - 80, width / 2 - 100);
        }

        super.render(guiGraphics, x, y, partialTicks);
    }

    private boolean isModUpdateAvailable() {
        return SSAMod.MOD_VERSION.equals(VersionChecker.Status.OUTDATED.name());
    }

    private void drawFancyText(GuiGraphics guiGraphics, String text, float y, float x, int posY, int posX) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y, 0);
        guiGraphics.drawString(font, text, posX, posY, 0xFFFFFFFF);
        guiGraphics.pose().popPose();
    }

    private void saveConfig() {

        SoundsConfig.ROOSTER.set((int) roosterVolumeSlider.getValue());
        SoundsConfig.WOLF.set((int) wolfVolumeSlider.getValue());
        SoundsConfig.PLAY_IN_CAVE.set(playInCave.selected());
        SoundsConfig.SEND_MESSAGES.set(sendMessages.selected());

        try {

            int value = Integer.parseInt(morningTick.getValue());
            int tempValue = Integer.parseInt(eveningTick.getValue());

            if (value > 23999) SoundsConfig.MORNING_TICK.set(23999);
            else SoundsConfig.MORNING_TICK.set(Math.max(value, 10));

            if (value == tempValue) {
                MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_same_value").
                        withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
                SoundsConfig.MORNING_TICK.set(SoundsConfig.MORNING_TICK.getDefault());
                return;
            }

        } catch (NumberFormatException e) {

            SoundsConfig.MORNING_TICK.set(SoundsConfig.MORNING_TICK.getDefault());
            MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_invalid_value").
                    withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));

        }

        try {

            int value = Integer.parseInt(eveningTick.getValue());
            int tempValue = Integer.parseInt(morningTick.getValue());

            if (value > 23999) SoundsConfig.EVENING_TICK.set(23999);
            else SoundsConfig.EVENING_TICK.set(Math.max(value, 10));

            if (value == tempValue) {
                MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_same_value").
                        withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
                SoundsConfig.EVENING_TICK.set(SoundsConfig.EVENING_TICK.getDefault());
            }

        } catch (NumberFormatException e) {

            SoundsConfig.EVENING_TICK.set(SoundsConfig.EVENING_TICK.getDefault());
            MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_invalid_value").
                    withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));

        }

    }

}