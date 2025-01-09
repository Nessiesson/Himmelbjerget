package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.particle.EffectRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {
	@ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
	private int himmelbjerget$increaseParticleLimit(final int constant) {
		return 16384; // like in 1.12
	}
}
