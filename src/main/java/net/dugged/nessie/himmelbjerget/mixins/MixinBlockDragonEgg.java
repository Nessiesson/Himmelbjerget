package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.MonolithHighlight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockDragonEgg.class)
public abstract class MixinBlockDragonEgg extends Block {
	public MixinBlockDragonEgg(final Material material) {
		super(material);
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(final World world, final IBlockState state) {
		return new MonolithHighlight.TileEntityMonolith();
	}
}
