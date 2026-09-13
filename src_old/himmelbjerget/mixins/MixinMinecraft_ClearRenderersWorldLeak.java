package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/GTNewHorizons/Hodgepodge/pull/278 🤔
@Mixin(Minecraft.class)
public abstract class MixinMinecraft_ClearRenderersWorldLeak {
	@Shadow
	public EffectRenderer effectRenderer;
	@Shadow
	public RenderGlobal renderGlobal;

	@Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;theWorld:Lnet/minecraft/client/multiplayer/WorldClient;", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
	private void hodgepodge$fixRenderersWorldLeak(final WorldClient worldClient, final String loadingMessage, final CallbackInfo ci) {
		if (worldClient == null) {
			if (this.renderGlobal != null) {
				this.renderGlobal.setWorldAndLoadRenderers(null);
			}

			if (this.effectRenderer != null) {
				this.effectRenderer.clearEffects(null);
			}
		}
	}
}
