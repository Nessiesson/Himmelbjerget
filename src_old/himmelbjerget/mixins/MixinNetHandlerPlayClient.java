package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public abstract class MixinNetHandlerPlayClient {
	@Inject(method = "handleTimeUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V", shift = At.Shift.AFTER))
	private void himmelbjerget$onTimeUpdate(final S03PacketTimeUpdate packetIn, final CallbackInfo ci) {
		Himmelbjerget.TPS.calculateSlowTps();
	}
}
