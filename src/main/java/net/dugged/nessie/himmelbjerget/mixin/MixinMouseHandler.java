package net.dugged.nessie.himmelbjerget.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MixinMouseHandler {
	@Shadow
	private double accumulatedDX;

	@Inject(method = "turnPlayer", at = @At("HEAD"))
	private void himmelbjerget$lockMouse(final CallbackInfo ci) {
		if (Himmelbjerget.lockMouseToggleKey.isSettingEnabled) {
			this.accumulatedDX = 0.0D;
		}
	}

	@WrapOperation(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/OptionInstance;get()Ljava/lang/Object;", ordinal = 0))
	private Object himmelbjerget$adjustRotationSensitivity(final OptionInstance<Double> instance, final Operation<Double> original) {
		final double value = instance.get();
		return Himmelbjerget.adjustRotationKey.isDown() ? value / 10.0D : value;
	}
}
