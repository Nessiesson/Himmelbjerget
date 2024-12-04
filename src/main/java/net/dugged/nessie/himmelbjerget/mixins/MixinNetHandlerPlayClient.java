package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
	@Inject(method = "handleParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER))
	private void himmelbjerget$onParticle(final S2APacketParticles p, final CallbackInfo ci) {
		if (p.getParticleType() == EnumParticleTypes.EXPLOSION_LARGE) {
			if (p.isLongDistance() && p.getParticleCount() == 8) {
				((IS2APacketParticles) p).setParticleType(EnumParticleTypes.VILLAGER_HAPPY);
			} else {
				Himmelbjerget.LOGGER.info("{} {} {}", p.isLongDistance(), p.getParticleSpeed(), p.getParticleCount());
			}
		}
	}
}
