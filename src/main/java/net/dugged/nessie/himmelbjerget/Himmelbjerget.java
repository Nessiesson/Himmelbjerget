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

import java.io.File;
import java.util.Arrays;

@Mod(modid = Himmelbjerget.MOD_ID, name = Himmelbjerget.MOD_NAME)
public class Himmelbjerget {
	public static final String MOD_NAME = "Himmelbjerget";
	public static final String MOD_ID = "himmelbjerget";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final KeyBinding adjustRotationKey = new KeyBinding("Adjust rotation", Keyboard.KEY_R, "key.categories.misc");
	public static final KeyBinding scoreboardVisibilityKey = new KeyBinding("Toggle scoreboard visibility", Keyboard.KEY_Y, "key.categories.misc");

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		ClientRegistry.registerKeyBinding(adjustRotationKey);
		ClientRegistry.registerKeyBinding(scoreboardVisibilityKey);
		ClientRegistry.registerTileEntity(MonolithHighlight.TileEntityMonolith.class, "himmelbjerget:monolith", new MonolithHighlight.TileEntityMonolithRenderer());

		final File[] files = Minecraft.getMinecraft().mcDataDir.listFiles(f -> f.isFile() && f.getName().startsWith("hs_err_pid"));
		if (files != null) {
			Arrays.stream(files).forEach(File::delete);
		}
	}

	@SubscribeEvent
	public void onKeyPressed(final InputEvent.KeyInputEvent event) {
		if (scoreboardVisibilityKey.isPressed()) {
			GuiIngameForge.renderObjective = !GuiIngameForge.renderObjective;
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayText(final RenderGameOverlayEvent.Text event) {
		final Minecraft mc = Minecraft.getMinecraft();
		if (!mc.gameSettings.showDebugInfo && adjustRotationKey.isKeyDown()) {
			final EntityPlayerSP player = mc.thePlayer;
			final double dX = player.posX - player.prevPosX;
			final double dY = player.posY - player.prevPosY;
			final double dZ = player.posZ - player.prevPosZ;
			final double speed = 20D * MathHelper.sqrt_double(dX * dX + dY * dY + dZ * dZ);
			final String rotationInfo = String.format("%+.2f @ %+.3f / %+.3f", speed, MathHelper.wrapAngleTo180_float(player.rotationYaw), MathHelper.wrapAngleTo180_float(player.rotationPitch));
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

		if (text.startsWith("Pickobulus is now available!") || text.startsWith("You used your Pickobulus Pickaxe Ability!")) {
			event.setCanceled(true);
			return;
		}

		if (text.startsWith("You earned") && text.contains("Event EXP from playing SkyBlock!")) {
			event.setCanceled(true);
			return;
		}
	}
}
