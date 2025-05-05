package com.llyycci.void_power.network.SP;

import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.Supplier;

public class SP_UpdateGlassScreen {

	public char c;
	public boolean enable;
	public BlockPos te;
	private ServerPlayer player; // 用于兼容 PacketManager 的 sendToAll 方法

	public SP_UpdateGlassScreen() {

	}

	public SP_UpdateGlassScreen(BlockPos b, boolean e, char c) {
		this.c = c;
		this.enable = e;
		this.te = b;
	}

	public static SP_UpdateGlassScreen decode(FriendlyByteBuf buf) {
		SP_UpdateGlassScreen data = new SP_UpdateGlassScreen();
		data.te = buf.readBlockPos();
		data.enable = buf.readBoolean();
		data.c = buf.readChar();
		return data;
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeBlockPos(te);
		buf.writeBoolean(enable);
		buf.writeChar(c);
	}

	@Environment(EnvType.CLIENT)
	public static void handler(SP_UpdateGlassScreen msg, Supplier<NetworkContext> context) {
		BlockEntity be = Minecraft.getInstance().level.getBlockEntity(msg.te);
		if (be instanceof GlassScreenTE te) {
			te.SetTrans(msg.enable);
			te.SetTransCol(msg.c);
			te.setTChanged();
		}
	}

	// 设置玩家，用于兼容 PacketManager 中的 sendToAll 方法
	public void setPlayer(ServerPlayer player) {
		this.player = player;
	}

	// 获取玩家，用于兼容 PacketManager 中的 sendToAll 方法
	public ServerPlayer getPlayer() {
		return player;
	}

	// 为了与 PacketManager 兼容添加的辅助方法
	public Entity getEntity() {
		return null; // 此类不涉及实体，仅返回 null
	}
}
