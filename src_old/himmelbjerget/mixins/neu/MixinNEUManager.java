package net.dugged.nessie.himmelbjerget.mixins.neu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import gg.essential.lib.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/NEUManager", remap = false)
public abstract class MixinNEUManager {
	@Dynamic
	@Inject(method = "loadItem", at = @At(value = "INVOKE_ASSIGN", target = "Lio/github/moulberry/notenoughupdates/NEUManager;getJsonFromFile(Ljava/io/File;)Lcom/google/gson/JsonObject;"), cancellable = true)
	private void himmelbjerget$doNotShowSkins(final String internalName, final CallbackInfo ci, final @Local JsonObject json) {
		if (internalName.startsWith("PET_SKIN_")) {
			ci.cancel();
			return;
		}

		if (json == null) {
			return;
		}


		if (json.has("lore")) {
			final var lore = json.get("lore").getAsJsonArray();
			for (final JsonElement element : lore) {
				final var str = element.getAsString();
				if (str.startsWith("§7§7Item skins give your gear a") || str.startsWith("§7§7This Minion skin changes")) {
					ci.cancel();
					return;
				}
			}
		}
	}
}
