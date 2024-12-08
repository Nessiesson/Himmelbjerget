package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.List;

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

			if (tag.hasKey("HideFlags", 99)) {
				tag.removeTag("HideFlags");
			}

			if (tag.hasKey("Unbreakable", 99)) {
				tag.removeTag("Unbreakable");
			}

			return (E) tag.toString();
		}

		return e;
	}

	// TODO: use WrapWithCondition if/when I get it to work. :thinking:
	@Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0, remap = false), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/util/RegistryNamespaced;getNameForObject(Ljava/lang/Object;)Ljava/lang/Object;")))
	private <E> boolean himmelbjerget$hideName(final List<E> instance, final E e) {
		return false;
	}
}
