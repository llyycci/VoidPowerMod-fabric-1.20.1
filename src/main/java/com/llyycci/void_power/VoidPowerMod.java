package com.llyycci.void_power;

import com.llyycci.void_power.compat.cct.ModComputerCraftProxy;
import com.llyycci.void_power.config.ModConfigs;
import com.llyycci.void_power.network.CP.CP_HologramInputEvent;
import com.llyycci.void_power.network.PacketManager;
import com.llyycci.void_power.registry.VPBlocks;
import com.llyycci.void_power.registry.VPCreativeTabs;
import com.llyycci.void_power.registry.VPItems;
import com.llyycci.void_power.registry.VPTileEntities;
import com.llyycci.void_power.utils.font.ASCII;
import com.llyycci.void_power.utils.font.Font;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;
import com.simibubi.create.Create;

import com.simibubi.create.foundation.data.CreateRegistrate;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoidPowerMod implements ModInitializer {
	public static final String ID = "void_power";
	public static final String NAME = "VoidPower";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Create addon mod [{}] is loading alongside Create [{}]!", NAME, Create.VERSION);
		LOGGER.info(EnvExecutor.unsafeRunForDist(
				() -> () -> "{} is accessing Porting Lib from the client!",
				() -> () -> "{} is accessing Porting Lib from the server!"
		), NAME);
		//register before REGISTRATE
		VPBlocks.register();
		VPItems.register();
		VPTileEntities.register();

		REGISTRATE.register();

		VPCreativeTabs.register();
		//register after REGISTRATE
		ModConfigs.register();
		PacketManager.Init();
		Font.Init();
		ModComputerCraftProxy.register();
		// 注册网络处理程序
//		CP_HologramInputEvent.register();

	}

	public static ResourceLocation getRL(String path) {
		return new ResourceLocation(ID, path);
	}

	public static CreateRegistrate registrate() {
		return REGISTRATE;
	}
}
