package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.util.LongHashMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(LongHashMap.class)
public abstract class MixinLongHashMap {
	/**
	 * @author embeddedt
	 * @reason Use a better hash (from TMCW) that avoids collisions.
	 */
	@Overwrite
	private static int getHashedKey(final long originalKey) {
		return (int) originalKey + (int) (originalKey >>> 32) * 92821;
	}
}
