package net.dugged.nessie.himmelbjerget;

import com.bawnorton.mixinsquared.MixinSquaredBootstrap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class HimmelbjergetMixinConfigPlugin implements IMixinConfigPlugin {
	@Override
	public void onLoad(final String mixinPackage) {
		MixinSquaredBootstrap.init();
	}

	// @formatter:off
	@Override public List<String> getMixins() { return null; }
	@Override public String getRefMapperConfig() { return null; }
	@Override public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {}
	@Override public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) { return true; }
	@Override public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {}
	@Override public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {}
	// @formatter:on
}
