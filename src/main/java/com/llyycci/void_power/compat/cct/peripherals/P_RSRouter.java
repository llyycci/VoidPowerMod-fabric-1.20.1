package com.llyycci.void_power.compat.cct.peripherals;

import org.jetbrains.annotations.Nullable;

import com.llyycci.void_power.utils.ParamUtils;
import com.llyycci.void_power.world.blocks.redstone_link.RSRouterTE;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.resources.ResourceLocation;

/*
* 无限红石路由器 外设实现
*/
//public class P_RSRouter implements IPeripheral {
public class P_RSRouter extends ModSyncedPeripheral<RSRouterTE>{

//    RSRouterTE te;

    public P_RSRouter(RSRouterTE be){
        super(be);
		be.AssignPeripheral(this);
    }

    @LuaFunction
    public final boolean openPort(int port_id, String channel, boolean mode) throws LuaException {
        if(!ParamUtils.checkChannel(channel)) return false;
        return blockEntity.openPort(port_id, new ResourceLocation(channel), mode);
    }
    @LuaFunction
    public final void closePort(int port_id) throws LuaException {
        blockEntity.closePort(port_id);
    }

    @LuaFunction
    public final int getPortMode(int port_id){
        return blockEntity.getPortMode(port_id);
    }

    @LuaFunction
    public final int getPower(int port_id){
        return blockEntity.getPower(port_id);
    }

    @LuaFunction
    public final boolean setPower(int port_id, int power){
        return blockEntity.setPower(port_id, power);
    }

    @LuaFunction(mainThread = true)
    public final void process(){
        blockEntity.process();
    }

    @Override
    public String getType() {
        return "redstone_router";
    }

//    @Override
//    public boolean equals(@Nullable IPeripheral iPeripheral) {
//        return iPeripheral == this;
//    }
}
