package net.dugged.nessie.himmelbjerget.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class MixinBlockBehaviour_BlockStateBase {
	@Inject(method = "getSeed", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$noRandomTextures(final BlockPos pos, final CallbackInfoReturnable<Long> cir) {
		cir.setReturnValue(0L);
	}

	@Inject(method = "getOffset", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$noModelOffset(final BlockPos pos, final CallbackInfoReturnable<Vec3> cir) {
		cir.setReturnValue(Vec3.ZERO);
	}
}
