package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldClient.class)
public abstract class MixinWorldClient extends World {
	@Shadow
	@Final
	private Minecraft mc;

	protected MixinWorldClient(final ISaveHandler ish, final WorldInfo wi, final WorldProvider wp, final Profiler p, final boolean client) {
		super(ish, wi, wp, p, client);
	}

	@Inject(method = "invalidateRegionAndSetBlock", at = @At("HEAD"))
	private void himmelbjerget$addFancyParticle(final BlockPos pos, final IBlockState state, final CallbackInfoReturnable<Boolean> cir) {
		final var oldState = this.getBlockState(pos);
		final var oldBlock = oldState.getBlock();
		if (oldBlock instanceof BlockLog && state.getBlock().isAir(this, pos) /* && is in park */) {
			this.mc.effectRenderer.addBlockDestroyEffects(pos, oldState);
		}
	}
}
