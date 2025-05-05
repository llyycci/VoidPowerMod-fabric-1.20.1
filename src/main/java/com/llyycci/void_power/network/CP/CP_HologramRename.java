package com.llyycci.void_power.network.CP;

import static com.llyycci.void_power.utils.ByteUtils.decodeString;

import java.util.function.Supplier;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.network.PacketManager;
import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.network.SP.SP_HologramRename;
import com.llyycci.void_power.utils.ByteUtils;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class CP_HologramRename {
	public BlockPos te;
	public String name;

	public CP_HologramRename(){

	}

	public CP_HologramRename(HologramTE te){
		this.te = te.getBlockPos();
		this.name = te.name;
//		VoidPowerMod.LOGGER.info("Rename Hologram_CP: {}", name);
	}

	public static CP_HologramRename decode(FriendlyByteBuf buf) {
		//System.out.println("Received_DEC");
		CP_HologramRename data = new CP_HologramRename();
		data.te = buf.readBlockPos();

		data.name = decodeString(buf);

		return data;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(te);
		ByteUtils.encodeString(buf, name);
	}

	public static void handler(CP_HologramRename msg, Supplier<NetworkContext> context){
		NetworkContext ctx = context.get();
		BlockEntity be = ctx.getSender().level().getBlockEntity(msg.te);
		if(be instanceof HologramTE te){
			te.Rename(msg.name);
			// 确保数据包被发送
			PacketManager.sendToAllPlayerTrackingThisBlock(new SP_HologramRename(te), te);
			// 同时发送给操作的玩家，确保GUI接收到更新
			PacketManager.sendToPlayer(new SP_HologramRename(te), ctx.getSender());
		}
	}
}
