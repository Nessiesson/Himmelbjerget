package net.dugged.nessie.himmelbjerget.mixins;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/overlays/PowderGrindingOverlay")
public abstract class MixinPowderGrindingOverlay {
	@Dynamic
	@ModifyConstant(method = "updateFrequent", remap = false, constant = @Constant(stringValue = "§3Unopened Chests: §c"))
	private String himmelbjerget$hideUnopenedChestsText(final String constant) {
		return "§c";
	}
}
