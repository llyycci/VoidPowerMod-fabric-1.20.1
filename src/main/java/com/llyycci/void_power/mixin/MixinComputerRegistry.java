package com.llyycci.void_power.mixin;


import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llyycci.void_power.utils.CCUtils;

import dan200.computercraft.shared.computer.core.ServerComputer;
import dan200.computercraft.shared.computer.core.ServerComputerRegistry;


@Mixin(value = ServerComputerRegistry.class, remap = false)
public abstract class MixinComputerRegistry {
    @Shadow
    public abstract Collection<ServerComputer> getComputers();

    @Inject(method = "update", at = @At("RETURN"), remap = false)
    public void updateInject(CallbackInfo ci){
        CCUtils.computers.clear();
        getComputers().forEach(e -> {
            CCUtils.computers.put(e.getID(), e);
        });
    }

}
