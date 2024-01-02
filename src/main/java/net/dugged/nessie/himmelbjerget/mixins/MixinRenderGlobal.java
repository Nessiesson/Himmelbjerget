package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal {
	@Redirect(method = "preRenderDamagedBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;doPolygonOffset(FF)V"))
	private void himmelbjerget$fixMc234(final float factor, final float units) {
		GlStateManager.doPolygonOffset(-1F, -10F);
	}
}
