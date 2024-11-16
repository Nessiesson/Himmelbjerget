package net.dugged.nessie.himmelbjerget;

import net.minecraft.client.settings.KeyBinding;

public class ExtendedAttackKeyBinding extends KeyBinding {
	public ExtendedAttackKeyBinding(final String description, final int key, final String category) {
		super(description, key, category);
	}

	@Override
	public boolean isKeyDown() {
		return Himmelbjerget.mayUseSecondaryAttackKey && Himmelbjerget.secondaryAttackKey.isKeyDown() || super.isKeyDown();
	}

	@Override
	public boolean isPressed() {
		return Himmelbjerget.mayUseSecondaryAttackKey && Himmelbjerget.secondaryAttackKey.isPressed() || super.isPressed();
	}
}
