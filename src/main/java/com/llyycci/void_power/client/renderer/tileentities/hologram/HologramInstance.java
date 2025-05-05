package com.llyycci.void_power.client.renderer.tileentities.hologram;

import com.llyycci.void_power.world.blocks.hologram.HologramTE;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;

public class HologramInstance extends BlockEntityInstance<HologramTE> {
    public HologramInstance(MaterialManager materialManager, HologramTE blockEntity) {
        super(materialManager, blockEntity);
    }

    @Override
    protected void remove() {

    }
}
