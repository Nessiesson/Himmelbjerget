package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
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

	@Inject(method = "handleTimeUpdate", at = @At("RETURN"))
	private void onTimeUpdate(final S03PacketTimeUpdate packetIn, final CallbackInfo ci) {
		final var currentTime = System.nanoTime();
		this.himmelbjerget$lastTimeUpdates.add(currentTime);
		if (this.himmelbjerget$lastTimeUpdates.size() > 5) {
			this.himmelbjerget$lastTimeUpdates.remove(0);
			final var dt = currentTime - this.himmelbjerget$lastTimeUpdates.get(0);
			Himmelbjerget.mspt = (int) Math.max(50, dt * 5E-8D / 5D);
		}
	}
}
