package com.llyycci.void_power.network.CP;

import java.util.function.Supplier;

import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CP_HologramUpdateRequest {
	public BlockPos te;

	public CP_HologramUpdateRequest(){

	}

	public CP_HologramUpdateRequest(HologramTE te){
		this.te = te.getBlockPos();
	}

	public static CP_HologramUpdateRequest decode(FriendlyByteBuf buf) {
		//System.out.println("Received_DEC");
		CP_HologramUpdateRequest data = new CP_HologramUpdateRequest();
		data.te = buf.readBlockPos();
		return data;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(te);
	}

	public static void handler(CP_HologramUpdateRequest msg, Supplier<NetworkContext> context){
		NetworkContext ctx = context.get();
		BlockEntity be = ctx.getSender().level().getBlockEntity(msg.te);
		if(be instanceof HologramTE te){
			te.returnFullUpdatePack(ctx.getSender());
		}
	}
}
