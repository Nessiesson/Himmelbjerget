package net.dugged.nessie.himmelbjerget.mixins.neu;

import net.minecraft.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Pseudo
@Mixin(targets = "io/github/moulberry/notenoughupdates/miscfeatures/BetterContainers", remap = false)
public interface IBetterContainers {
	@Accessor
	static List<Slot> getLastSlots() {
		return null;
	}
}
