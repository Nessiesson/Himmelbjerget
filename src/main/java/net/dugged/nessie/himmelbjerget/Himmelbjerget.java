package net.dugged.nessie.himmelbjerget;

import net.dugged.nessie.himmelbjerget.mixins.IPositionedSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Mod(modid = Himmelbjerget.MOD_ID, name = Himmelbjerget.MOD_NAME)
public class Himmelbjerget {
	public static final String MOD_NAME = "Himmelbjerget";
	public static final String MOD_ID = "himmelbjerget";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final KeyBinding adjustRotationKey = new KeyBinding("Adjust Rotation", Keyboard.KEY_R, "key.categories.misc");
	public static final KeyBinding secondaryAttackKey = new KeyBinding("Attack/Destroy Secondary", -100, "key.categories.gameplay");
	public static final ToggleSettingKeyBinding secondaryAttackToggleKey = new ToggleSettingKeyBinding("Toggle Secondary Attack/Destroy Key", Keyboard.KEY_NONE, "key.categories.misc");
	public static final ToggleSettingKeyBinding scoreboardVisibilityKey = new ToggleSettingKeyBinding("Toggle Scoreboard Visibility", Keyboard.KEY_Y, "key.categories.misc", () -> GuiIngameForge.renderObjective = !GuiIngameForge.renderObjective);
	public static List<Integer> mspt = Arrays.asList(50, 20, 50, 20);

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.registerKeyBinding(adjustRotationKey);
		ClientRegistry.registerKeyBinding(secondaryAttackKey);
		ClientRegistry.registerKeyBinding(secondaryAttackToggleKey);
		ClientRegistry.registerKeyBinding(scoreboardVisibilityKey);

		final File[] files = Minecraft.getMinecraft().mcDataDir.listFiles(f -> f.isFile() && f.getName().startsWith("hs_err_pid"));
		if (files != null) {
			Arrays.stream(files).forEach(File::delete);
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text event) {
		final var mc = Minecraft.getMinecraft();
		final var player = mc.thePlayer;
		final var dX = player.posX - player.prevPosX;
		final var dY = player.posY - player.prevPosY;
		final var dZ = player.posZ - player.prevPosZ;
		final var speed = 20D * MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ);
		if (mc.gameSettings.showDebugInfo) {
			event.left.set(5, String.format("%s, v: %+.2f", event.left.get(5), speed));
		}
	}

	@SubscribeEvent
	public void onClientReceivedChat(final ClientChatReceivedEvent event) {
		final IChatComponent msg = event.message;
		final String text = EnumChatFormatting.getTextWithoutFormattingCodes(msg.getUnformattedText()).trim();
		if (text.startsWith("[NPC] Don Expresso") && !text.contains("I DON'T FEEL SO GOOD...")) {
			event.setCanceled(true);
			return;
		}

		if (text.contains("Guild >")) {
			try {
				((IChatComponentText) msg).himmelbjerget$replaceFirstInText("Guild > ", "Ⓖ");
				((IChatComponentText) msg.getSiblings().get(0)).himmelbjerget$replaceFirstInText("Guild > ", "Ⓖ");
			} catch (final Throwable ignored) {
			}

			return;
		}
	}

	// TODO: Work out if this breaks any sounds that I care about.
	@SubscribeEvent
	public void onSoundEvent(final PlaySoundEvent event) {
		final var sound = event.sound;
		if (sound.getVolume() == 0.5F && "random.orb".equals(event.name)) {
			((IPositionedSound) sound).setVolume(sound.getVolume() * 0.25F);
		}
	}
}
