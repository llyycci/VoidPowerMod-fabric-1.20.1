package com.llyycci.void_power.events;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.world.redstone.ChannelNetworkHandler;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class ServerWorldEvent {

	public static void register() {
		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			ChannelNetworkHandler.reset();
		});
	}
}
