package net.dugged.nessie.himmelbjerget.mixins;

import net.dugged.nessie.himmelbjerget.Himmelbjerget;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
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
	private void himmelbjerget$tpsEstimateAndActionBarCleanUp(final S02PacketChat packetIn, final CallbackInfo ci) {
		final var component = packetIn.getChatComponent();
		if (packetIn.getType() == 2 && component.getUnformattedText().contains("❤")) {
			if (component instanceof ChatComponentText) {
				final var text = (IChatComponentText) component;
				text.setText(text.getText().replace("✎ Mana", "✎"));
			}

			final var currentTime = System.nanoTime();
			this.himmelbjerget$lastTimeUpdates.add(currentTime);
			final var size = this.himmelbjerget$lastTimeUpdates.size();
			if (size >= 6 /* 3 seconds */) {
				final var dt = currentTime - this.himmelbjerget$lastTimeUpdates.get(size - 6);
				final var mspt = (int) Math.max(50, dt * 5E-8 / 3D);
				final var tps = 1000 / mspt;
				Himmelbjerget.mspt.set(0, mspt);
				Himmelbjerget.mspt.set(1, tps);
			}

			if (size > 120 /* 60 seconds */) {
				final var dt = currentTime - this.himmelbjerget$lastTimeUpdates.get(0);
				final var mspt = (int) Math.max(50, dt * 5E-8 / 120D);
				final var tps = 1000 / mspt;
				Himmelbjerget.mspt.set(2, mspt);
				Himmelbjerget.mspt.set(3, tps);
				this.himmelbjerget$lastTimeUpdates.remove(0);
			}
		}
	}
}
