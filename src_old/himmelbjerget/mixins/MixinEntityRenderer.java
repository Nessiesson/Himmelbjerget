package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
	@Redirect(method = "updateCameraAndRender", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;mouseSensitivity:F", opcode = Opcodes.GETFIELD))
	private float himmelbjerget$adjustRotation(final GameSettings instance) {
		return Himmelbjerget.adjustRotationKey.isKeyDown() ? instance.mouseSensitivity / 10F : instance.mouseSensitivity;
	}

	@ModifyConstant(method = "updateCameraAndRender", constant = @Constant(floatValue = 0.2F))
	private float himmelbjerget$adjustRotation(final float constant) {
		return Himmelbjerget.adjustRotationKey.isKeyDown() ? constant / 10F : constant;
	}
}
