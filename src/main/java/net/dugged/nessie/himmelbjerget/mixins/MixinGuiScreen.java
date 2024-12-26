package net.dugged.nessie.himmelbjerget.mixins;

import gg.essential.lib.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {
	@Inject(method = "sendChatMessage(Ljava/lang/String;Z)V", at = @At("HEAD"))
	private void himmelbjerget$logHiddenCommands(final String msg, final boolean addToChat, final CallbackInfo ci) {
		if (!addToChat) {
			final var text = new ChatComponentText("");
			text.appendSibling(new ChatComponentText("Executed: ").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN)));
			text.appendSibling(new ChatComponentText(msg).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW).setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg))));
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
		}
	}

	@ModifyArg(method = "renderToolTip", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;drawHoveringText(Ljava/util/List;IILnet/minecraft/client/gui/FontRenderer;)V", remap = false))
	private List<String> himmelbjerget$modifyToolTip(final List<String> lines, @Local final ItemStack stack) {
		final var compound = stack.getTagCompound();
		if (compound == null || lines.isEmpty()) {
			return lines;
		}

		if (compound.getBoolean("HideTooltip")) {
			lines.clear();
		} else if (GuiScreen.isAltKeyDown()) {
			final var tag = (NBTTagCompound) stack.getTagCompound().copy();
			if (tag.hasKey("SkullOwner", 10)) {
				tag.removeTag("SkullOwner");
			}

			if (tag.hasKey("HideFlags", 99)) {
				tag.removeTag("HideFlags");
			}

			if (tag.hasKey("Unbreakable", 99)) {
				tag.removeTag("Unbreakable");
			}

			if (tag.hasKey("overrideMeta", 99)) {
				tag.removeTag("overrideMeta");
			}

			if (tag.hasKey("ench", 9)) {
				tag.removeTag("ench");
			}

			// TODO: Work out if hasNoTags() is ever false
			if (tag.hasKey("AttributeModifiers", 9) && tag.getTagList("AttributeModifiers", 10).hasNoTags()) {
				tag.removeTag("AttributeModifiers");
			}

			if (tag.hasKey("display", 10)) {
				final var display = tag.getCompoundTag("display");
				if (display.getTagId("Lore") == 9) {
					final var lore = display.getTagList("Lore", 8);
					for (int i = 0; i < lore.tagCount(); ++i) {
						final var line = (NBTTagString) lore.get(i);
						((INBTTagString) line).setData(line.getString() + EnumChatFormatting.GRAY);
					}
				}

				if (display.hasKey("Name", 8)) {
					final var name = (NBTTagString) display.getTag("Name");
					((INBTTagString) name).setData(name.getString() + EnumChatFormatting.GRAY);
				}
			}

			final var nbt = EnumChatFormatting.GRAY + tag.toString();
			if (Minecraft.getMinecraft().gameSettings.advancedItemTooltips) {
				lines.set(lines.size() - 1, nbt);
			} else {
				lines.add("");
				lines.add(nbt);
			}
		}

		return lines;
	}
}
