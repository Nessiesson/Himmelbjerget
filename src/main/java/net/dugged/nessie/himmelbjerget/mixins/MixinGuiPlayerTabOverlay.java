package net.dugged.nessie.himmelbjerget.mixins;

import com.google.common.collect.Ordering;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(GuiPlayerTabOverlay.class)
public abstract class MixinGuiPlayerTabOverlay extends Gui {
	@Redirect(method = "renderPlayerlist", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Ordering;sortedCopy(Ljava/lang/Iterable;)Ljava/util/List;", remap = false))
	private List<NetworkPlayerInfo> himmelbjerget$limitToThreeColumns(final Ordering<NetworkPlayerInfo> instance, final Iterable<NetworkPlayerInfo> elements) {
		List<NetworkPlayerInfo> list = instance.sortedCopy(elements);

		if (list.size() >= 80) {
			final List<NetworkPlayerInfo> tmp = new ArrayList<>();
			tmp.addAll(list.subList(0, 20));
			tmp.addAll(list.subList(40, 80));
			list = tmp;
		}

		return list;
	}

	@Inject(method = "drawPing", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$hidePingIcons(final int i, final int j, final int k, final NetworkPlayerInfo networkPlayerInfoIn, final CallbackInfo ci) {
		ci.cancel();
	}
}
