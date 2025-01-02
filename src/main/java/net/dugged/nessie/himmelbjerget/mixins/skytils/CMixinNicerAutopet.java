package net.dugged.nessie.himmelbjerget.mixins.skytils;

import io.netty.channel.ChannelHandlerContext;
import net.dugged.nessie.himmelbjerget.IChatComponentText;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ChatComponentText;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CMixinNicerAutopet {
	@Mixin(NetworkManager.class)
	public static abstract class MixinNetworkManager {
		@Shadow
		@Final
		private EnumPacketDirection direction;

		@Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"))
		private void himmelbjerget$cleanAutopetText(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
			if (this.direction == EnumPacketDirection.CLIENTBOUND && packet instanceof final S02PacketChat chat && chat.getType() != 2) {
				final var component = chat.getChatComponent();
				if (component instanceof final ChatComponentText text) {
					final var raw = component.getUnformattedText();
					if (raw.startsWith("§cAutopet")) {
						((IChatComponentText) text).himmelbjerget$replaceFirstInText(" §a§lVIEW RULE", "");
					}
				}
			}
		}
	}

	@Pseudo
	@Mixin(targets = "gg/skytils/skytilsmod/features/impl/misc/PetFeatures")
	public static abstract class MixinPetFeatures {
		@Dynamic
		@ModifyConstant(method = "<clinit>", remap = false, constant = @Constant(stringValue = "§cAutopet §eequipped your §7\\[Lvl (?<level>\\d+)] (?<pet>.+)§e! §a§lVIEW RULE§r"))
		private static String himmelbjerget$fixSkytilsAutopet(final String constant) {
			return "§cAutopet §eequipped your §7\\[Lvl (?<level>\\d+)] (?<pet>.+)§e!";
		}
	}
}
