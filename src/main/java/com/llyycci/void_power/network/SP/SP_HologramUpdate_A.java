package com.llyycci.void_power.network.SP;

import static com.llyycci.void_power.utils.ByteUtils.*;

import java.io.IOException;
import java.util.function.Supplier;

import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

@SuppressWarnings("rawtypes")
public class SP_HologramUpdate_A {
	public final BlockPos te;
	public final short width, height;
	public final int[] buffer;

	public SP_HologramUpdate_A(HologramTE te, int[] buffer){
		this.te = te.getBlockPos();
		width = (short) te.getWidth();
		height = (short) te.getHeight();
		this.buffer = buffer;
	}

	public SP_HologramUpdate_A(FriendlyByteBuf buf){
		te = buf.readBlockPos();
		width = buf.readShort();
		height = buf.readShort();
		int[] buffer = null;

		byte[] compressed = buf.readByteArray();

		try {
			buffer = decompress(compressed);
		} catch (IOException e) {
			System.out.println("decompress Filed");
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}

		this.buffer = buffer;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(te);
		buf.writeShort(width);
		buf.writeShort(height);
		try {
			buf.writeByteArray(compress(buffer));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public static SP_HologramUpdate_A decode(FriendlyByteBuf buf){
		return new SP_HologramUpdate_A(buf);
	}

	public void handle(HologramTE te){
		int curr = 0;
		int[] bf = te.getBuffer();
		while (curr < buffer.length){
			int offset = buffer[curr++];
			int len = buffer[curr++];
			if (len >= 0) System.arraycopy(buffer, curr, bf, offset, len);
			curr += len;
		}
	}

	@Environment(EnvType.CLIENT)
	public static void handler(SP_HologramUpdate_A msg, Supplier<NetworkContext> context){
		BlockEntity be = Minecraft.getInstance().level.getBlockEntity(msg.te);
		if(be instanceof HologramTE te){
			te.resize(msg.width, msg.height);
			try{
				msg.handle(te);
			} catch (Exception e) {
				e.printStackTrace();
			}
			te.UpdateRenderCache();
		}
	}

	// 为了与 PacketManager 兼容添加的辅助方法
	public Entity getEntity() {
		return null; // 此类不涉及实体，仅返回 null
	}
}
