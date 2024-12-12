package net.dugged.nessie.himmelbjerget.mixins;

import com.google.common.collect.EvictingQueue;
import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.EnumParticleTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnstableApiUsage")
@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
	@Unique
	private final EvictingQueue<Long> himmelbjerget$slowTimeUpdates = EvictingQueue.create(60);

	@Inject(method = "handleParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER))
	private void himmelbjerget$onParticle(final S2APacketParticles p, final CallbackInfo ci) {
		if (p.getParticleType() == EnumParticleTypes.EXPLOSION_LARGE) {
			((IS2APacketParticles) p).setParticleType(EnumParticleTypes.VILLAGER_HAPPY);
		}
	}

	@Inject(method = "handleTimeUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER))
	private void himmelbjerget$onTimeUpdate(final S03PacketTimeUpdate packetIn, final CallbackInfo ci) {
		final var currentTime = System.nanoTime();
		this.himmelbjerget$slowTimeUpdates.add(currentTime);

		final var dt = currentTime - this.himmelbjerget$slowTimeUpdates.peek();
		final var mspt = (int) Math.max(50, dt * 5E-8D / this.himmelbjerget$slowTimeUpdates.size());
		final var tps = 1000 / mspt;
		Himmelbjerget.mspt.set(2, mspt);
		Himmelbjerget.mspt.set(3, tps);
	}
}
