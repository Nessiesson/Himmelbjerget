package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.particle.EntityLargeExplodeFX;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLargeExplodeFX.class)
public abstract class MixinEntityLargeExplodeFX {
	@ModifyArg(method = "renderParticle", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;color(FFFF)V"))
	private float himmelbjerget$makeTransparentGlStateManager(final float alpha) {
		return 0.4F;
	}

	@ModifyArg(method = "renderParticle", index = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;color(FFFF)Lnet/minecraft/client/renderer/WorldRenderer;"))
	private float himmelbjerget$makeTransparentWorldRenderer(final float alpha) {
		return 0.4F;
	}

	@Inject(method = "renderParticle", at = @At("HEAD"))
	private void himmelbjerget$enableBlend(final CallbackInfo ci) {
		GlStateManager.enableBlend();
	}

	@Inject(method = "renderParticle", at = @At("RETURN"))
	private void himmelbjerget$disableBlend(final CallbackInfo ci) {
		GlStateManager.disableBlend();
	}
}
