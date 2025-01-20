package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockBarrier.class)
public abstract class MixinBlockBarrier extends Block {
	public MixinBlockBarrier(final Material materialIn) {
		super(materialIn);
	}

	/**
	 * @author nessie
	 * @reason fix
	 */
	@Overwrite
	public int getRenderType() {
		return 3;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}
}
