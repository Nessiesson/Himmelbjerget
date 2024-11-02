package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextureAtlasSprite.class)
public abstract class MixinTextureAtlasSprite {
	@ModifyConstant(method = "initSprite", constant = @Constant(doubleValue = (double) 0.01F))
	private double himmelbjerget$noZoom(final double constant) {
		return 0F;
	}

	@Inject(method = {"getInterpolatedU", "getInterpolatedV"}, at = @At("RETURN"), cancellable = true)
	private void himmelbjerget$vroom(final double uv, final CallbackInfoReturnable<Float> cir) {
		final float value = cir.getReturnValue();
		final float factor = 1F / 16384F;
		cir.setReturnValue(Math.round(value / factor) * factor);
	}
}
