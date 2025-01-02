package net.dugged.nessie.himmelbjerget.mixins.neu;

import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/miscfeatures/EnchantingSolvers")
public abstract class MixinEnchantingSolvers {
	@Dynamic
	@Redirect(method = "onItemTooltip", remap = false, at = @At(value = "INVOKE", target = "Ljava/lang/String;trim()Ljava/lang/String;", ordinal = 0))
	private String himmelbjerget$hideTooltipBetter(final String instance) {
		return EnumChatFormatting.getTextWithoutFormattingCodes(instance).trim();
	}
}
