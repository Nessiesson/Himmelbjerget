package net.dugged.nessie.himmelbjerget.mixins.patcher;

import club.sk1er.patcher.config.PatcherConfig;
import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LayerCape.class, priority = 1500) // priority is higher than the target mixin
public abstract class MixinLayerCapeSquared {
	@TargetHandler(mixin = "club.sk1er.patcher.mixins.features.LayerCapeMixin_NaturalCapes", name = "patcher$setEntityLivingBaseIn")
	@Inject(method = "@MixinSquared:Handler", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$noLeekNoWorry(final CallbackInfo ci) {
		if (!PatcherConfig.naturalCapes) {
			ci.cancel();
		}
	}
}
