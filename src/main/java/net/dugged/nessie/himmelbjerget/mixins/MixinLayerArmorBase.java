package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = LayerArmorBase.class, remap = false)
public abstract class MixinLayerArmorBase {
	@Redirect(method = "getArmorResource(Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;ILjava/lang/String;)Lnet/minecraft/util/ResourceLocation;", at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 0))
	private String himmelbjerget$bigBrainFix0(final String format, final Object[] args) {
		// "_%s"
		return "_" + args[0];
	}

	@Redirect(method = "getArmorResource(Lnet/minecraft/entity/Entity;Lnet/minecraft/item/ItemStack;ILjava/lang/String;)Lnet/minecraft/util/ResourceLocation;",  at = @At(value = "INVOKE", target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", ordinal = 1))
	private String himmelbjerget$bigBrainFix1(final String format, final Object[] args) {
		//"%s:textures/models/armor/%s_layer_%d%s.png"
		return args[0] + ":textures/models/armor/" + args[1] + "_layer_" + args[2] + args[3] + ".png";
	}
}
