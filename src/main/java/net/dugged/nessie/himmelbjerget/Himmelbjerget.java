package net.dugged.nessie.himmelbjerget;

import net.dugged.nessie.himmelbjerget.mixins.IPositionedSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
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
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.ArrayList;
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
	public static final List<Integer> mspt = Arrays.asList(50, 20, 50, 20);
	private final List<Long> lastTimeUpdates = new ArrayList<>();
	private final List<Double> lastSpeeds = new ArrayList<>();
	private final MutablePair<Double, Double> speeds = new MutablePair<>(0D, 0D);

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
	public void onPlayerTick(final TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && event.player instanceof EntityPlayerSP) {
			final var player = event.player;
			final var dX = player.posX - player.prevPosX;
			final var dY = player.posY - player.prevPosY;
			final var dZ = player.posZ - player.prevPosZ;
			final var speed = 20D * MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ);

			this.lastSpeeds.add(speed);
			if (this.lastSpeeds.size() > 100) {
				this.lastSpeeds.remove(0);
			}

			this.speeds.setLeft(speed);
			this.speeds.setRight(this.lastSpeeds.stream().mapToDouble(s -> s).average().orElse(0D));
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text event) {
		if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			event.left.set(5, String.format("%s, v: %+.2f, %+.2f", event.left.get(5), this.speeds.getLeft(), this.speeds.getRight()));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST) // run last to let other mods do whatever they need to
	public void onClientReceivedChat(final ClientChatReceivedEvent event) {
		final IChatComponent msg = event.message;
		final String text = EnumChatFormatting.getTextWithoutFormattingCodes(msg.getUnformattedText()).trim();
		if (text.startsWith("[NPC] Don Expresso") && !text.contains("I DON'T FEEL SO GOOD...")) {
			event.setCanceled(true);
			return;
		}

		if (event.type == 2 && text.contains("❤")) {
			if (msg instanceof ChatComponentText) {
				final var replace = (IChatComponentText) msg;
				replace.himmelbjerget$replaceFirstInText("✎ Mana", "✎");
				replace.himmelbjerget$replaceFirstInText("❈ Defense", "❈");
			}

			final var currentTime = System.nanoTime();
			this.lastTimeUpdates.add(currentTime);
			final var size = this.lastTimeUpdates.size();

			final var shortIndex = Math.max(0, size - 6);
			final var shortDt = currentTime - this.lastTimeUpdates.get(shortIndex);
			final var shortMspt = (int) Math.max(50, shortDt * 1E-7 / Math.max(shortIndex, 1D));
			final var shortTps = 1000 / shortMspt;
			Himmelbjerget.mspt.set(0, shortMspt);
			Himmelbjerget.mspt.set(1, shortTps);

			final var longDt = currentTime - this.lastTimeUpdates.get(0);
			final var longMspt = (int) Math.max(50, longDt * 1E-7 / size);
			final var longTps = 1000 / longMspt;
			Himmelbjerget.mspt.set(2, longMspt);
			Himmelbjerget.mspt.set(3, longTps);

			if (size >= 100) {
				this.lastTimeUpdates.remove(0);
			}
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
