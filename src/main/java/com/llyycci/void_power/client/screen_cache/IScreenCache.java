package com.llyycci.void_power.client.screen_cache;

import net.minecraft.resources.ResourceLocation;

public interface IScreenCache {
    void invalidate();
    void cleanup();
    ResourceLocation getTexture();
}
