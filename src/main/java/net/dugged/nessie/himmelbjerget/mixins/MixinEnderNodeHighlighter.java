package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/miscfeatures/world/EnderNodeHighlighter")
public abstract class MixinEnderNodeHighlighter {
	@Dynamic
	@Redirect(method = "isValidHighlightSpot", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;"))
	private Block himmelbjerget$neuhackfixendernodes(final IBlockState state) {
		final var block = state.getBlock();
		return block == Blocks.stained_hardened_clay && state.getValue(BlockColored.COLOR) == EnumDyeColor.PURPLE ? Blocks.end_stone : block;
	}
}
