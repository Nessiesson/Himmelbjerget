package net.dugged.nessie.himmelbjerget.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundEngine.class)
public abstract class MixinSoundEngine {
	// TODO: Work out if this breaks any sounds that I care about.
	@WrapOperation(method = "play", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/sounds/SoundInstance;getVolume()F"))
	private float himmelbjerget$shutup(final SoundInstance instance, final Operation<Float> original) {
		final float volume = instance.getVolume();
		if ("entity.experience_orb.pickup".equals(instance.getIdentifier().getPath()) && (volume == 0.5F || (volume == 1F && instance.getPitch() == 1.4920635F))) {
			return volume * 0.25F;
		}
		return volume;
	}
}
