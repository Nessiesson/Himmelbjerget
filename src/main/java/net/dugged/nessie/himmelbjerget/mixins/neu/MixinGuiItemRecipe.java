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
	private final static Comparator<NeuRecipe> himmelbjerget$cmpEnchFirst = (o1, o2) -> {
		if (o1 == o2) return 0;
		if (o1 instanceof CraftingRecipe c1) {
			final var id = c1.getOutput().getInternalItemId();
			if (id.startsWith("BOX_OF_SEEDS")) return -1;
			if (id.startsWith("MUTANT_NETHER_STALK")) return -1;
			if (id.startsWith("POLISHED_PUMPKIN")) return -1;
			if (id.startsWith("ENCHANTED_COOKIE")) return -1;
			if (id.startsWith("ENCHANTED_GOLDEN_CARROT")) return -1;
			if (id.startsWith("ENCHANTED_SUGAR_CANE")) return -1;
			if (id.startsWith("ENCHANTED")) return -1;
		}
		return 1;
	};

	@Dynamic
	@ModifyVariable(method = "<init>(Ljava/util/List;Lio/github/moulberry/notenoughupdates/NEUManager;)V", at = @At("HEAD"), argsOnly = true)
	private static List<NeuRecipe> himmelbjerget$putEnchangedItemsFirst(final List<NeuRecipe> unsortedRecipes) {
		unsortedRecipes.sort(himmelbjerget$cmpEnchFirst);
		return unsortedRecipes;
	}
}
