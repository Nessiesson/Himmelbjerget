package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.nbt.NBTTagString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NBTTagString.class)
public interface INBTTagString {
	@Accessor
	void setData(final String data);
}
