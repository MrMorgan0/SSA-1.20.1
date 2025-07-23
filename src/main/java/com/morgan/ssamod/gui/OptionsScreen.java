package com.morgan.ssamod.gui;

import com.morgan.ssamod.SunriseSunsetAudio;
import com.morgan.ssamod.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import com.morgan.ssamod.handler.MessageHandler;
import com.morgan.ssamod.handler.SoundHandler;
import com.morgan.ssamod.registery.ModRegistry;
import com.morgan.ssamod.util.GameUtils;
import com.morgan.ssamod.util.SmoothChasingValue;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.VersionChecker;
import net.neoforged.neoforge.client.gui.widget.ExtendedButton;
import net.neoforged.neoforge.client.gui.widget.ExtendedSlider;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class OptionsScreen extends Screen {

    private final Screen parent;
    private final SmoothChasingValue titleY;
    private final SmoothChasingValue updateX;
    private final SoundManager soundManager = GameUtils.getSoundManager();

    ExtendedSlider roosterVolumeSlider;
    ExtendedSlider wolfVolumeSlider;
    ExtendedSlider chanceOfPlayingWolfSound;
    ExtendedSlider chanceOfPlayingRoosterSound;
    ExtendedButton moonPhase;
    EditBox morningTick;
    EditBox eveningTick;
    Checkbox playInCave;
    Checkbox sendMessages;
    Checkbox playInOtherDim;
    Checkbox sendMessagesInOtherDim;

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

        roosterVolumeSlider = new ExtendedSlider(width / 2 + 30, height / 2 - 10, 80, 20, Component.translatable("gui.ssa.options_slider_rooster"),
                Component.empty(), 0d, 100d, Config.ROOSTER.get(), true);

        wolfVolumeSlider = new ExtendedSlider(width / 2 + 30, height / 2 - 40, 80, 20, Component.translatable("gui.ssa.options_slider_wolf"),
                Component.empty(), 0d, 100d, Config.WOLF.get(), true);

        chanceOfPlayingRoosterSound = new ExtendedSlider(width / 2 + 160, height / 2 - 10, 100, 20, Component.translatable("gui.ssa.options_chance_rooster"),
                Component.translatable("gui.ssa.options_percent"), 0d, 100d, Config.CHANCE_ROOSTER.get(),true);

        chanceOfPlayingWolfSound = new ExtendedSlider(width / 2 + 160, height / 2 - 40, 100, 20, Component.translatable("gui.ssa.options_chance_wolf"),
                Component.translatable("gui.ssa.options_percent"), 0d, 100d, Config.CHANCE_WOLF.get(),true);

        moonPhase = new ExtendedButton(width / 2 + 160, height / 2 + 20, 100, 20, Component.literal(Config.CURRENT_PHASE.get()), button -> {

            if (Config.CURRENT_PHASE.get().equals("All")) {
                moonPhase.setMessage(Component.literal("Full Moon"));
                Config.CURRENT_PHASE.set("Full Moon");
            } else if (Config.CURRENT_PHASE.get().equals("Full Moon")) {
                moonPhase.setMessage(Component.literal("All"));
                Config.CURRENT_PHASE.set("All");
            }

        });

        morningTick = new EditBox(font, width / 2 + 30, height / 2 + 20, 105, 20,
                Component.empty());

        eveningTick = new EditBox(font, width / 2 + 30, height / 2 + 50, 105, 20,
                Component.empty());

        playInCave = Checkbox.builder(Component.translatable("gui.ssa.options_play_cave"), font)
                .pos(width / 2 - 130, height / 2 - 10)
                .maxWidth(100)
                .selected(Config.PLAY_IN_CAVE.get())
                .build();

        sendMessages = Checkbox.builder(Component.translatable("gui.ssa.options_send_messages"), font)
                .pos(width / 2 - 130, height / 2 - 40)
                .maxWidth(100)
                .selected(Config.SEND_MESSAGES.get())
                .build();

        playInOtherDim = Checkbox.builder(Component.translatable("gui.ssa.options_play_in_other_dim"), font)
                .pos(width / 2 - 130, height / 2 + 80)
                .maxWidth(100)
                .selected(Config.PLAY_IN_OTHER_DIM.get())
                .build();

        sendMessagesInOtherDim = Checkbox.builder(Component.translatable("gui.ssa.options_send_messages_in_other_dim"), font)
                .pos(width / 2 - 130, height / 2 + 110)
                .maxWidth(100)
                .selected(Config.SEND_MESSAGES_IN_OTHER_DIM.get())
                .build();

        morningTick.setValue(Config.MORNING_TICK.get().toString());
        eveningTick.setValue(Config.EVENING_TICK.get().toString());

        //Sliders
        addRenderableWidget(roosterVolumeSlider);
        addRenderableWidget(chanceOfPlayingRoosterSound);
        addRenderableWidget(wolfVolumeSlider);
        addRenderableWidget(chanceOfPlayingWolfSound);
        //EditBox
        addRenderableWidget(morningTick);
        addRenderableWidget(eveningTick);
        //CheckBoxes
        addRenderableWidget(playInCave);
        addRenderableWidget(sendMessages);
        addRenderableWidget(playInOtherDim);
        addRenderableWidget(sendMessagesInOtherDim);

        addRenderableOnly((guiGraphics, i, i1, v) -> guiGraphics.drawString(font, Component.translatable("gui.ssa.options_morning_tick"), width / 2 - 130, height / 2 + 25, 0xFFFFFFFF));
        addRenderableOnly((guiGraphics, i, i1, v) -> guiGraphics.drawString(font, Component.translatable("gui.ssa.options_evening_tick"), width / 2 - 130, height / 2 + 55, 0xFFFFFFFF));
        addRenderableOnly((guiGraphics, i, i1, v) -> {

            Objects.requireNonNull(minecraft);

            boolean smallUI = minecraft.getWindow().getGuiScale() < 3;
            int left = width / 2 - 105;
            int top = height / 2 - 150;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(left, top, 0);

            float scale = smallUI ? 2F : 1.5F;
            guiGraphics.pose().scale(scale, scale, scale);

            titleY.tick(v);
            drawFancyText(guiGraphics, I18n.get("gui.ssa.options"), Math.min(titleY.value, 20F), 0, 0, 0);

            guiGraphics.pose().popPose();

            if (isModUpdateAvailable()) {
                updateX.tick(v);
                drawFancyText(guiGraphics, I18n.get("gui.ssa.options_update"), 0, Math.min(updateX.value, 50F), height / 2 - 80, width / 2 - 100);
            }
        });

        //Buttons
        addRenderableWidget(moonPhase).setTooltip(Tooltip.create(Component.translatable("gui.ssa.options_moon_phase_tooltip")));
        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_reset"), w -> {

            roosterVolumeSlider.setValue(Config.ROOSTER.getDefault());
            chanceOfPlayingRoosterSound.setValue(Config.CHANCE_ROOSTER.getDefault());
            wolfVolumeSlider.setValue(Config.WOLF.getDefault());
            chanceOfPlayingWolfSound.setValue(Config.CHANCE_WOLF.getDefault());
            morningTick.setValue(Config.MORNING_TICK.getDefault().toString());
            eveningTick.setValue(Config.EVENING_TICK.getDefault().toString());
            moonPhase.setMessage(Component.literal("All"));

            if (!sendMessages.selected()) sendMessages.onPress();
            if (playInCave.selected()) playInCave.onPress();
            if (playInOtherDim.selected()) playInOtherDim.onPress();
            if (sendMessagesInOtherDim.selected()) sendMessagesInOtherDim.onPress();

            saveConfig();

        }).bounds(width / 2 - 120, height / 2 + 140, 100, 20).build());

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, w -> onClose())
                .bounds(width / 2 + 30, height / 2 + 140, 100, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_play"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.ROOSTER_MORNING.get(), 100, roosterVolumeSlider.getValueInt(), 1f);
        }).bounds(width / 2 + 120, height / 2 - 10, 20, 20).build());

        addRenderableWidget(Button.builder(Component.translatable("gui.ssa.options_play"), w -> {
            soundManager.stop(ModRegistry.ROOSTER_MORNING.get().getLocation(), null);
            soundManager.stop(ModRegistry.WOLF_EVENING.get().getLocation(), null);
            SoundHandler.playSoundForPlayerOnce(ModRegistry.WOLF_EVENING.get(), 100,  wolfVolumeSlider.getValueInt(), 1f);
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
    public void renderBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }

    private boolean isModUpdateAvailable() {
        return SunriseSunsetAudio.MOD_VERSION.equals(VersionChecker.Status.OUTDATED.name());
    }

    private void drawFancyText(GuiGraphics guiGraphics, String text, float y, float x, int posY, int posX) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x, y, 0);
        guiGraphics.drawString(font, text, posX, posY, 0xFFFFFFFF);
        guiGraphics.pose().popPose();
    }

    private void saveConfig() {

        Config.ROOSTER.set((int) roosterVolumeSlider.getValue());
        Config.CHANCE_ROOSTER.set((int) chanceOfPlayingRoosterSound.getValue());
        Config.WOLF.set((int) wolfVolumeSlider.getValue());
        Config.CHANCE_WOLF.set((int) chanceOfPlayingWolfSound.getValue());
        Config.PLAY_IN_CAVE.set(playInCave.selected());
        Config.SEND_MESSAGES.set(sendMessages.selected());
        Config.PLAY_IN_OTHER_DIM.set(playInOtherDim.selected());
        Config.SEND_MESSAGES_IN_OTHER_DIM.set(sendMessagesInOtherDim.selected());
        Config.CURRENT_PHASE.set(moonPhase.getMessage().getString());

        try {

            int value = Integer.parseInt(morningTick.getValue());
            int tempValue = Integer.parseInt(eveningTick.getValue());

            if (value > 23999) Config.MORNING_TICK.set(23999);
            else Config.MORNING_TICK.set(Math.max(value, 10));

            if (value == tempValue) {
                MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_same_value").
                        withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
                Config.MORNING_TICK.set(Config.MORNING_TICK.getDefault());
                return;
            }

        } catch (NumberFormatException e) {

            Config.MORNING_TICK.set(Config.MORNING_TICK.getDefault());
            MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_invalid_value").
                    withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));

        }

        try {

            int value = Integer.parseInt(eveningTick.getValue());
            int tempValue = Integer.parseInt(morningTick.getValue());

            if (value > 23999) Config.EVENING_TICK.set(23999);
            else Config.EVENING_TICK.set(Math.max(value, 10));

            if (value == tempValue) {
                MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_same_value").
                        withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));
                Config.EVENING_TICK.set(Config.EVENING_TICK.getDefault());
            }

        } catch (NumberFormatException e) {

            Config.EVENING_TICK.set(Config.EVENING_TICK.getDefault());
            MessageHandler.sendMessage(GameUtils.getPlayer(), Component.translatable("messages.ssa.error_message_invalid_value").
                    withStyle(ChatFormatting.DARK_RED, ChatFormatting.BOLD));

        }

        Config.ROOSTER.save();
        Config.CHANCE_ROOSTER.save();
        Config.WOLF.save();
        Config.CHANCE_WOLF.save();
        Config.PLAY_IN_CAVE.save();
        Config.SEND_MESSAGES.save();
        Config.PLAY_IN_OTHER_DIM.save();
        Config.SEND_MESSAGES_IN_OTHER_DIM.save();
        Config.CURRENT_PHASE.save();

    }

}