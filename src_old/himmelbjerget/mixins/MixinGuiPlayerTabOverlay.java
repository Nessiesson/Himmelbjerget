package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay extends Gui {
	@Inject(method = "drawPing", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$hidePingIcons(final int i, final int j, final int k, final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfo ci) {
		ci.cancel();
	}
}
