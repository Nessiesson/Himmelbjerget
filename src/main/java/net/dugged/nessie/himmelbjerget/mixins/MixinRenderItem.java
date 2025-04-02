package net.dugged.nessie.himmelbjerget.mixins;

import at.hannibal2.skyhanni.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem {
	@Inject(method = "renderItemOverlayIntoGUI", at = @At("RETURN"))
	private void himmelbjerget$showRecombobulatedItems(final FontRenderer fr, final ItemStack stack, final int xPosition, final int yPosition, final String text, final CallbackInfo ci) {
		if (Minecraft.getMinecraft().currentScreen == null || stack == null || !stack.hasTagCompound()) {
			return;
		}

		final var attributes = stack.getSubCompound("ExtraAttributes", false);
		if (attributes != null && attributes.getInteger("rarity_upgrades") != 0) {
			final var rarity = ItemUtils.INSTANCE.getItemRarityOrCommon(stack).oneBelow(false);
			if (rarity != null) {
				this.himmelbjerget$draw(xPosition, yPosition, rarity.getColor().toDyeColor());
			}
		}
	}

	@Unique
	private void himmelbjerget$draw(final int x, final int y, final EnumDyeColor colour) {
		final var rgb = colour.getMapColor().colorValue;
		final var r = rgb >> 16 & 0xFF;
		final var g = rgb >> 8 & 0xFF;
		final var b = rgb & 0xFF;

		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();

		final var tessellator = Tessellator.getInstance();
		final var renderer = tessellator.getWorldRenderer();
		renderer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		renderer.pos(x + 16D, y + 0D, 0D).color(r, g, b, 255).endVertex();
		renderer.pos(x + 12D, y + 0D, 0D).color(r, g, b, 255).endVertex();
		renderer.pos(x + 16D, y + 4D, 0D).color(r, g, b, 255).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}
}
