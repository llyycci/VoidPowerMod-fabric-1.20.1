package com.llyycci.void_power.world.blocks.void_engine;

import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import com.llyycci.void_power.config.ModConfigs;
import com.llyycci.void_power.compat.vs.ship.EngineController;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class VoidEngineTE extends KineticBlockEntity{

    public VoidEngineTE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    private ServerShip ship;

    @Override
    public void onSpeedChanged(float previousSpeed) {
        super.onSpeedChanged(previousSpeed);
    }

    @Override
    public void tick() {
        super.tick();

        if(level.isClientSide) return;
        if(ship == null){
            ship = VSGameUtilsKt.getShipObjectManagingPos((ServerLevel) level, getBlockPos());
            if(ship != null){
                EngineController ec = EngineController.getOrCreate(ship);
                ec.addEngine(this);
            }
        }

        calculateStressApplied();
        updateSpeed = true;
    }

    private float STRESS = 0;
    @Override
    public float calculateStressApplied() {
        STRESS = ModConfigs.common().STRESS_PER_RPM.getF();
        this.lastStressApplied = STRESS;
        return STRESS;
    }

    public double massCanDrive(){
        //System.out.println(STRESS * ModConfigs.common().MASS_PER_STRESS.getF() * Mth.abs(getSpeed()));
        return STRESS * ModConfigs.common().MASS_PER_STRESS.getF() * Mth.abs(getSpeed());
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if(level.isClientSide) return;
        if(ship != null){
            EngineController ec = EngineController.getOrCreate(ship);
            ec.removeEngine(this);
        }
        ship = null;
    }

    @Override
    public void remove() {
        super.remove();

        if(level.isClientSide) return;
        if(ship != null){
            EngineController ec = EngineController.getOrCreate(ship);
            ec.removeEngine(this);
        }
    }
}
