package net.dugged.nessie.himmelbjerget;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ToggleSettingKeyBinding extends KeyMapping {
	public boolean isSettingEnabled = false;
	private final Runnable onToggle;

	public ToggleSettingKeyBinding(final String translationKey, final int keyCode, final KeyMapping.Category category, final Runnable onToggle) {
		super(translationKey, InputConstants.Type.KEYSYM, keyCode, category);
		this.onToggle = onToggle;
	}

	public void toggle() {
		this.isSettingEnabled = !this.isSettingEnabled;
		this.onToggle.run();
	}
}
