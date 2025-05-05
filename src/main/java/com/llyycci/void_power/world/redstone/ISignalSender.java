package com.llyycci.void_power.world.redstone;

import net.minecraft.resources.ResourceLocation;

public interface ISignalSender {
    ResourceLocation getChannel();
    int getPower();
    boolean shouldRemove();
    //void setChannel(ResourceLocation c);
}
