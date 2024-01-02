package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityEnderman.class)
public abstract class MixinEntityEnderman {
	@Redirect(method = "onLivingUpdate", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z", opcode = Opcodes.GETFIELD))
	private boolean himmelbjerget$stopEndermenParticles(final World instance) {
		return false;
	}
}
