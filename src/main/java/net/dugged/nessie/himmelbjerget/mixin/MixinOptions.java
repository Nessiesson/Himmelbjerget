package net.dugged.nessie.himmelbjerget.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.InputConstants;
import net.dugged.nessie.himmelbjerget.ExtendedAttackKeyMapping;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import java.util.function.BooleanSupplier;

@Mixin(Options.class)
public abstract class MixinOptions {
	@WrapOperation(method = "<init>", slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;keyUse:Lnet/minecraft/client/KeyMapping;"), to = @At(value = "CONSTANT", args = "stringValue=key.pickItem", ordinal = 0)), at = @At(value = "NEW", args = "class=net/minecraft/client/ToggleKeyMapping"))
	private static ToggleKeyMapping himmelbjerget$extendedAttackKeyMapping(final String name, final InputConstants.Type type, final int value, final KeyMapping.Category category, final BooleanSupplier needsToggle, final boolean shouldRestore, final Operation<ToggleKeyMapping> original) {
		return new ExtendedAttackKeyMapping(name, type, value, category, needsToggle, shouldRestore);
	}
}
