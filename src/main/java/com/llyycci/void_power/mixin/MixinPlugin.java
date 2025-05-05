package com.llyycci.void_power.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import com.llyycci.void_power.compat.CompatManager;

public class MixinPlugin implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
        CompatManager.ModCheck();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        boolean res = CompatManager.ShouldLoadMixin(mixinClassName, targetClassName);
        if(res){
            System.out.println("[VoidPowerMixinPlugin] Apply " + mixinClassName + " -> " + targetClassName);
        }
        else {
            System.out.println("[VoidPowerMixinPlugin] Skip " + mixinClassName + " -> " + targetClassName);
        }
        return res;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

}
