package com.llyycci.void_power.compat.cct.peripherals;

import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;

import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.shared.peripheral.monitor.MonitorPeripheral;

public class P_GlassScreenPeripheral extends MonitorPeripheral {

    GlassScreenTE m;
    public P_GlassScreenPeripheral(GlassScreenTE monitor) {
        super(monitor);
        this.m = monitor;

    }

    @LuaFunction
    public final void setTransparentMode(boolean mode){
        m.SetTrans(mode);
    }


    @LuaFunction
    public final void setTransparentColor(int color) throws LuaException {
        int colour = parseColour(color);
        m.SetTransCol("0123456789abcdef".charAt(colour));
    }
}
