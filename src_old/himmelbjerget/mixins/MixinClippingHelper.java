package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.renderer.culling.ClippingHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClippingHelper.class)
public abstract class MixinClippingHelper {
	@Shadow
	public float[][] frustum;

	/**
	 * @author nessie
	 * @reason Forcefully apply the IDE-specific implementation of the method.
	 * The IDE and non-IDE implementations are different because `n <= 0.0` and `!(n > 0.0)` are not equivalent
	 * when `n` is NaN.
	 */
	@Overwrite
	public boolean isBoxInFrustum(final double minX, final double minY, final double minZ, final double maxX, final double maxY, final double maxZ) {
		for (int i = 0; i < 6; i++) {
			final float[] afloat = this.frustum[i];
			if (this.himmelbjerget$dot(afloat, minX, minY, minZ) <= 0D && this.himmelbjerget$dot(afloat, maxX, minY, minZ) <= 0D && this.himmelbjerget$dot(afloat, minX, maxY, minZ) <= 0D && this.himmelbjerget$dot(afloat, maxX, maxY, minZ) <= 0D && this.himmelbjerget$dot(afloat, minX, minY, maxZ) <= 0D && this.himmelbjerget$dot(afloat, maxX, minY, maxZ) <= 0D && this.himmelbjerget$dot(afloat, minX, maxY, maxZ) <= 0D && this.himmelbjerget$dot(afloat, maxX, maxY, maxZ) <= 0D) {
				return false;
			}
		}

		return true;
	}

	@Unique
	private double himmelbjerget$dot(final float[] vec, final double x, final double y, final double z) {
		return (double) vec[0] * x + (double) vec[1] * y + (double) vec[2] * z + (double) vec[3];
	}
}
