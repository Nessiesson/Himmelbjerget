package net.dugged.nessie.himmelbjerget;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Himmelbjerget implements ClientModInitializer {
	public static final String MOD_NAME = "Himmelbjerget";
	public static final String MOD_ID = "himmelbjerget";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final KeyMapping.Category CATEGORY_MISC = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "misc"));

	public static final KeyMapping adjustRotationKey = new KeyMapping("key.himmelbjerget.adjust_rotation", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, CATEGORY_MISC);
	public static final KeyMapping secondaryAttackKey = new KeyMapping("key.himmelbjerget.secondary_attack", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_MISC);
	public static final KeyMapping secondaryChatKey = new KeyMapping("key.himmelbjerget.secondary_chat", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_MISC);

	private static final List<ToggleSettingKeyBinding> keybinds = new ArrayList<>();
	public static final ToggleSettingKeyBinding lockMouseToggleKey = registerToggleKeyBind("key.himmelbjerget.toggle_lock_mouse");
	public static final ToggleSettingKeyBinding secondaryAttackToggleKey = registerToggleKeyBind("key.himmelbjerget.toggle_secondary_attack");


	@Override
	public void onInitializeClient() {
		KeyMappingHelper.registerKeyMapping(adjustRotationKey);
		KeyMappingHelper.registerKeyMapping(secondaryAttackKey);
		KeyMappingHelper.registerKeyMapping(secondaryChatKey);
		keybinds.forEach(KeyMappingHelper::registerKeyMapping);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			keybinds.forEach(kb -> {
				while (kb.consumeClick()) {
					kb.toggle();
				}
			});

			final var screen = client.screen;
			if (screen == null && secondaryChatKey.consumeClick()) {
				client.setScreen(new ChatScreen("", false));
			}
		});

		HudElementRegistry.addLast(Identifier.fromNamespaceAndPath(MOD_ID, "overlay"), Himmelbjerget::extractOverlay);
	}

	private static void extractOverlay(final GuiGraphicsExtractor graphics, final net.minecraft.client.DeltaTracker deltaTracker) {
		final var minecraft = Minecraft.getInstance();
		if (lockMouseToggleKey.isSettingEnabled) {
			final var width = graphics.guiWidth();
			final var height = graphics.guiHeight();
			graphics.fill(width - 4, height - 4, width - 2, height - 2, Color.RED.getRGB());
		}
	}

	private static ToggleSettingKeyBinding registerToggleKeyBind(final String translationKey) {
		return registerToggleKeyBind(translationKey, () -> {
		});
	}

	private static ToggleSettingKeyBinding registerToggleKeyBind(final String translationKey, final Runnable onToggle) {
		final var keybind = new ToggleSettingKeyBinding(translationKey, GLFW.GLFW_KEY_UNKNOWN, CATEGORY_MISC, onToggle);
		keybinds.add(keybind);
		return keybind;
	}
}