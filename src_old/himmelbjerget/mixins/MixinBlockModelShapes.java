package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.IntStream;

@Mixin(BlockModelShapes.class)
public abstract class MixinBlockModelShapes {
	@Inject(method = "registerBuiltInBlocks", at = @At("HEAD"))
	private void himmelbjerget$makeChestARealBlockTM(final Block[] blocks, final CallbackInfo ci) {
		IntStream.range(0, blocks.length).filter(i -> blocks[i] == Blocks.barrier).forEach(i -> blocks[i] = Blocks.air);
	}
}
