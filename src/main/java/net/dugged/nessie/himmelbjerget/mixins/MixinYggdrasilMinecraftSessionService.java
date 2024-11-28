package net.dugged.nessie.himmelbjerget.mixins;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import gg.essential.lib.mixinextras.injector.v2.WrapWithCondition;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(value = YggdrasilMinecraftSessionService.class, remap = false)
public abstract class MixinYggdrasilMinecraftSessionService {
	@WrapWithCondition(method = "getTextures", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"))
	private boolean himmelbjerget$ignoreInvalidJson(final Logger logger, final String message, final Throwable t) {
		return false;
	}
}
