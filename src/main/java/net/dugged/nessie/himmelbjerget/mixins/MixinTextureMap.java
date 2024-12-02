package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.texture.TextureMap;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TextureMap.class)
public abstract class MixinTextureMap {
	// TODO: use WrapWithCondition if/when I get it to work. :thinking:
	@Dynamic
	@Redirect(method = "loadTextureAtlas", at = @At(value = "INVOKE", target = "LConfig;log(Ljava/lang/String;)V", remap = false, ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=Scaled too small texture: ")))
	private void himmelbjerget$silenceOptifineTextureScalingMessage(final String message) {
	}
}
