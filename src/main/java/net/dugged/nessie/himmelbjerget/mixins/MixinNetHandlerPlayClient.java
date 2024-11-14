package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
	@Unique
	private final List<Long> himmelbjerget$lastTimeUpdates = new ArrayList<>();

	@Inject(method = "handleChat", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER))
	private void himmelbjerget$tpsEstimate(final S02PacketChat packetIn, final CallbackInfo ci) {
		if (packetIn.getType() == 2 && packetIn.getChatComponent().getUnformattedText().contains("❤")) {
			final var currentTime = System.nanoTime();
			this.himmelbjerget$lastTimeUpdates.add(currentTime);
			if (this.himmelbjerget$lastTimeUpdates.size() > 6) {
				final var dt = currentTime - this.himmelbjerget$lastTimeUpdates.remove(0);
				Himmelbjerget.mspt = (int) Math.max(50, dt * 5E-8 / 3D);
			}
		}
	}
}
