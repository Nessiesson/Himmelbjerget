package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.EntityFishHookDuck;
import net.minecraft.client.renderer.entity.RenderFish;
import net.minecraft.entity.projectile.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderFish.class)
public abstract class MixinRenderFish {
	@Inject(method = "doRender(Lnet/minecraft/entity/projectile/EntityFishHook;DDDFF)V", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$hackFixFishRod(final EntityFishHook entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks, final CallbackInfo ci) {
		if (!((EntityFishHookDuck) entity).himmelbjerget$shouldRender()) {
			ci.cancel();
		}
	}
}
