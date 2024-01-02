package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {
	@Redirect(method = "showCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;showDebugInfo:Z", opcode = Opcodes.GETFIELD))
	private boolean himmelbjerget$f3ShowsCrosshair(final GameSettings instance) {
		return false;
	}
}
