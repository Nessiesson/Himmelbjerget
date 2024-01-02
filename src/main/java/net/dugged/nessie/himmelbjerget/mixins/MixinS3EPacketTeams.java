package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S3EPacketTeams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(S3EPacketTeams.class)
public abstract class MixinS3EPacketTeams {
	@Redirect(method = "processPacket(Lnet/minecraft/network/play/INetHandlerPlayClient;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/play/INetHandlerPlayClient;handleTeams(Lnet/minecraft/network/play/server/S3EPacketTeams;)V"))
	private void himmelbjerget$processPacket(final INetHandlerPlayClient instance, final S3EPacketTeams packet) {
		try {
			instance.handleTeams(packet);
		} catch (Throwable ignored) {
		}
	}
}
