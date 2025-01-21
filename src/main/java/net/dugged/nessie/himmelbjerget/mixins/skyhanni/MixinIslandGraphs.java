package net.dugged.nessie.himmelbjerget.mixins.skyhanni;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "at/hannibal2/skyhanni/data/IslandGraphs", remap = false)
public abstract class MixinIslandGraphs {
	@Dynamic
	@Inject(method = "sendChatDistance", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$shutUpNavigationInChat(final CallbackInfo ci) {
		ci.cancel();
	}
}
