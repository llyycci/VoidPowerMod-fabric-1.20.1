package com.llyycci.void_power.world.blocks.engine_controller;

import java.util.List;

import com.llyycci.void_power.compat.cct.ModComputerCraftProxy;
import com.simibubi.create.compat.computercraft.AbstractComputerBehaviour;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import com.llyycci.void_power.compat.cct.peripherals.P_EngineController;
import com.llyycci.void_power.compat.vs.ship.EngineController;
import com.llyycci.void_power.compat.vs.ship.QueuedForceApplier;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import dan200.computercraft.api.peripheral.IPeripheral;
//import dan200.computercraft.shared.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
//import net.minecraftforge.common.capabilities.Capability;//fabric 暂无Capability相关
//import net.minecraftforge.common.util.LazyOptional;

public class EngineControllerTE extends SmartBlockEntity {
    public P_EngineController peripheral = null;
	public AbstractComputerBehaviour computerBehaviour;
//    private LazyOptional<IPeripheral> peripheralCap;

    private ServerShip ship;
    private EngineController ec;
    private QueuedForceApplier qfa;

//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if(cap == Capabilities.CAPABILITY_PERIPHERAL){
//            if(this.peripheral == null){
//                this.peripheral = new P_EngineController(this);
//            }
//            if(peripheralCap == null || !peripheralCap.isPresent())
//                peripheralCap =  LazyOptional.of(() -> this.peripheral);
//            return peripheralCap.cast();
//        }
//        return super.getCapability(cap, side);
//    }

    public boolean hasShip(){
        return ship != null;
    }

    public Vector3d getFaceRaw(){
        Vec3i o = this.getBlockState().getValue(EngineControllerBlock.FACING).getNormal();
        return new Vector3d(o.getX(), o.getY(), o.getZ());
    }

    public Vector3d getFace(){
        Vector3d face = getFaceRaw();
        if (hasShip()) {
            return ship.getTransform().getShipToWorldRotation().transform(face);
        }
        return face;
    }

    @Override
    public void tick() {
        super.tick();
        if(level.isClientSide) return;
        if(ship == null){
            ship = VSGameUtilsKt.getShipObjectManagingPos((ServerLevel) level, getBlockPos());
        }

        if(ship != null){
            ec = EngineController.getOrCreate(ship);
            qfa = QueuedForceApplier.getOrCreate(ship);

            ec.addController(this);
            qfa.Enabled = ec.canDrive();
        }

    }

//    public P_EngineController getPeripheral(){
//        if(this.peripheral == null){
//            this.peripheral = new P_EngineController(this);
//        }
//        return peripheral;
//    }

	//注册cc外设
	public void AssignPeripheral(P_EngineController p){peripheral = p;}
    public boolean shipCanDrive(){
        return ec != null && ec.canDrive();
    }

    public EngineControllerTE(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    public void invalidate() {
        super.invalidate();

        if(level.isClientSide) return;
        if(ec != null){
            ec.removeController(this);
        }
    }

    @Override
    public void remove() {
        super.remove();

        if (level != null && level.isClientSide) return;
        if(ec != null){
            ec.removeController(this);
        }
        ship = null;
        ec = null;
        qfa = null;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		behaviours.add(computerBehaviour = ModComputerCraftProxy.behaviour(this));
		if(peripheral == null){
			peripheral = new P_EngineController(this);
		}
    }

    public double massCanDrive(){
        if(ship != null && ec != null){
            return ec.massCanDrive();
        }
        return 0;
    }

    public QueuedForceApplier getApplier(){
        if(qfa == null && ship != null){
            qfa = QueuedForceApplier.getOrCreate(ship);
        }
        return qfa;
    }

    public EngineController getCtrl(){
        if(ec == null && ship != null){
            ec = EngineController.getOrCreate(ship);
        }
        return ec;
    }

    public void PushCCEvent(PhysShipImpl physShip){
        //System.out.println("PUSH");
        if(peripheral != null){
           //System.out.println("PUSH2");
            peripheral.PushEvent(physShip);
        }
    }

    public ServerShip getShip(){
        return this.ship;
    }
}
