package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderItemFrame.class)
public abstract class MixinRenderItemFrame {
	@Redirect(method = "doRender(Lnet/minecraft/entity/item/EntityItemFrame;DDDFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BlockModelRenderer;renderModelBrightnessColor(Lnet/minecraft/client/resources/model/IBakedModel;FFFF)V"))
	private void himmelbjerget$hideFilledMap(final BlockModelRenderer instance, final IBakedModel bakedModel, final float brightness, final float red, final float green, final float blue, final EntityItemFrame entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
		if (entity.getDisplayedItem() == null) {
			instance.renderModelBrightnessColor(bakedModel, brightness, red, green, blue);
		}
	}
}
