package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {
	@Inject(method = "getDebugLoadedEntities", at = @At("RETURN"), cancellable = true)
	private void himmelbjerget$tps(final CallbackInfoReturnable<String> cir) {
		final var tps = Math.min(20, 1000 / Himmelbjerget.mspt);
		cir.setReturnValue(String.format("%s. TPS: %d, MSPT: %d", cir.getReturnValue(), tps, Himmelbjerget.mspt));
	}
}
