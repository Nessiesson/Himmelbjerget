package net.dugged.nessie.himmelbjerget.mixins.skyhanni;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.dugged.nessie.himmelbjerget.IChatComponentText;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.regex.Pattern;

@Pseudo
@Mixin(targets = "at/hannibal2/skyhanni/data/hypixel/chat/PlayerNameFormatter", remap = false)
public abstract class MixinPlayerNameFormatter {
	@Unique
	private static final Pattern himmelbjerget$rankStart = Pattern.compile("^(?:§.)*?\\[");

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

	@Dynamic
	@Inject(method = "nameFormat", at = @At("RETURN"))
	private void himmelbjerget$fixUpNameFormat(final CallbackInfoReturnable<ChatComponentText> cir) {
		final var name = cir.getReturnValue();
		final List<IChatComponent> siblings = name.getSiblings();
		for (int i = 0; i < siblings.size(); i++) {
			final var child = siblings.get(i);
			final var childSiblings = child.getSiblings();
			if (!childSiblings.isEmpty()) {
				final var maybeRank = ((IChatComponentText) childSiblings.get(0)).himmelbjerget$getText();
				if (himmelbjerget$rankStart.matcher(maybeRank).find()) {
					final var last = childSiblings.get(childSiblings.size() - 1);
					childSiblings.clear();
					childSiblings.add(last);
				}
			}

			final var text = ((IChatComponentText) child).himmelbjerget$getText();
			if ("§7♲".equals(text) && i != 0) {
				siblings.remove(i - 1);
				i--;
			}
		}
	}
}
