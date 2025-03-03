package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityParticleEmitter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import java.util.List;

@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {
	@Shadow
	protected abstract void tickParticle(final EntityFX particle);

	@Shadow
	private List<EntityParticleEmitter> particleEmitters;
	@Shadow
	private List<EntityFX>[][] fxLayers;

	@ModifyConstant(method = "addEffect", constant = @Constant(intValue = 4000))
	private int himmelbjerget$increaseParticleLimit(final int constant) {
		return 16384; // like in 1.12
	}

	/**
	 * @author nessie
	 * @reason speeeeeed
	 */
	@Overwrite
	public void updateEffects() {
		for (int i = 0; i < 4; ++i) {
			this.fxLayers[i][0].removeIf(p -> { this.tickParticle(p); return p.isDead; });
			this.fxLayers[i][1].removeIf(p -> { this.tickParticle(p); return p.isDead; });
		}

		this.particleEmitters.removeIf(p -> { p.onUpdate(); return p.isDead; });
	}
}
