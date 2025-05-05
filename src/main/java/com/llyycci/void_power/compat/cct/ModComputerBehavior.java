package com.llyycci.void_power.compat.cct;

import com.llyycci.void_power.compat.cct.peripherals.P_EngineController;
import com.llyycci.void_power.compat.cct.peripherals.P_HologramPeripheral;
import com.llyycci.void_power.compat.cct.peripherals.P_RSRouter;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerTE;

import com.llyycci.void_power.world.blocks.hologram.HologramTE;
import com.llyycci.void_power.world.blocks.redstone_link.RSRouterTE;

import org.jetbrains.annotations.Nullable;

//import com.llyycci.create_tweaked_controllers.block.TweakedLecternControllerBlockEntity;
//import com.llyycci.create_tweaked_controllers.compat.ComputerCraft.TweakedLecternPeripheral;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class ModComputerBehavior extends AbstractComputerBehaviour {
	@Nullable
	public static IPeripheral peripheralProvider(Level level, BlockPos blockPos) {
		AbstractComputerBehaviour behavior = BlockEntityBehaviour.get(level, blockPos, AbstractComputerBehaviour.TYPE);
		if (behavior instanceof ModComputerBehavior real)
			return real.getPeripheral();
		return null;
	}

	IPeripheral peripheral;

	public ModComputerBehavior(SmartBlockEntity te) {
		super(te);
		this.peripheral = getPeripheralFor(te);
	}
	public static IPeripheral getPeripheralFor(SmartBlockEntity be) {
		if (be instanceof EngineControllerTE ecte)
			return new P_EngineController(ecte);
		if (be instanceof RSRouterTE rste)
			return new P_RSRouter(rste);
		if (be instanceof HologramTE hbe)
			return new P_HologramPeripheral(hbe);
		throw new IllegalArgumentException("No peripheral available for " + be.getType()
				.getClass().getName());
	}
	@Override
	public <T> T getPeripheral() {
		//noinspection unchecked
		return (T) peripheral;
	}
}
