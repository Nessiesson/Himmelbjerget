package net.dugged.nessie.himmelbjerget.mixins.skyhanni;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "at/hannibal2/skyhanni/data/hypixel/chat/PlayerNameFormatter", remap = false)
public abstract class MixinPlayerNameFormatter {
	@Dynamic
	@Redirect(method = "onPlayerAllChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatComponentText;appendSibling(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;", ordinal = 2, remap = true))
	private IChatComponent himmelbjerget$makeSameChatColorGreatAgain(final ChatComponentText instance, final IChatComponent component) {
		final var siblings = component.getSiblings();
		if (!siblings.isEmpty()) {
			final var style = siblings.get(0).getChatStyle();
			if (style.getColor() == EnumChatFormatting.GRAY) {
				style.setColor(EnumChatFormatting.WHITE);
			}
		}

		return instance.appendSibling(component);
	}
}
