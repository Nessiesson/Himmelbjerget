package net.dugged.nessie.himmelbjerget;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.8.9")
public class HimmelbjergetLoadingPlugin implements IFMLLoadingPlugin {
	public HimmelbjergetLoadingPlugin() {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.himmelbjerget.json");
	}

	// @formatter:off
	@Override public String getAccessTransformerClass() { return null; }
	@Override public String[] getASMTransformerClass() { return null; }
	@Override public void injectData(final Map<String, Object> data) {}
	@Nullable @Override public String getSetupClass() { return null; }
	@Override public String getModContainerClass() { return null; }
	// @formatter:on
}
