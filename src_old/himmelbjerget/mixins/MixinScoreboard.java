package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Scoreboard.class)
public abstract class MixinScoreboard {
	@Inject(method = "removeObjective", at = @At("HEAD"), cancellable = true)
	public void himmelbjerget$onRemoveObjective(final ScoreObjective obj, final CallbackInfo info) {
		if (obj == null) {
			info.cancel();
		}
	}

	@Inject(method = "removeTeam", at = @At("HEAD"), cancellable = true)
	public void himmelbjerget$onRemoveTeam(final ScorePlayerTeam team, final CallbackInfo info) {
		if (team == null) {
			info.cancel();
		}
	}
}
