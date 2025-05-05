package com.llyycci.void_power.compat.cct;

import static com.llyycci.void_power.compat.cct.ModComputerBehavior.peripheralProvider;

import java.util.function.Function;

import com.llyycci.void_power.compat.cct.ModComputerBehavior;
import com.llyycci.create_tweaked_controllers.compat.Mods;
import com.llyycci.void_power.compat.cct.peripherals.P_EngineController;
import com.llyycci.void_power.registry.VPTileEntities;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerTE;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;
import com.simibubi.create.compat.computercraft.FallbackComputerBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import dan200.computercraft.api.peripheral.PeripheralLookup;

//import static com.simibubi.create.compat.computercraft.implementation.ComputerBehaviour.peripheralProvider;

public class ModComputerCraftProxy {
	private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> fallbackFactory;
	private static Function<SmartBlockEntity, ? extends AbstractComputerBehaviour> computerFactory;

	public static void register() {
		fallbackFactory = FallbackComputerBehaviour::new;
		Mods.COMPUTERCRAFT.executeIfInstalled(() -> ModComputerCraftProxy::registerWithDependency);
	}

	private static void registerWithDependency() {
		/* Comment if computercraft.implementation is not in the source set */
		computerFactory = ModComputerBehavior::new;
		PeripheralLookup.get().registerFallback((level, blockPos, blockState, blockEntity, direction) -> peripheralProvider(level, blockPos));

		// 注册 GlassScreen 外设
		registerPeripherals();
	}

	public static AbstractComputerBehaviour behaviour(SmartBlockEntity sbe) {
		if (computerFactory == null)
			return fallbackFactory.apply(sbe);
		return computerFactory.apply(sbe);
	}

	public static void registerPeripherals() {
		// 注册 GlassScreen 外设
		PeripheralLookup.get().registerForBlockEntity(
				(blockEntity, direction) -> {
					if (blockEntity instanceof GlassScreenTE) {
						return ((GlassScreenTE) blockEntity).peripheral();
					}
					return null;
				},
				VPTileEntities.GLASS_SCREEN_TE.get()
		);
		// 注册 EngineController 外设
		PeripheralLookup.get().registerForBlockEntity(
				(blockEntity, direction) -> {
					if (blockEntity instanceof EngineControllerTE) {
						EngineControllerTE engineController = (EngineControllerTE) blockEntity;
						if (engineController.peripheral == null) {
							engineController.peripheral = new P_EngineController(engineController);
						}
						return engineController.peripheral;
					}
					return null;
				},
				VPTileEntities.ENGINE_CONTROLLER_TE.get()
		);

		// 注册 Hologram 外设
		PeripheralLookup.get().registerForBlockEntity(
				(blockEntity, direction) -> {
					if (blockEntity instanceof HologramTE) {
						return ((HologramTE) blockEntity).peripheral;
					}
					return null;
				},
				VPTileEntities.HOLOGRAM_TE.get()
		);
	}
}
