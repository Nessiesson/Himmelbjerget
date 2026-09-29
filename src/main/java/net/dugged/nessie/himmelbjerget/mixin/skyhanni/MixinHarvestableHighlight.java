package net.dugged.nessie.himmelbjerget.mixin.skyhanni;

import at.hannibal2.skyhanni.events.GuiContainerEvent;
import at.hannibal2.skyhanni.features.garden.greenhouse.HarvestableHighlight;
import at.hannibal2.skyhanni.utils.InventoryUtils;
import at.hannibal2.skyhanni.utils.ItemUtils;
import at.hannibal2.skyhanni.utils.LorenzColor;
import at.hannibal2.skyhanni.utils.RenderUtils;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HarvestableHighlight.class)
public abstract class MixinHarvestableHighlight {
	@Inject(method = "onGrowthStatusDrawn", at = @At(value = "INVOKE", target = "Lat/hannibal2/skyhanni/utils/InventoryUtils;getSlotAtIndex(I)Lnet/minecraft/world/inventory/Slot;"))
	private void addGreenToHarvestLevelSymbol(final GuiContainerEvent.BackgroundDrawnEvent event, final CallbackInfo ci) {
		final var slot = InventoryUtils.INSTANCE.getSlotAtIndex(20);
		if (slot != null) {
			final var sapling = ItemUtils.INSTANCE.takeUnlessEmpty(slot.getItem());
			if (sapling != null && sapling.is(Items.JUNGLE_SAPLING) && ItemUtils.INSTANCE.getLoreComponent(sapling).stream().anyMatch(lore -> "Stage: 10/10".equals(lore.getString()))) {
				RenderUtils.INSTANCE.highlight(slot, LorenzColor.GREEN);
			}
		}
	}
}
