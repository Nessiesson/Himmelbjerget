package net.dugged.nessie.himmelbjerget.mixins.performance;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collections;
import java.util.List;

@Mixin(World.class)
public abstract class MixinWorld {
	// TODO: Determine if this breaks anything.
	@Redirect(method = "setActivePlayerChunksAndCheckLight", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;playerEntities:Ljava/util/List;", opcode = Opcodes.GETFIELD))
	private List<EntityPlayer> himmelbjerget$onlyCheckForMe(final World instance) {
		return instance.isRemote ? Collections.singletonList(Minecraft.getMinecraft().thePlayer) : instance.playerEntities;
	}
}
