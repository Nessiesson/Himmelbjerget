package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.util.MouseHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHelper.class)
public abstract class MixinMouseHelper {
	@Shadow
	public int deltaX;
	@Shadow
	public int deltaY;

	@Inject(method = "mouseXYChange", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$lockMouse(final CallbackInfo ci) {
		if (Himmelbjerget.lockMouseToggleKey.isSettingEnabled) {
			ci.cancel();
			this.deltaX = 0;
			this.deltaY = 0;
		}
	}
}
