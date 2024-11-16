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
		final var mspt = Himmelbjerget.mspt;
		cir.setReturnValue(String.format("%s. sTPS: %d/%d, lTPS: %d/%d", cir.getReturnValue(), mspt.get(1), mspt.get(0), mspt.get(3), mspt.get(2)));
	}
}
