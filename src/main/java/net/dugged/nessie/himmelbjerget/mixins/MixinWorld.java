package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld {
	@Inject(method = "getDebugLoadedEntities", at = @At("RETURN"), cancellable = true)
	private void himmelbjerget$tps(final CallbackInfoReturnable<String> cir) {
		final var tps = Himmelbjerget.TPS;
		cir.setReturnValue(String.format("%s. sTPS: %d/%d, lTPS: %d/%d", cir.getReturnValue(), tps.slowTps, tps.slowMspt, tps.fastTps, tps.fastMspt));
	}

	// TODO: Determine if this breaks anything.
	@Redirect(method = "setActivePlayerChunksAndCheckLight", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;playerEntities:Ljava/util/List;", opcode = Opcodes.GETFIELD))
	private List<EntityPlayer> himmelbjerget$onlyCheckForMe(final World instance) {
		return instance.isRemote ? Collections.singletonList(Minecraft.getMinecraft().thePlayer) : instance.playerEntities;
	}
}
