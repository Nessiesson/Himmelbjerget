package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	@Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"))
	private void himmelbjerget$logHiddenCommands(final String msg, final boolean addToChat, final CallbackInfo ci) {
		if (!addToChat) {
			Himmelbjerget.LOGGER.info("Executed {}", msg);
		}
	}
}
