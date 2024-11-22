package net.dugged.nessie.himmelbjerget;

import net.minecraft.client.settings.KeyBinding;

public class ToggleSettingKeyBinding extends KeyBinding {
	public boolean isSettingEnabled = false;
	private final Runnable runnable;

	public ToggleSettingKeyBinding(final String description, final int key, final String category) {
		this(description, key, category, () -> {});
	}

	public ToggleSettingKeyBinding(final String description, final int key, final String category, final Runnable onToggle) {
		super(description, key, category);
		this.runnable = onToggle;
	}

	public void toggle() {
		this.isSettingEnabled = !this.isSettingEnabled;
		runnable.run();
	}
}
