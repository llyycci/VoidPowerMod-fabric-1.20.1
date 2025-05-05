package com.llyycci.void_power.compat.cct.peripherals;

import com.llyycci.void_power.compat.cct.lua.LuaPhysShip;
import com.llyycci.void_power.compat.vs.ship.QueuedForceApplier;
import com.llyycci.void_power.utils.CCUtils;
import com.llyycci.void_power.utils.VSUtils;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerTE;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.util.Mth;

import org.jetbrains.annotations.Nullable;
import org.joml.*;
import org.joml.primitives.AABBi;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.game.ships.PhysShipImpl;

import java.lang.Math;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

//public class P_EngineController implements IPeripheral {
public class P_EngineController extends ModSyncedPeripheral<EngineControllerTE> {
//    private final EngineControllerTE te;
//    public P_EngineController(EngineControllerTE te){
//        this.te = te;
//    }
	public P_EngineController(EngineControllerTE be){
		super(be);
		be.AssignPeripheral(this);
	}
//    private final Set<IComputerAccess> computers = Sets.newConcurrentHashSet();

    @Override
    public String getType() {
        return "EngineController";
    }

//    @Override
//    public boolean equals(@Nullable IPeripheral iPeripheral) {
//        return Objects.equals(this, iPeripheral);
//    }
//
//    @Override
//    public void attach(IComputerAccess computer) {
//        computers.add(computer);
//        //System.out.println("ATTACH");
//    }
//
//    @Override
//    public void detach(IComputerAccess computer) {
//        //System.out.println("DETACH");
//        computers.remove(computer);
//        if(computers.isEmpty() && blockEntity.hasShip()){
//            blockEntity.getCtrl().setIdle(true);
//        }
//    }

    public void PushEvent(PhysShipImpl physShip){
        LuaPhysShip.ShipPhysStateSnapshot snapshot = LuaPhysShip.createSnapshot(physShip, blockEntity);
        computers.forEach((c) -> {
            c.queueEvent("phys_tick", new Object[]{new LuaPhysShip(snapshot, physShip, blockEntity)});
        });
    }

    @LuaFunction
    public final long getId(){
        if(blockEntity.hasShip()){
            return blockEntity.getShip().getId();
        }
        return -1;
    }

    @LuaFunction
    public final double getMass(){
        if(blockEntity.hasShip()){
            return blockEntity.getShip().getInertiaData().getMass();
        }
        return 0;
    }

    @LuaFunction
    public final List<List<Double>> getMomentOfInertiaTensor() {
        if(blockEntity.hasShip()){
            return CCUtils.dumpMat3(blockEntity.getShip().getInertiaData().getMomentOfInertiaTensor());
        }
        return null;
    }

    @LuaFunction
    public final String getName(){
        if(blockEntity.hasShip()){
            return blockEntity.getShip().getSlug();
        }
        return "";
    }


    @LuaFunction
    public final Map<String, Double> getOmega(){
        if(blockEntity.hasShip()){
            return CCUtils.dumpVec3(blockEntity.getShip().getOmega());
        }
        return CCUtils.dumpVec3(0,0,0);
    }

    @LuaFunction
    public final Map<String, Double> getRotation() {
        if(blockEntity.hasShip()){
            Quaterniondc rot = blockEntity.getShip().getTransform().getShipToWorldRotation();
            return CCUtils.dumpVec4(rot);
        }
        return CCUtils.dumpVec4(0,0,0,0);
    }

    @LuaFunction
    public final Map<String, Double> getFaceVector() {
        return CCUtils.dumpVec3(blockEntity.getFace());
    }

    @LuaFunction
    public final Map<String, Double> getFaceRaw() {
        return CCUtils.dumpVec3(blockEntity.getFaceRaw());
    }


    @LuaFunction
    public final double getRoll() {
        if(blockEntity.hasShip()){
            double[][] rotMatrix = VSUtils.getRotationMatrixRaw(blockEntity.getShip());
            return Mth.atan2(rotMatrix[1][0], rotMatrix[1][1]);
        }
        return 0;
    }

    @LuaFunction
    public final double getYaw() {
        if(blockEntity.hasShip()){
            double[][] rotMatrix = VSUtils.getRotationMatrixRaw(blockEntity.getShip());
            return Mth.atan2(-rotMatrix[0][2], rotMatrix[2][2]);
        }
        return 0;
    }

    @LuaFunction
    public final double getPitch(){
        if(blockEntity.hasShip()){
            return Math.asin(VSUtils.getRotationMatrixRaw(blockEntity.getShip())[1][2]);
        }
        return 0;
    }

    @LuaFunction
    public final List<List<Double>> getRotationMatrix(){
        if(blockEntity.hasShip()){
            return getRotationMatrix(blockEntity.getShip());
        }
        return null;
    }

