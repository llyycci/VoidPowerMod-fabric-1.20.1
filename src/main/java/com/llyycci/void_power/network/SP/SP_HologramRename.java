package com.llyycci.void_power.network.SP;

import static com.llyycci.void_power.utils.ByteUtils.decodeString;

import java.util.function.Supplier;

import com.llyycci.void_power.client.gui.HologramGUI;
import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.utils.ByteUtils;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SP_HologramRename {
	public BlockPos te;
	public String name;

	public SP_HologramRename(){

	}

	public SP_HologramRename(HologramTE te){
		this.te = te.getBlockPos();
		this.name = te.name;
	}

	public static SP_HologramRename decode(FriendlyByteBuf buf) {
		//System.out.println("Received_DEC");
		SP_HologramRename data = new SP_HologramRename();
		data.te = buf.readBlockPos();
		data.name = decodeString(buf);
		return data;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(te);
		ByteUtils.encodeString(buf, name);
	}

	@Environment(EnvType.CLIENT)
	public static void handler(SP_HologramRename msg, Supplier<NetworkContext> context){
		BlockEntity be = Minecraft.getInstance().level.getBlockEntity(msg.te);
		if(be instanceof HologramTE te){
			te.Rename(msg.name);
			te.name = msg.name;

		}
	}
}
