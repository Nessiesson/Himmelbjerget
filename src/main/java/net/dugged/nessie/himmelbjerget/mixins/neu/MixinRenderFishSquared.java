package net.dugged.nessie.himmelbjerget.mixins.neu;

import com.bawnorton.mixinsquared.TargetHandler;
import net.minecraft.client.renderer.entity.RenderFish;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = RenderFish.class, priority = 1500) // priority is higher than the target mixin
public abstract class MixinRenderFishSquared {
	@Unique
	private static final double himmelbjerget$uvOffset = 1D / 512D;

	@TargetHandler(mixin = "io.github.moulberry.notenoughupdates.mixins.MixinRenderFish", name = "render")
	@ModifyArg(method = "@MixinSquared:Handler", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;tex(DD)Lnet/minecraft/client/renderer/WorldRenderer;", ordinal = 0))
	private double himmelbjerget$u0(final double u) {
		return u + himmelbjerget$uvOffset;
	}

	@TargetHandler(mixin = "io.github.moulberry.notenoughupdates.mixins.MixinRenderFish", name = "render")
	@ModifyArg(method = "@MixinSquared:Handler", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;tex(DD)Lnet/minecraft/client/renderer/WorldRenderer;", ordinal = 1))
	private double himmelbjerget$u1(final double u) {
		return u - himmelbjerget$uvOffset;
	}

	@TargetHandler(mixin = "io.github.moulberry.notenoughupdates.mixins.MixinRenderFish", name = "render")
	@ModifyArg(method = "@MixinSquared:Handler", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;tex(DD)Lnet/minecraft/client/renderer/WorldRenderer;", ordinal = 2))
	private double himmelbjerget$u2(final double u) {
		return u - himmelbjerget$uvOffset;
	}

	@TargetHandler(mixin = "io.github.moulberry.notenoughupdates.mixins.MixinRenderFish", name = "render")
	@ModifyArg(method = "@MixinSquared:Handler", index = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;tex(DD)Lnet/minecraft/client/renderer/WorldRenderer;", ordinal = 3))
	private double himmelbjerget$u3(final double u) {
		return u + himmelbjerget$uvOffset;
	}
}
