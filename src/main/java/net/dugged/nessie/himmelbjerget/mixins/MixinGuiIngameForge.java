package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiIngameForge.class)
public abstract class MixinGuiIngameForge {
	@ModifyArg(method = "renderRecordOverlay", index = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;translate(FFF)V"))
	private float himmelbjerget$renderActionBarAboveChat(final float z) {
		return 1F;
	}

	@Redirect(method = "renderRecordOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
	private int himmelbjerget$renderTextWithShadows(final FontRenderer instance, final String text, final int x, final int y, final int color) {
		return instance.drawString(text, x, y, color, true);
	}
}
