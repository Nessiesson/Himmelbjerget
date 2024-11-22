package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.util.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {
	@Inject(method = "addBlockDestroyEffects", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$hideBlockBreakParticles(final BlockPos pos, final IBlockState state, final CallbackInfo ci) {
		if (Himmelbjerget.secondaryAttackToggleKey.isSettingEnabled) {
			ci.cancel();
		}
	}
}
