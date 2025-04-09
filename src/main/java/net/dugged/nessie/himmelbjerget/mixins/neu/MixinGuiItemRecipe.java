package net.dugged.nessie.himmelbjerget.mixins.neu;

import io.github.moulberry.notenoughupdates.recipes.CraftingRecipe;
import io.github.moulberry.notenoughupdates.recipes.NeuRecipe;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Comparator;
import java.util.List;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/miscgui/GuiItemRecipe", remap = false)
public abstract class MixinGuiItemRecipe {
	@Unique
	private static int himmelbjerget$getPriority(final NeuRecipe recipe) {
		if (recipe instanceof final CraftingRecipe craft) {
			final var id = craft.getOutput().getInternalItemId();
			if (id.equals("BOX_OF_SEEDS")) return 0;
			if (id.equals("MUTANT_NETHER_STALK")) return 0;
			if (id.equals("POLISHED_PUMPKIN")) return 0;
			if (id.equals("ENCHANTED_COOKIE")) return 0;
			if (id.equals("ENCHANTED_GOLDEN_CARROT")) return 0;
			if (id.equals("ENCHANTED_SUGAR_CANE")) return 0;
			if (id.equals("ENCHANTED_WHEAT")) return 0;
			if (id.startsWith("ENCHANTED_HUGE_MUSHROOM_")) return 0;

			if (id.startsWith("ENCHANTED_")) return 1;
		}

		return 2;
	}

	@Dynamic
	@ModifyVariable(method = "<init>(Ljava/util/List;Lio/github/moulberry/notenoughupdates/NEUManager;)V", at = @At("HEAD"), argsOnly = true)
	private static List<NeuRecipe> himmelbjerget$putEnchangedItemsFirst(final List<NeuRecipe> unsortedRecipes) {
		unsortedRecipes.sort(Comparator.comparingInt(MixinGuiItemRecipe::himmelbjerget$getPriority));
		return unsortedRecipes;
	}
}
