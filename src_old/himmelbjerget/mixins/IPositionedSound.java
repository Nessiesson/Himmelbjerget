package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.audio.PositionedSound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PositionedSound.class)
public interface IPositionedSound {
	@Accessor
	void setVolume(final float volume);
}
