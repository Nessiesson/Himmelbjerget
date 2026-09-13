package net.dugged.nessie.himmelbjerget.mixins;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// DEFINITELY not stolen from https://github.com/prplz/MouseDelayFix
@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
	public MixinEntityLivingBase(final World worldIn) {
		super(worldIn);
	}

	@Inject(method = "getLook", at = @At("HEAD"), cancellable = true)
	private void himmelbjerget$mc67665(final float partialTicks, final CallbackInfoReturnable<Vec3> cir) {
		if ((EntityLivingBase) (Object) this instanceof EntityPlayerSP) {
			cir.setReturnValue(super.getLook(partialTicks));
		}
	}
}
