package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ItemStack.class)
public abstract class MixinItemStack {
	@Shadow
	public abstract NBTTagCompound getTagCompound();

	@SuppressWarnings("unchecked")
	@ModifyArg(method = "getTooltip", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, remap = false), slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue= tag(s)")))
	private <E> E himmelbjerget$showNBT(final E e) {
		if (GuiScreen.isAltKeyDown()) {
			final var tag = (NBTTagCompound) this.getTagCompound().copy();
			if (tag.hasKey("SkullOwner", 10)) {
				tag.removeTag("SkullOwner");
			}

			return (E) tag.toString();
		}

		return e;
	}
}
