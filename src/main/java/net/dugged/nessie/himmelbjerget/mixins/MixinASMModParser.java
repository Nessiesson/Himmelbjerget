package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraftforge.fml.common.discovery.asm.ASMModParser;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ASMModParser.class)
public abstract class MixinASMModParser {
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/FMLLog;log(Lorg/apache/logging/log4j/Level;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V", remap = false))
	private void himmelbjerget$nolog(final Level level, final Throwable ex, final String format, final Object[] data) {
	}
}
