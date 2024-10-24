package net.dugged.nessie.himmelbjerget;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class MonolithHighlight {
	public static class TileEntityMonolith extends TileEntity {
		@Override
		public double getMaxRenderDistanceSquared() {
			return Double.POSITIVE_INFINITY;
		}
	}

	public static class TileEntityMonolithRenderer extends TileEntitySpecialRenderer<TileEntityMonolith> {
		@Override
		public void renderTileEntityAt(final TileEntityMonolith te, final double x, final double y, final double z, final float partialTicks, final int destroyStage) {
			GlStateManager.disableFog();
			GlStateManager.disableLighting();
			GlStateManager.disableTexture2D();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
			this.setLightmapDisabled(true);
			GlStateManager.depthFunc(GL11.GL_ALWAYS);

			GlStateManager.color(1F, 1F, 0F, 1F);
			this.renderFilledBox(x, y, z, x + 1D, y + 1D, z + 1D);

			GlStateManager.depthFunc(GL11.GL_LEQUAL);
			this.setLightmapDisabled(false);
			GlStateManager.enableLighting();
			GlStateManager.enableTexture2D();
			GlStateManager.enableDepth();
			GlStateManager.depthMask(true);
			GlStateManager.enableFog();
		}

		protected void setLightmapDisabled(final boolean disabled) {
			GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);

			if (disabled) {
				GlStateManager.disableTexture2D();
			} else {
				GlStateManager.enableTexture2D();
			}

			GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		}

		public void renderFilledBox(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
			final Tessellator tessellator = Tessellator.getInstance();
			final WorldRenderer builder = tessellator.getWorldRenderer();
			builder.begin(GL11.GL_TRIANGLE_STRIP, DefaultVertexFormats.POSITION);
			builder.pos(minX, minY, minZ).endVertex();
			builder.pos(minX, minY, minZ).endVertex();
			builder.pos(minX, minY, minZ).endVertex();
			builder.pos(minX, minY, maxZ).endVertex();
			builder.pos(minX, maxY, minZ).endVertex();
			builder.pos(minX, maxY, maxZ).endVertex();
			builder.pos(minX, maxY, maxZ).endVertex();
			builder.pos(minX, minY, maxZ).endVertex();
			builder.pos(maxX, maxY, maxZ).endVertex();
			builder.pos(maxX, minY, maxZ).endVertex();
			builder.pos(maxX, minY, maxZ).endVertex();
			builder.pos(maxX, minY, minZ).endVertex();
			builder.pos(maxX, maxY, maxZ).endVertex();
			builder.pos(maxX, maxY, minZ).endVertex();
			builder.pos(maxX, maxY, minZ).endVertex();
			builder.pos(maxX, minY, minZ).endVertex();
			builder.pos(minX, maxY, minZ).endVertex();
			builder.pos(minX, minY, minZ).endVertex();
			builder.pos(minX, minY, minZ).endVertex();
			builder.pos(maxX, minY, minZ).endVertex();
			builder.pos(minX, minY, maxZ).endVertex();
			builder.pos(maxX, minY, maxZ).endVertex();
			builder.pos(maxX, minY, maxZ).endVertex();
			builder.pos(minX, maxY, minZ).endVertex();
			builder.pos(minX, maxY, minZ).endVertex();
			builder.pos(minX, maxY, maxZ).endVertex();
			builder.pos(maxX, maxY, minZ).endVertex();
			builder.pos(maxX, maxY, maxZ).endVertex();
			builder.pos(maxX, maxY, maxZ).endVertex();
			builder.pos(maxX, maxY, maxZ).endVertex();
			tessellator.draw();
		}
	}
}
