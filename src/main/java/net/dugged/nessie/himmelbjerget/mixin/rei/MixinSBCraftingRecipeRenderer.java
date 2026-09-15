package net.dugged.nessie.himmelbjerget.mixin.rei;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.shedaniel.rei.api.client.registry.display.DynamicDisplayGenerator;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.impl.client.view.ViewsImpl;
import moe.nea.firmament.compat.rei.recipes.GenericRecipe;
import moe.nea.firmament.deps.repo.data.NEUCraftingRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
@Mixin(ViewsImpl.class)
public abstract class MixinSBCraftingRecipeRenderer {
	@WrapOperation(method = "generateLiveDisplays", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/client/registry/display/DynamicDisplayGenerator;getUsageFor(Lme/shedaniel/rei/api/common/entry/EntryStack;)Ljava/util/Optional;"))
	private static <T extends Display> Optional<List<T>> himmelbjerget$putEnchantedItemsFirst(final DynamicDisplayGenerator<T> instance, final EntryStack<?> entry, final Operation<Optional<List<T>>> original) {
		final var usageForDisplays = original.call(instance, entry);
		usageForDisplays.ifPresent(usages -> usages.sort(Comparator.comparingInt(MixinSBCraftingRecipeRenderer::himmelbjerget$getPriority)));
		return usageForDisplays;
	}

	@Unique
	private static int himmelbjerget$getPriority(final Object recipe) {
		if (recipe instanceof final GenericRecipe gr && gr.getNeuRecipe() instanceof NEUCraftingRecipe craft) {
			final var id = craft.getOutput().getItemId();

			if (id.equals("BOX_OF_SEEDS")) return 0;
			if (id.equals("MUTANT_NETHER_STALK")) return 0;
			if (id.equals("POLISHED_PUMPKIN")) return 0;
			if (id.equals("ENCHANTED_COOKIE")) return 0;
			if (id.equals("ENCHANTED_GOLDEN_CARROT")) return 0;
			if (id.equals("ENCHANTED_SUGAR_CANE")) return 0;
			if (id.equals("ENCHANTED_WHEAT")) return 0;
			if (id.startsWith("ENCHANTED_SUGAR")) return 0;
			if (id.startsWith("ENCHANTED_HUGE_MUSHROOM_")) return 0;

			if (id.startsWith("ENCHANTED_")) return 1;
		}

		return 2;
	}
}