    @LuaFunction
    public final boolean isIdle(){
        if(blockEntity.hasShip()){
            return  blockEntity.getCtrl().isIdle();
        }
        return true;
    }

    @LuaFunction
    public final void setIdle(boolean idle){
        if(blockEntity.hasShip()){
            blockEntity.getCtrl().setIdle(idle);
        }
    }

    @LuaFunction
    public final void forcedDisableIdle(boolean b){
        if(blockEntity.hasShip()){
            blockEntity.getCtrl().disableIdle(b);
        }
    }

    @LuaFunction
    public final boolean isOnShip(){
        return blockEntity.hasShip();
    }

    @LuaFunction
    public final Map<String, Double> getVelocity(){
        if(blockEntity.hasShip()){
            return CCUtils.dumpVec3(blockEntity.getShip().getVelocity());
        }
        return CCUtils.dumpVec3(0,0,0);
    }

    @LuaFunction
    public final Map<String, Double> getPosition(){
        if(blockEntity.hasShip()){
            return CCUtils.dumpVec3(blockEntity.getShip().getTransform().getPositionInWorld());
        }
        return CCUtils.dumpVec3(0,0,0);
    }//▀▄

    private List<List<Double>> getRotationMatrix(ServerShip ship){
        Matrix4dc transform = ship.getTransform().getShipToWorld();
        List<List<Double>> matrix = Lists.newArrayList();

        for (int i = 0; i < 4; i++) {
            Vector4d row = transform.getRow(i, new Vector4d());
            matrix.add(List.of(row.x, row.y, row.z, row.w));
        }
        return matrix;
    }

    @LuaFunction
    public final double getMassCanDrive(){
        if(blockEntity.hasShip()){
            return blockEntity.massCanDrive();
        }
        return 0;
    }

    /***
     * power API
     */
    @LuaFunction
    public final void applyInvariantForce(double x, double y, double z) {
        QueuedForceApplier applier = blockEntity.getApplier();
        //System.out.println("APPLY_0");
        if(applier != null){
            //System.out.println("APPLY_1");
            applier.applyInvariantForce(new Vector3d(x,y,z));
        }
    }

    @LuaFunction
    public final void applyInvariantTorque(double x, double y, double z) {
        QueuedForceApplier applier = blockEntity.getApplier();
        if(applier != null){
            applier.applyInvariantTorque(new Vector3d(x,y,z));
        }
    }

    @LuaFunction
    public final void applyInvariantForceToPos(double px, double py, double pz, double fx, double fy, double fz) {
        QueuedForceApplier applier = blockEntity.getApplier();
        if(applier != null){
            applier.applyInvariantForceToPos(new Vector3d(fx,fy,fz), new Vector3d(px,py,pz));
        }
    }

    @LuaFunction
    public final void applyRotDependentForce(double x, double y, double z) {
        QueuedForceApplier applier = blockEntity.getApplier();
        if(applier != null){
            applier.applyRotDependentForce(new Vector3d(x,y,z));
        }
    }

    @LuaFunction
    public final void applyRotDependentTorque(double x, double y, double z) {
        QueuedForceApplier applier = blockEntity.getApplier();
        if(applier != null){
            applier.applyRotDependentTorque(new Vector3d(x,y,z));
        }
    }

    @LuaFunction
    public final void applyRotDependentForceToPos(double px, double py, double pz, double fx, double fy, double fz) {
        QueuedForceApplier applier = blockEntity.getApplier();
        if(applier != null){
            applier.applyRotDependentForceToPos(new Vector3d(fx,fy,fz), new Vector3d(px,py,pz));
        }
    }

    @LuaFunction
    public final boolean isStatic(){
        if(blockEntity.hasShip()){
            return blockEntity.getShip().isStatic();
        }
        return true;
    }

    @LuaFunction
    public final Map<String, Double> getScale(){
        if(blockEntity.hasShip()){
            Vector3d s = blockEntity.getShip().getShipToWorld().getScale(new Vector3d());
            return CCUtils.dumpVec3(s);
        }
        return null;
    }

    @LuaFunction
    public final Map<String, Double> getSize(){
        if(blockEntity.hasShip()){
            var aabb = blockEntity.getShip().getShipAABB();
            if(aabb == null) aabb = new AABBi(0, 0, 0, 0, 0, 0);
            return CCUtils.dumpVec3(
                    aabb.maxX() - aabb.minX(),
                    aabb.maxY() - aabb.minY(),
                    aabb.maxZ() - aabb.minZ()
            );
        }
        return null;
    }

    @LuaFunction
    public final Map<String, Double> getShipCenter(){ //getShipyardPosition
        if(blockEntity.hasShip()){
            Vector3dc s = blockEntity.getShip().getTransform().getPositionInShip();
            return CCUtils.dumpVec3(s);
        }
        return null;
    }


}
