package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.EnumFacing;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.Set;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {
	@Redirect(method = "preRenderDamagedBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;doPolygonOffset(FF)V"))
	private void himmelbjerget$fixMc234(final float factor, final float units) {
		GlStateManager.doPolygonOffset(-1F, -10F);
	}

	@Redirect(method = "setupTerrain", at = @At(value = "INVOKE", target = "Ljava/util/Set;size()I", remap = false, ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;getVisibleFacings(Lnet/minecraft/util/BlockPos;)Ljava/util/Set;", ordinal = 0)))
	private int himmelbjerget$mc63020(final Set<EnumFacing> facingSetIn) {
		return 0;
	}
}
