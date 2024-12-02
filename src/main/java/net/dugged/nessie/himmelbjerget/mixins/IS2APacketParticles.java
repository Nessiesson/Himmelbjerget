package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(S2APacketParticles.class)
public interface IS2APacketParticles {
	@Accessor
	void setParticleType(EnumParticleTypes type);
}
