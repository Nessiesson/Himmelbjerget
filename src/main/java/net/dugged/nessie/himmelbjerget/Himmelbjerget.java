package net.dugged.nessie.himmelbjerget;

import com.google.common.collect.EvictingQueue;
import net.dugged.nessie.himmelbjerget.mixins.IPositionedSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
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
import net.minecraftforge.event.world.WorldEvent;
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

import java.awt.*;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
@Mod(modid = Himmelbjerget.MOD_ID, name = Himmelbjerget.MOD_NAME)
public class Himmelbjerget {
	public static final String MOD_NAME = "Himmelbjerget";
	public static final String MOD_ID = "himmelbjerget";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final KeyBinding adjustRotationKey = new KeyBinding("Adjust Rotation", Keyboard.KEY_R, "key.categories.misc");
	public static final KeyBinding secondaryAttackKey = new KeyBinding("Attack/Destroy Secondary", -100, "key.categories.gameplay");
	public static final ToggleSettingKeyBinding lockMouseToggleKey = new ToggleSettingKeyBinding("Toggle Lock Mouse", Keyboard.KEY_NONE, "key.categories.misc");
	public static final ToggleSettingKeyBinding secondaryAttackToggleKey = new ToggleSettingKeyBinding("Toggle Secondary Attack/Destroy Key", Keyboard.KEY_NONE, "key.categories.misc");
	public static final ToggleSettingKeyBinding scoreboardVisibilityKey = new ToggleSettingKeyBinding("Toggle Scoreboard Visibility", Keyboard.KEY_Y, "key.categories.misc", () -> GuiIngameForge.renderObjective = !GuiIngameForge.renderObjective);
	public static final List<Integer> mspt = Arrays.asList(50, 20, 50, 20);
	private final EvictingQueue<Long> fastTimeUpdates = EvictingQueue.create(10);
	private final EvictingQueue<Double> lastSpeeds = EvictingQueue.create(100);
	private final MutablePair<Double, Double> speeds = new MutablePair<>(0D, 0D);

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.registerKeyBinding(adjustRotationKey);
		ClientRegistry.registerKeyBinding(secondaryAttackKey);
		ClientRegistry.registerKeyBinding(lockMouseToggleKey);
		ClientRegistry.registerKeyBinding(secondaryAttackToggleKey);
		ClientRegistry.registerKeyBinding(scoreboardVisibilityKey);
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
	@SubscribeEvent
	public void onRenderGameOverlayText(final RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.ALL && lockMouseToggleKey.isSettingEnabled) {
			final var width = event.resolution.getScaledWidth();
			final var height = event.resolution.getScaledHeight();
			Gui.drawRect(width - 4, height - 4, width - 2, height - 2, Color.RED.getRGB());
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

		if (event.type == 2) {
			if (msg instanceof ChatComponentText) {
				final var replace = (IChatComponentText) msg;
				replace.himmelbjerget$replaceFirstInText("✎ Mana", "✎");
				replace.himmelbjerget$replaceFirstInText("❈ Defense", "❈");
			}

			if (text.contains("❤")) {
				final var currentTime = System.nanoTime();
				this.fastTimeUpdates.add(currentTime);
				final var dt = currentTime - this.fastTimeUpdates.peek();
				final var mspt = (int) Math.max(50, dt * 1E-7 / this.fastTimeUpdates.size());
				final var tps = 1000 / mspt;
				Himmelbjerget.mspt.set(0, mspt);
				Himmelbjerget.mspt.set(1, tps);
			}
		}

		if (this.replaceAtStart(msg, text, "Coop >", "Ⓒ ⊳") || this.replaceAtStart(msg, text, "Guild >", "Ⓖ ⊳") || this.replaceAtStart(msg, text, "Friend >", "Ⓕ ⊳") || this.replaceAtStart(msg, text, "Party >", "Ⓟ ⊳")) {
			return;
		}
	}

	// TODO: Work out if this breaks any sounds that I care about.
	@SubscribeEvent
	public void onSoundEvent(final PlaySoundEvent event) {
		final var sound = event.sound;
		if ("random.orb".equals(event.name) && (sound.getVolume() == 0.5F || (sound.getVolume() == 1F && sound.getPitch() == 1.4920635F))) {
			((IPositionedSound) sound).setVolume(sound.getVolume() * 0.25F);
		}
	}

	@SubscribeEvent
	public void onWorldLoad(final WorldEvent.Load event) {
		final var handler = Minecraft.getMinecraft().getNetHandler();
		if (event.world.isRemote && handler != null) {
			this.fastTimeUpdates.clear();
			((INetHandlerPlayClient) handler).himmelbjerget$resetSlowTimeUpdates();
		}
	}

	private boolean replaceAtStart(final IChatComponent msg, final String haystack, final String needle, final String replacement) {
		if (haystack.startsWith(needle)) {
			try {
				((IChatComponentText) msg).himmelbjerget$replaceFirstInText(needle, replacement);
				((IChatComponentText) msg.getSiblings().get(0)).himmelbjerget$replaceFirstInText(needle, replacement);
				return true;
			} catch (final Throwable ignored) {
			}
		}

		return false;
	}
}
