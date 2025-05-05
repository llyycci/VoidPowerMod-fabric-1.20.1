package com.llyycci.void_power.compat;

import com.google.common.collect.Maps;
import com.llyycci.void_power.compat.Mods;
//import net.minecraftforge.fml.loading.FMLLoader;

import java.util.Map;

public class CompatManager {


    static final Map<String, String> mixin_modid = Maps.newHashMap();
    static final Map<String, Boolean> needed = Maps.newHashMap();

    static {
        mixin_modid.put("MixinTweakedControllerTE", "create_tweaked_controllers");
        mixin_modid.put("MixinPeripheralProxyTE_Kt", "peripheralworks");
    }


    public static void ModCheck(){
        mixin_modid.forEach((k,v) -> {
            if(!needed.containsKey(v)){
                needed.put(v, Mods.fromId(v).isLoaded());
            }
        });
    }


    public static boolean ShouldLoadMixin(String mixinClass, String targetClass){
        if(mixin_modid.containsKey(mixinClass)){
            return needed.getOrDefault(mixin_modid.getOrDefault(mixinClass, ""), false);
        }
        return true;
    }


}
