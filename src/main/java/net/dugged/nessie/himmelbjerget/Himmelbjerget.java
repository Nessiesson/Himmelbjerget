package net.dugged.nessie.himmelbjerget;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Himmelbjerget.MOD_ID, name = Himmelbjerget.NAME, version = Himmelbjerget.VERSION)
public class Himmelbjerget {
	public static final String MOD_ID = "@MODID@";
	public static final String NAME = "@MODNAME@";
	public static final String VERSION = "@VERSION@";
	public static final Logger LOGGER = LogManager.getLogger();

	@Mod.EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
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
				((IChatComponentText) msg).himmelbjerget$replaceFirstInText("Guild > ", "G");
				((IChatComponentText) msg.getSiblings().get(0)).himmelbjerget$replaceFirstInText("Guild > ", "G");
				return;
			} catch (final Throwable e) {
				LOGGER.info("not good", e);
			}

			LOGGER.info("-");
			LOGGER.info("{}", msg);
		}
	}
}
