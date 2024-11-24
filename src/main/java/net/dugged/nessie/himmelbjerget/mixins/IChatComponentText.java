package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ChatComponentText.class)
public interface IChatComponentText {
	@Accessor
	String getText();

	@Mutable
	@Accessor
	void setText(final String text);
}
