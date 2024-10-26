package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.settings.GameSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame {
	@Redirect(method = "showCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;showDebugInfo:Z", opcode = Opcodes.GETFIELD))
	private boolean himmelbjerget$f3ShowsCrosshair(final GameSettings instance) {
		return false;
	}

	/*
		In both cases that we care about the bytecode turns into
			INVOKEVIRTUAL net/minecraft/scoreboard/Score.getScorePoints ()I
			INVOKEVIRTUAL java/lang/StringBuilder.append (I)Ljava/lang/StringBuilder;
	*/
	@Redirect(method = "renderScoreboard", at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;append(I)Ljava/lang/StringBuilder;", remap = false), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/scoreboard/Score;getScorePoints()I")))
	private StringBuilder himmelbjerget$hideScoreboardScores(final StringBuilder instance, final int i) {
		return instance;
	}

	@ModifyConstant(method = "renderScoreboard", constant = @Constant(stringValue = ": "))
	private String himmelbjerget$trimUnnecessarySpacing(final String constant) {
		return "";
	}
}
