package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiOverlayDebug.class)
public abstract class MixinGuiOverlayDebug {
	@ModifyConstant(method = "call", constant = @Constant(stringValue = "Facing: %s (%s) (%.1f / %.1f)"))
	private String himmelbjerget$boop(final String constant) {
		return Himmelbjerget.adjustRotationKey.isKeyDown() ? "Facing: %s (%s) (%.5f / %.5f)" : constant;
	}
}
