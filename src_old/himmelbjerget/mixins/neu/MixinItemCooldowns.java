package net.dugged.nessie.himmelbjerget.mixins.neu;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/miscfeatures/ItemCooldowns")
public class MixinItemCooldowns {
	@Dynamic
	@Inject(method = "getDurabilityOverride", remap = false, at = @At("RETURN"), cancellable = true)
	private static void himmelbjerget$decrementInDiscreteSteps(final ItemStack stack, final CallbackInfoReturnable<Float> cir) {
		final var factor = 1D / 8D;
		cir.setReturnValue((float) (Math.ceil(cir.getReturnValue() / factor) * factor));
	}
}
