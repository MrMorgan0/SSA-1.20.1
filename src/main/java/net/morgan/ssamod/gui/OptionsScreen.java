package net.morgan.ssamod.gui;

import java.util.*;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.fml.VersionChecker;
import net.morgan.ssamod.ModRegistry;
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

        roosterVolumeSlider = new ForgeSlider(width / 2 + 50, height / 2 - 10, 130, 20, Component.empty(),
                Component.empty(), 0d, 100d, SoundsConfig.ROOSTER.get(), true);

        wolfVolumeSlider = new ForgeSlider(width / 2 + 50, height / 2 - 40, 130, 20, Component.empty(),
                Component.empty(), 0d, 100d, SoundsConfig.WOLF.get(), true);

        playInCave = new Checkbox(width / 2 + 50, height / 2 + 20, 130, 20, Component.translatable("gui.ssa.options_play_cave"),
                SoundsConfig.PLAY_IN_CAVE.get(), true);

        sendMessages = new Checkbox(width / 2 + 50, height / 2 + 50, 130, 20, Component.translatable("gui.ssa.options_send_messages"),
                SoundsConfig.SEND_MESSAGES.get(), true);

        addRenderableWidget(roosterVolumeSlider);
        addRenderableWidget(wolfVolumeSlider);
        addRenderableWidget(playInCave);
        addRenderableWidget(sendMessages);

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, w -> {
            onClose();
        }).bounds(width / 2 - 50, height / 2 + 80, 100, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_rooster"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), roosterVolumeSlider.getValueInt(), 1f);
        }).bounds(width / 2 - 100, height / 2 + 20, 100, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_wolf"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), wolfVolumeSlider.getValueInt(), 1f);
        }).bounds(width / 2 - 100, height / 2 + 50, 100, 20).build());

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
        renderBackground(guiGraphics, x, y, partialTicks);
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

        guiGraphics.drawString(font, Component.translatable("gui.ssa.options_rooster"), width / 2 - 130, height / 2, 0xFFFFFFFF);
        guiGraphics.drawString(font, Component.translatable("gui.ssa.options_wolf"), width / 2 - 130, height / 2 - 30, 0xFFFFFFFF);

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

    }

}