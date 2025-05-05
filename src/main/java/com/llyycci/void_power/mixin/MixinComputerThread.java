package com.llyycci.void_power.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llyycci.void_power.config.ModConfigs;

import dan200.computercraft.core.computer.computerthread.ComputerThread;

@Mixin(value = ComputerThread.class, remap = false)
public abstract class MixinComputerThread {


    @Inject(method = "scaledPeriod", at = @At("RETURN"), cancellable = true)
    public void scaledPeriodPatch(CallbackInfoReturnable<Long> cir){
		//Forge使用Config.DefaultMinPeriodFactor获取配置选项的默认参数值，fabric选择直接get
        cir.setReturnValue((long) (cir.getReturnValue() * ModConfigs.common().CC_DEFAULT_MIN_PERIOD_FACTOR.get()));
        cir.cancel();
    }

}
