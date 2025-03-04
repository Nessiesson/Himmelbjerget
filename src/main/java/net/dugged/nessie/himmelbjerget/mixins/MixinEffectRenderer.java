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

@SuppressWarnings("ForLoopReplaceableByForEach")
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
			this.updateEffectLayer(i);
		}

		for (int i = 0; i < this.particleEmitters.size(); i++) {
			this.particleEmitters.get(i).onUpdate();
		}

		this.particleEmitters.removeIf(p -> p.isDead);
	}

	/**
	 * @author nessie
	 * @reason speeeeeed
	 */
	@Overwrite
	private void updateEffectLayer(final int layer) {
		final var fx0 = this.fxLayers[layer][0];
		final var fx1 = this.fxLayers[layer][1];

		for (int i = 0; i < fx0.size(); ++i) {
			this.tickParticle(fx0.get(i));
		}

		for (int i = 0; i < fx1.size(); ++i) {
			this.tickParticle(fx1.get(i));
		}

		fx0.removeIf(p -> p.isDead);
		fx1.removeIf(p -> p.isDead);
	}
}
