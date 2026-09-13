package net.dugged.nessie.himmelbjerget;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.ToggleKeyMapping;

import java.util.function.BooleanSupplier;

public class ExtendedAttackKeyMapping extends ToggleKeyMapping {
	public ExtendedAttackKeyMapping(final String name, final InputConstants.Type type, final int value, final KeyMapping.Category category, final BooleanSupplier needsToggle, final boolean shouldRestore) {
		super(name, type, value, category, needsToggle, shouldRestore);
	}

	@Override
	public boolean isDown() {
		return super.isDown() || Himmelbjerget.secondaryAttackToggleKey.isSettingEnabled && Himmelbjerget.secondaryAttackKey.isDown();
	}

	@Override
	public boolean consumeClick() {
		return super.consumeClick() || Himmelbjerget.secondaryAttackToggleKey.isSettingEnabled && Himmelbjerget.secondaryAttackKey.consumeClick();
	}
}