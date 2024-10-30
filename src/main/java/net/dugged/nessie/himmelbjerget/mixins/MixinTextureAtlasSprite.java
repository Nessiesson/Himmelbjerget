package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(TextureAtlasSprite.class)
public abstract class MixinTextureAtlasSprite {
	@ModifyConstant(method = "initSprite", constant = @Constant(doubleValue = (double) 0.01F))
	private double himmelbjerget$noZoom(final double constant) {
		return 0F;
	}
}
