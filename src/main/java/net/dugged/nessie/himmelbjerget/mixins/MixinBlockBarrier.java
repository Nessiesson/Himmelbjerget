package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
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

	@Override
	public boolean shouldSideBeRendered(final IBlockAccess world, final BlockPos pos, final EnumFacing side) {
		final IBlockState state = world.getBlockState(pos);
		final Block block = state.getBlock();
		if (this == Blocks.barrier) {
			if (world.getBlockState(pos.offset(side.getOpposite())) != state) {
				return true;
			}

			if (block == this) {
				return false;
			}
		}

		if (block == this) {
			return false;
		}

		return super.shouldSideBeRendered(world, pos, side);
	}
}
