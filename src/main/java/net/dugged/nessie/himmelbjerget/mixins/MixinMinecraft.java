package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
	@Inject(method = "toggleFullscreen", at = @At(value = "JUMP", target = "Lnet/minecraft/client/Minecraft;toggleFullscreen()V", shift = At.Shift.AFTER))
	private void himmelbjerget$toggleFullScreen(final CallbackInfo ci) {
		Display.setResizable(false);
		Display.setResizable(true);
	}
}
