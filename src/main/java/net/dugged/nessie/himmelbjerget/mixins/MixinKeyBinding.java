package net.dugged.nessie.himmelbjerget.mixins;

import gg.essential.lib.mixinextras.sugar.Local;
import net.dugged.nessie.himmelbjerget.ToggleSettingKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding {
	// TODO: Figure out why this requires gg.essential.lib.mixinextras.sugar.Local and not com.llamalad7.mixinextras.sugar.Local; 💀💀💀
	@Inject(method = "setKeyBindState", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/KeyBinding;pressed:Z", opcode = Opcodes.PUTFIELD))
	private static void himmelbjerget$updateKeyState(final int keyCode, final boolean pressed, final CallbackInfo ci, final @Local KeyBinding keyBinding) {
		if (keyBinding instanceof ToggleSettingKeyBinding toggleSetting && !((IKeyBinding) keyBinding).getPressed() && pressed) {
			toggleSetting.toggle();
		}
	}
}
