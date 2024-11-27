package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.EntityFishHookDuck;
import net.minecraft.entity.projectile.EntityFishHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityFishHook.class)
public abstract class MixinEntityFishHook implements EntityFishHookDuck {
	@Unique
	private boolean himmelbjerget$shouldRender = false;

	@Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onUpdate()V"))
	private void himmelbjerget$whatever(final CallbackInfo ci) {
		this.himmelbjerget$shouldRender = true;
	}

	@Override
	public boolean himmelbjerget$shouldRender() {
		return this.himmelbjerget$shouldRender;
	}
}
