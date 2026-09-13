package net.dugged.nessie.himmelbjerget;

import net.minecraft.client.settings.KeyBinding;

public class ExtendedAttackKeyBinding extends KeyBinding {
	public ExtendedAttackKeyBinding(final String description, final int key, final String category) {
		super(description, key, category);
	}

	@Override
	public boolean isKeyDown() {
		return super.isKeyDown() || Himmelbjerget.secondaryAttackToggleKey.isSettingEnabled && Himmelbjerget.secondaryAttackKey.isKeyDown();
	}

	@Override
	public boolean isPressed() {
		return super.isPressed() || Himmelbjerget.secondaryAttackToggleKey.isSettingEnabled && Himmelbjerget.secondaryAttackKey.isPressed();
	}
}
