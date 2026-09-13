package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraftforge.fml.common.discovery.JarDiscoverer;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JarDiscoverer.class)
public abstract class MixinJarDiscoverer {
	@Redirect(method = "discover", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/FMLLog;log(Lorg/apache/logging/log4j/Level;Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V"))
	private void himmelbjerget$nolog(final Level level, final Throwable ex, final String format, final Object[] data) {
	}
}
