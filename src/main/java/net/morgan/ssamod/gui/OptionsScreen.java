package net.morgan.ssamod.gui;

import java.util.Objects;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.morgan.ssamod.config.SoundsConfig;
import net.morgan.ssamod.util.SmoothChasingValue;
import org.jetbrains.annotations.NotNull;

public class OptionsScreen extends Screen {

	private final Screen parent;
	private final SmoothChasingValue titleY;

	ForgeSlider roosterVolumeSlider;
	ForgeSlider wolfVolumeSlider;
	Checkbox playInCave;

	public OptionsScreen(Screen parent) {
		super(Component.translatable("gui.ssa.options"));
		this.parent = parent;
		titleY = new SmoothChasingValue().start(8).target(32).withSpeed(0.1F);

	}

	@Override
	protected void init() {
		Objects.requireNonNull(minecraft);

		roosterVolumeSlider = new ForgeSlider(width / 2 + 50, height / 2 - 10, 130, 20, Component.empty(),
				Component.empty(), 0d, 100d, SoundsConfig.ROOSTER.get(), true);

		wolfVolumeSlider = new ForgeSlider(width / 2 + 50, height / 2 - 40, 130, 20, Component.empty(),
				Component.empty(), 0d, 100d, SoundsConfig.WOLF.get(), true);

		playInCave = new Checkbox(width / 2 + 50, height / 2 + 20, 130, 20, Component.translatable("gui.ssa.options_play_cave"),
				SoundsConfig.PLAY_IN_CAVE.get(), true);

		addRenderableWidget(roosterVolumeSlider);
		addRenderableWidget(wolfVolumeSlider);
		addRenderableWidget(playInCave);

		addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, w -> {
			onClose();
		}).bounds(width / 2 - 50, height / 2 + 50, 100, 20).build());
	}

	@Override
	public void onClose() {

		saveConfig();

		Objects.requireNonNull(minecraft).setScreen(parent);

	}

	@Override
	public void tick() {
		// TODO: 9/15/2023 Optional. Future tick volume adjustment
//		roosterVolumeValue = (int) roosterVolumeSlider.getValue();
//		wolfVolumeValue = (int) wolfVolumeSlider.getValue();
//		playInCaveValue = playInCave.selected();

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

		drawFancyTitle(guiGraphics, I18n.get("gui.ssa.options"), Math.min(titleY.value, 20F), 20F);

		guiGraphics.pose().popPose();

		guiGraphics.drawString(font, Component.translatable("gui.ssa.options_rooster"), width / 2 - 130 , height / 2, 0xFFFFFFFF);
		guiGraphics.drawString(font, Component.translatable("gui.ssa.options_wolf"), width / 2 - 130 , height / 2 - 30, 0xFFFFFFFF);

		super.render(guiGraphics, x, y, partialTicks);
	}

	private void drawFancyTitle(GuiGraphics guiGraphics, String text, float y, float expectY) {
		guiGraphics.pose().pushPose();
		guiGraphics.pose().translate(0, y, 0);
		guiGraphics.drawString(font, text, 0, 0, 0xFFFFFFFF);
		guiGraphics.pose().popPose();
	}

	private void saveConfig() {

		SoundsConfig.ROOSTER.set((int) roosterVolumeSlider.getValue());
		SoundsConfig.WOLF.set((int) wolfVolumeSlider.getValue());
		SoundsConfig.PLAY_IN_CAVE.set(playInCave.selected());

	}

}