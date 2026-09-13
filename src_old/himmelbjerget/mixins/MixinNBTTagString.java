package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(NBTTagString.class)
public abstract class MixinNBTTagString {
	@ModifyVariable(method = "<init>(Ljava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTBase;<init>()V", shift = At.Shift.AFTER), argsOnly = true)
	private String himmelbjerget$avoidcrash(final String value) {
		return value != null ? value : "";
	}
}
