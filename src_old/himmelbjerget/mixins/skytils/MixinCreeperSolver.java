package net.dugged.nessie.himmelbjerget.mixins.skytils;

import gg.skytils.skytilsmod.utils.graphics.colors.CustomColor;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Pseudo
@Mixin(targets = "gg/skytils/skytilsmod/features/impl/dungeons/solvers/CreeperSolver", remap = false)
public abstract class MixinCreeperSolver {
	@Shadow
	@Final
	@Mutable
	private static CustomColor[] colors;

	@Dynamic
	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static void himmelbjerget$removeBlack(final CallbackInfo ci) {
		 colors = Arrays.copyOfRange(colors, 1, colors.length - 1);
	}
}
