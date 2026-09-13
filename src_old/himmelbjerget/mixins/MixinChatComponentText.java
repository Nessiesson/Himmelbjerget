package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.IChatComponentText;
import net.minecraft.util.ChatComponentStyle;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChatComponentText.class)
public abstract class MixinChatComponentText extends ChatComponentStyle implements IChatComponent, IChatComponentText {
	@Mutable
	@Shadow
	@Final
	private String text;

	@Override
	public void himmelbjerget$replaceFirstInText(final String input, final String replacement) {
		this.text = text.replaceFirst(input, replacement);
	}

	@Override
	public String himmelbjerget$getText() {
		return this.text;
	}

	@Override
	public void himmelbjerget$setText(final String text) {
		this.text = text;
	}

	/**
	 * @author nessie
	 * @reason easier debugging
	 */
	@Overwrite(remap = false)
	public String toString() {
		return "TC{text='" + this.text + '\'' + (!this.siblings.isEmpty() ? ", siblings=" + this.siblings : "") + '}';
	}
}
