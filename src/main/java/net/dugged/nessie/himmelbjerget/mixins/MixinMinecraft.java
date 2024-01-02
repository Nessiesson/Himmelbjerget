package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	@Inject(method = "toggleFullscreen", at = @At(value = "JUMP", target = "Lnet/minecraft/client/Minecraft;toggleFullscreen()V", shift = At.Shift.AFTER))
	private void himmelbjerget$toggleFullScreen(final CallbackInfo ci) {
		Display.setResizable(false);
		Display.setResizable(true);
	}

	@Redirect(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;refreshResources()V", opcode = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "intValue=31")))
	private void himmelbjerget$noF3plusS(final Minecraft instance) {
	}
}
