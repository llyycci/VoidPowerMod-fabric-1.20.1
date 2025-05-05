package com.llyycci.void_power.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llyycci.void_power.config.ModConfigs;

import net.minecraft.core.BlockPos;
import site.siredvin.peripheralworks.common.blockentity.PeripheralProxyBlockEntity;

@Mixin(value = PeripheralProxyBlockEntity.class, remap = false)
public abstract class MixinPeripheralProxyTE_Kt {
    @Inject(method = "isPosApplicable", at = @At("HEAD"), cancellable = true)
    public void distCheckVSPatch(BlockPos pos, CallbackInfoReturnable<Boolean> cir){
        if(ModConfigs.common().WIRELESS_HUB_UNLIMITED.get()){
            cir.setReturnValue(true);
            cir.cancel();
        }
        /*
        BlockEntity te = (BlockEntity)(Object)this;
        Level level = te.getLevel();
        int max = PeripheralWorksConfig.INSTANCE.getPeripheralProxyMaxRange();
        if(level instanceof ServerLevel serverLevel){
            cir.setReturnValue(VSUtils.GetBlockDistanceSqrBetween(serverLevel, te.getBlockPos(), pos)
                    < max * max);
            //System.out.println(VSUtils.GetBlockDistanceSqrBetween(serverLevel, te.getBlockPos(), pos));
            cir.cancel();
        }*/
    }
}
