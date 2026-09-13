package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.ExtendedAttackKeyBinding;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {
	@Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V", at = @At(value = "NEW", args = "class=net/minecraft/client/settings/KeyBinding", ordinal = 0), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=key.drop"), to = @At(value = "CONSTANT", args = "stringValue=key.attack")))
	private KeyBinding himmelbjerget$extendAttackKey(final String description, final int key, final String category) {
		return new ExtendedAttackKeyBinding(description, key, category);
	}
}
