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

	@Inject(method = "mouseXYChange", at = @At("RETURN"))
	private void himmelbjerget$lockMouse(final CallbackInfo ci) {
		if (Himmelbjerget.lockMouseToggleKey.isSettingEnabled) {
			this.deltaX = 0;
		}
	}
}
