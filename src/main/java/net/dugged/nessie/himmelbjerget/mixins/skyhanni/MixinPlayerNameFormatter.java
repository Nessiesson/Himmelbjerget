package net.dugged.nessie.himmelbjerget.mixins.skyhanni;

import net.dugged.nessie.himmelbjerget.IChatComponentText;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "at/hannibal2/skyhanni/data/hypixel/chat/PlayerNameFormatter")
public abstract class MixinPlayerNameFormatter {
	@Dynamic
	@Redirect(method = "nameFormat", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ChatComponentText;appendSibling(Lnet/minecraft/util/IChatComponent;)Lnet/minecraft/util/IChatComponent;", remap = true))
	private IChatComponent himmelbjerget$test(final ChatComponentText instance, final IChatComponent component) {
		if (component instanceof ChatComponentText) {
			final var siblings = instance.getSiblings();
			if ("§7♲".equals(((IChatComponentText) component).himmelbjerget$getText()) && !siblings.isEmpty()) {
				final var index = siblings.size() - 1;
				final var last = siblings.get(index);
				if (last instanceof ChatComponentText && " ".equals(((IChatComponentText) last).himmelbjerget$getText())) {
					siblings.remove(index);
				}
			}
		}

		return instance.appendSibling(component);
	}
}
