package com.llyycci.void_power.config;

import com.llyycci.void_power.VoidPowerMod;
import com.simibubi.create.foundation.config.ConfigBase;

import fuzs.forgeconfigapiport.api.config.v2.ForgeConfigRegistry;
import fuzs.forgeconfigapiport.api.config.v2.ModConfigEvents;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModConfigs {

	private static final Map<ModConfig.Type, ConfigBase> CONFIGS = new EnumMap<>(ModConfig.Type.class);

	private static ModCommonConfig common;

	public static ModCommonConfig common(){return common;}

	private static <T extends ConfigBase> T register(Supplier<T> factory, ModConfig.Type side) {
		Pair<T, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(builder -> {
			T config = factory.get();
			config.registerAll(builder);
			return config;
		});

		T config = specPair.getLeft();
		config.specification = specPair.getRight();
		CONFIGS.put(side, config);
		return config;
	}

	public static void  register(){
		common = register(ModCommonConfig::new, ModConfig.Type.COMMON);
		for(Map.Entry<ModConfig.Type, ConfigBase> pair : CONFIGS.entrySet())
			ForgeConfigRegistry.INSTANCE.register(VoidPowerMod.ID, pair.getKey(), pair.getValue().specification);

		ModConfigEvents.loading(VoidPowerMod.ID).register(ModConfigs::onLoad);
		ModConfigEvents.reloading(VoidPowerMod.ID).register(ModConfigs::onReload);
	}

	public static void onLoad(ModConfig modConfig) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == modConfig
					.getSpec())
				config.onLoad();
	}

	public static void onReload(ModConfig modConfig) {
		for (ConfigBase config : CONFIGS.values())
			if (config.specification == modConfig
					.getSpec())
				config.onReload();
	}
}
