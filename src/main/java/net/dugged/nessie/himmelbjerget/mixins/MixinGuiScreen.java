package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	@Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"))
	private void himmelbjerget$logHiddenCommands(final String msg, final boolean addToChat, final CallbackInfo ci) {
		if (!addToChat) {
			final var text = new ChatComponentText("");
			text.appendSibling(new ChatComponentText("Executed: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
			text.appendSibling(new ChatComponentText(msg).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW).setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg))));
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
		}
	}
}
