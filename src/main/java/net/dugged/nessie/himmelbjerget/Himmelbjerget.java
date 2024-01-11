package net.dugged.nessie.himmelbjerget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(modid = Himmelbjerget.MOD_ID, name = Himmelbjerget.NAME, version = Himmelbjerget.VERSION)
public class Himmelbjerget {
	public static final String MOD_ID = "@MODID@";
	public static final String NAME = "@MODNAME@";
	public static final String VERSION = "@VERSION@";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final KeyBinding adjustRotationKey = new KeyBinding("Adjust rotation", Keyboard.KEY_R, "key.categories.misc");
	public static final KeyBinding scoreboardVisibilityKey = new KeyBinding("Toggle scoreboard visibility", Keyboard.KEY_Y, "key.categories.misc");

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.registerKeyBinding(adjustRotationKey);
		ClientRegistry.registerKeyBinding(scoreboardVisibilityKey);
	}

	@SubscribeEvent
	public void onKeyPressed(final InputEvent.KeyInputEvent event) {
		if (scoreboardVisibilityKey.isPressed()) {
			GuiIngameForge.renderObjective = !GuiIngameForge.renderObjective;
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text event) {
		if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
			final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
			final String rotationInfo = String.format("%+.3f %s/§r %+.3f", MathHelper.wrapAngleTo180_float(player.rotationYaw), adjustRotationKey.isKeyDown() ? "§c" : "", MathHelper.wrapAngleTo180_float(player.rotationPitch));
			event.right.add(rotationInfo);
		}
	}

	@SubscribeEvent
	public void onClientReceivedChat(final ClientChatReceivedEvent event) {
		final IChatComponent msg = event.message;
		final String text = EnumChatFormatting.getTextWithoutFormattingCodes(msg.getUnformattedText());
		if (text.startsWith("[NPC] Don Expresso") && !text.contains("I DON'T FEEL SO GOOD...")) {
			event.setCanceled(true);
			return;
		}


		if (text.contains("Guild")) {
			try {
				((IChatComponentText) msg).himmelbjerget$replaceFirstInText("Guild > ", "Ⓖ");
				((IChatComponentText) msg.getSiblings().get(0)).himmelbjerget$replaceFirstInText("Guild > ", "Ⓖ");
				return;
			} catch (final Throwable e) {
				LOGGER.info("not good", e);
			}

			LOGGER.info("-");
			LOGGER.info("{}", msg);
		}
	}
}
