package net.dugged.nessie.himmelbjerget.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class MixinChatScreen {
	@Inject(method = "sendUnattendedCommand", at = @At("HEAD"))
	private void himmelbjerget$logHiddenCommands(final String command, final Screen screenAfterCommand, final CallbackInfo ci) {
		final MutableComponent text = Component.literal("");
		text.append(Component.literal("Executed: ").setStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)));
		text.append(Component.literal("/" + command).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW).withClickEvent(new ClickEvent.RunCommand(command))));
		Minecraft.getInstance().gui.getChat().addClientSystemMessage(text);
	}
}
