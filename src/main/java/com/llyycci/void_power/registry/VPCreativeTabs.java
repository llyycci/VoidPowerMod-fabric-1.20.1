package com.llyycci.void_power.registry;

import com.llyycci.void_power.VoidPowerMod;
import com.simibubi.create.AllCreativeModeTabs;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Supplier;

public class VPCreativeTabs {
	public static final AllCreativeModeTabs.TabInfo TAB = register("tab",
			() -> FabricItemGroup.builder()
					.title(Component.translatable("itemGroup."+ VoidPowerMod.ID +".main"))
					.icon(() ->{return new ItemStack(Items.STICK);})
					.build());

	private static AllCreativeModeTabs.TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
		ResourceLocation id = VoidPowerMod.getRL(name);
		ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
		CreativeModeTab tab = supplier.get();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
		return new AllCreativeModeTabs.TabInfo(key, tab);
	}

	public static void register() {}
}
