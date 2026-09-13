package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Block.class, BlockDoublePlant.class, BlockFlower.class, BlockTallGrass.class})
public abstract class CMixinNoOffsets {
	@Inject(method = "getOffsetType", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$nooffset(final CallbackInfoReturnable<Block.EnumOffsetType> cir) {
		cir.setReturnValue(Block.EnumOffsetType.NONE);
	}
}
