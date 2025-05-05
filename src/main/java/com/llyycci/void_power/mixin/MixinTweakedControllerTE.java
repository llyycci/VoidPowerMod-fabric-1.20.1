package com.llyycci.void_power.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llyycci.void_power.config.ModConfigs;
import com.llyycci.create_tweaked_controllers.block.TweakedLecternControllerBlockEntity;
import com.llyycci.create_tweaked_controllers.controller.ControllerRedstoneOutput;

import net.minecraft.world.entity.player.Player;


@Mixin(value = TweakedLecternControllerBlockEntity.class, remap = false)
public abstract class MixinTweakedControllerTE {

    @Shadow
    private ControllerRedstoneOutput output;

    @Inject(method = "stopUsing", at = @At("HEAD"), remap = false)
    public void ClearStatePatch(Player player, CallbackInfo ci){
        if(ModConfigs.common().RESET_CONTROLLER_WHEN_LEFT.get())
            output.Clear();
    }

}
