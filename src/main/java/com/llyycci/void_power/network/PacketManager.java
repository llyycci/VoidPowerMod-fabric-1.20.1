package com.llyycci.void_power.network;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.network.CP.CP_HologramInputEvent;
import com.llyycci.void_power.network.CP.CP_HologramRename;
import com.llyycci.void_power.network.CP.CP_HologramUpdateRequest;
import com.llyycci.void_power.network.CP.CP_RSI_ChannelModify;
import com.llyycci.void_power.network.SP.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class PacketManager {
	private static final String VERSION = "1.4";

	// 定义所有数据包的ID
	public static final ResourceLocation SP_UPDATE_GLASS_SCREEN_ID = new ResourceLocation(VoidPowerMod.ID, "sp_update_glass_screen");
	public static final ResourceLocation SP_HOLOGRAM_UPDATE_A_ID = new ResourceLocation(VoidPowerMod.ID, "sp_hologram_update_a");
	public static final ResourceLocation SP_HOLOGRAM_UPDATE_B_ID = new ResourceLocation(VoidPowerMod.ID, "sp_hologram_update_b");
	public static final ResourceLocation SP_HOLOGRAM_POSE_UPDATE_ID = new ResourceLocation(VoidPowerMod.ID, "sp_hologram_pose_update");
	public static final ResourceLocation CP_HOLOGRAM_UPDATE_REQUEST_ID = new ResourceLocation(VoidPowerMod.ID, "cp_hologram_update_request");
	public static final ResourceLocation CP_HOLOGRAM_INPUT_EVENT_ID = new ResourceLocation(VoidPowerMod.ID, "cp_hologram_input_event");
	public static final ResourceLocation SP_HOLOGRAM_RENAME_ID = new ResourceLocation(VoidPowerMod.ID, "sp_hologram_rename");
	public static final ResourceLocation CP_HOLOGRAM_RENAME_ID = new ResourceLocation(VoidPowerMod.ID, "cp_hologram_rename");
	public static final ResourceLocation CP_RSI_CHANNEL_MODIFY_ID = new ResourceLocation(VoidPowerMod.ID, "cp_rsi_channel_modify");

	public PacketManager() {
	}

	// 通用方法，用于发送数据包
	private static <T> FriendlyByteBuf createPacketBuffer(T message, BiConsumer<T, FriendlyByteBuf> encoder) {
		FriendlyByteBuf buffer = PacketByteBufs.create();
		encoder.accept(message, buffer);
		return buffer;
	}

	// 发送到服务器
	public static <T> void sendToServer(T message, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder) {
		ClientPlayNetworking.send(packetId, createPacketBuffer(message, encoder));
	}

	// 发送到特定玩家
	public static <T> void sendToPlayer(T message, ServerPlayer player, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder) {
		ServerPlayNetworking.send(player, packetId, createPacketBuffer(message, encoder));
	}

	// 发送到所有玩家
	public static <T> void sendToAll(T message, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder, net.minecraft.server.MinecraftServer server) {
		FriendlyByteBuf buffer = createPacketBuffer(message, encoder);
		for (ServerPlayer player : PlayerLookup.all(server)) {
			ServerPlayNetworking.send(player, packetId, buffer);
		}
	}

	// 发送到追踪实体的所有玩家
	public static <T> void sendToAllPlayerTrackingThisEntity(T message, Entity entity, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder) {
		FriendlyByteBuf buffer = createPacketBuffer(message, encoder);
		for (ServerPlayer player : PlayerLookup.tracking(entity)) {
			ServerPlayNetworking.send(player, packetId, buffer);
		}
	}

	// 发送到追踪实体的所有玩家和自己
	public static <T> void sendToAllPlayerTrackingThisEntityWithSelf(T message, ServerPlayer entity, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder) {
		FriendlyByteBuf buffer = createPacketBuffer(message, encoder);
		ServerPlayNetworking.send(entity, packetId, buffer);
		for (ServerPlayer player : PlayerLookup.tracking(entity)) {
			if (player != entity) {
				ServerPlayNetworking.send(player, packetId, buffer);
			}
		}
	}

	// 发送到追踪方块实体所在区块的所有玩家
	public static <T> void sendToAllPlayerTrackingThisBlock(T message, BlockEntity te, ResourceLocation packetId, BiConsumer<T, FriendlyByteBuf> encoder) {
		FriendlyByteBuf buffer = createPacketBuffer(message, encoder);
		for (ServerPlayer player : PlayerLookup.tracking((ServerLevel) te.getLevel(), te.getBlockPos())) {
			ServerPlayNetworking.send(player, packetId, buffer);
		}
	}

	// 为了与原代码兼容的包装方法
	public static <T> void sendToServer(T message) {
		if (message instanceof CP_HologramUpdateRequest) {
			sendToServer((CP_HologramUpdateRequest)message, CP_HOLOGRAM_UPDATE_REQUEST_ID, CP_HologramUpdateRequest::encode);
		} else if (message instanceof CP_HologramInputEvent) {
			sendToServer((CP_HologramInputEvent)message, CP_HOLOGRAM_INPUT_EVENT_ID, CP_HologramInputEvent::encode);
		} else if (message instanceof CP_HologramRename) {
			sendToServer((CP_HologramRename)message, CP_HOLOGRAM_RENAME_ID, CP_HologramRename::encode);
		} else if (message instanceof CP_RSI_ChannelModify) {
			sendToServer((CP_RSI_ChannelModify)message, CP_RSI_CHANNEL_MODIFY_ID, CP_RSI_ChannelModify::encode);
		}
	}

	// 包装方法：发送到客户端
	public static <T> void sendToClient(T message, ServerPlayer player) {
		if (message instanceof SP_UpdateGlassScreen) {
			sendToPlayer((SP_UpdateGlassScreen)message, player, SP_UPDATE_GLASS_SCREEN_ID, SP_UpdateGlassScreen::encode);
		} else if (message instanceof SP_HologramUpdate_A) {
			sendToPlayer((SP_HologramUpdate_A)message, player, SP_HOLOGRAM_UPDATE_A_ID, SP_HologramUpdate_A::encode);
		} else if (message instanceof SP_HologramUpdate_B) {
			sendToPlayer((SP_HologramUpdate_B)message, player, SP_HOLOGRAM_UPDATE_B_ID, SP_HologramUpdate_B::encode);
		} else if (message instanceof SP_HologramPoseUpdate) {
			sendToPlayer((SP_HologramPoseUpdate)message, player, SP_HOLOGRAM_POSE_UPDATE_ID, SP_HologramPoseUpdate::encode);
		} else if (message instanceof SP_HologramRename) {
			sendToPlayer((SP_HologramRename)message, player, SP_HOLOGRAM_RENAME_ID, SP_HologramRename::encode);
		}
	}

	// 发送到所有玩家的包装方法
	public static <T> void sendToAll(T message) {
		// 需要获取服务器实例
		if (message instanceof SP_UpdateGlassScreen) {
			SP_UpdateGlassScreen msg = (SP_UpdateGlassScreen) message;
			ServerPlayer player = msg.getPlayer();
			if (player != null && player.getServer() != null) {
				sendToAll((SP_UpdateGlassScreen)message, SP_UPDATE_GLASS_SCREEN_ID, SP_UpdateGlassScreen::encode, player.getServer());
			}
		} else if (message instanceof SP_HologramUpdate_A && ((SP_HologramUpdate_A) message).getEntity() != null) {
			Entity entity = ((SP_HologramUpdate_A) message).getEntity();
			if (entity.getServer() != null) {
				sendToAll((SP_HologramUpdate_A)message, SP_HOLOGRAM_UPDATE_A_ID, SP_HologramUpdate_A::encode, entity.getServer());
			}
		}
		// 可以根据需要添加其他类型
	}

	// 发送到追踪实体的所有玩家的包装方法
	public static <T> void sendToAllPlayerTrackingThisEntity(T message, Entity entity) {
		if (message instanceof SP_UpdateGlassScreen) {
			sendToAllPlayerTrackingThisEntity((SP_UpdateGlassScreen)message, entity, SP_UPDATE_GLASS_SCREEN_ID, SP_UpdateGlassScreen::encode);
		} else if (message instanceof SP_HologramUpdate_A) {
			sendToAllPlayerTrackingThisEntity((SP_HologramUpdate_A)message, entity, SP_HOLOGRAM_UPDATE_A_ID, SP_HologramUpdate_A::encode);
		} else if (message instanceof SP_HologramUpdate_B) {
			sendToAllPlayerTrackingThisEntity((SP_HologramUpdate_B)message, entity, SP_HOLOGRAM_UPDATE_B_ID, SP_HologramUpdate_B::encode);
		} else if (message instanceof SP_HologramPoseUpdate) {
			sendToAllPlayerTrackingThisEntity((SP_HologramPoseUpdate)message, entity, SP_HOLOGRAM_POSE_UPDATE_ID, SP_HologramPoseUpdate::encode);
		} else if (message instanceof SP_HologramRename) {
			sendToAllPlayerTrackingThisEntity((SP_HologramRename)message, entity, SP_HOLOGRAM_RENAME_ID, SP_HologramRename::encode);
		}
	}

	// 包装方法：发送到玩家
	public static <T> void sendToPlayer(T message, ServerPlayer player) {
		sendToClient(message, player);
	}

	// 发送到追踪实体的所有玩家和自己的包装方法
	public static <T> void sendToAllPlayerTrackingThisEntityWithSelf(T message, ServerPlayer entity) {
		if (message instanceof SP_UpdateGlassScreen) {
			sendToAllPlayerTrackingThisEntityWithSelf((SP_UpdateGlassScreen)message, entity, SP_UPDATE_GLASS_SCREEN_ID, SP_UpdateGlassScreen::encode);
		} else if (message instanceof SP_HologramUpdate_A) {
			sendToAllPlayerTrackingThisEntityWithSelf((SP_HologramUpdate_A)message, entity, SP_HOLOGRAM_UPDATE_A_ID, SP_HologramUpdate_A::encode);
		} else if (message instanceof SP_HologramUpdate_B) {
			sendToAllPlayerTrackingThisEntityWithSelf((SP_HologramUpdate_B)message, entity, SP_HOLOGRAM_UPDATE_B_ID, SP_HologramUpdate_B::encode);
		} else if (message instanceof SP_HologramPoseUpdate) {
			sendToAllPlayerTrackingThisEntityWithSelf((SP_HologramPoseUpdate)message, entity, SP_HOLOGRAM_POSE_UPDATE_ID, SP_HologramPoseUpdate::encode);
		} else if (message instanceof SP_HologramRename) {
			sendToAllPlayerTrackingThisEntityWithSelf((SP_HologramRename)message, entity, SP_HOLOGRAM_RENAME_ID, SP_HologramRename::encode);
		}
	}

	// 发送到追踪方块实体的所有玩家的包装方法
	public static <T> void sendToAllPlayerTrackingThisBlock(T message, BlockEntity te) {
		if (message instanceof SP_UpdateGlassScreen) {
			sendToAllPlayerTrackingThisBlock((SP_UpdateGlassScreen)message, te, SP_UPDATE_GLASS_SCREEN_ID, SP_UpdateGlassScreen::encode);
		} else if (message instanceof SP_HologramUpdate_A) {
			sendToAllPlayerTrackingThisBlock((SP_HologramUpdate_A)message, te, SP_HOLOGRAM_UPDATE_A_ID, SP_HologramUpdate_A::encode);
		} else if (message instanceof SP_HologramUpdate_B) {
			sendToAllPlayerTrackingThisBlock((SP_HologramUpdate_B)message, te, SP_HOLOGRAM_UPDATE_B_ID, SP_HologramUpdate_B::encode);
		} else if (message instanceof SP_HologramPoseUpdate) {
			sendToAllPlayerTrackingThisBlock((SP_HologramPoseUpdate)message, te, SP_HOLOGRAM_POSE_UPDATE_ID, SP_HologramPoseUpdate::encode);
		} else if (message instanceof SP_HologramRename) {
			sendToAllPlayerTrackingThisBlock((SP_HologramRename)message, te, SP_HOLOGRAM_RENAME_ID, SP_HologramRename::encode);
		}
	}

	// 初始化方法：服务端注册数据包
	public static void Init() {
		registerServerPackets();
//		registerClientPackets();
	}

	// 初始化方法：客户端注册数据包
	@Environment(EnvType.CLIENT)
	public static void ClientInit(){
		registerClientPackets();
	}

	// 注册服务器接收的数据包（客户端发送的）
	private static void registerServerPackets() {
		ServerPlayNetworking.registerGlobalReceiver(CP_HOLOGRAM_UPDATE_REQUEST_ID, (server, player, handler, buf, responseSender) -> {
			CP_HologramUpdateRequest message = CP_HologramUpdateRequest.decode(buf);
			server.execute(() -> CP_HologramUpdateRequest.handler(message, ctx(player)));
		});

		ServerPlayNetworking.registerGlobalReceiver(CP_HOLOGRAM_INPUT_EVENT_ID, (server, player, handler, buf, responseSender) -> {
			CP_HologramInputEvent message = CP_HologramInputEvent.decode(buf);
			server.execute(() -> CP_HologramInputEvent.handler(message, ctx(player)));
		});

		ServerPlayNetworking.registerGlobalReceiver(CP_HOLOGRAM_RENAME_ID, (server, player, handler, buf, responseSender) -> {
			CP_HologramRename message = CP_HologramRename.decode(buf);
			server.execute(() -> CP_HologramRename.handler(message, ctx(player)));
		});

		ServerPlayNetworking.registerGlobalReceiver(CP_RSI_CHANNEL_MODIFY_ID, (server, player, handler, buf, responseSender) -> {
			CP_RSI_ChannelModify message = CP_RSI_ChannelModify.decode(buf);
			server.execute(() -> CP_RSI_ChannelModify.handler(message, ctx(player)));
		});
	}

	// 注册客户端接收的数据包（服务器发送的）
	@Environment(EnvType.CLIENT)
	private static void registerClientPackets() {
		ClientPlayNetworking.registerGlobalReceiver(SP_UPDATE_GLASS_SCREEN_ID, (client, handler, buf, responseSender) -> {
			SP_UpdateGlassScreen message = SP_UpdateGlassScreen.decode(buf);
			client.execute(() -> SP_UpdateGlassScreen.handler(message, ctxClient()));
		});

		ClientPlayNetworking.registerGlobalReceiver(SP_HOLOGRAM_UPDATE_A_ID, (client, handler, buf, responseSender) -> {
			SP_HologramUpdate_A message = SP_HologramUpdate_A.decode(buf);
			client.execute(() -> SP_HologramUpdate_A.handler(message, ctxClient()));
		});

		ClientPlayNetworking.registerGlobalReceiver(SP_HOLOGRAM_UPDATE_B_ID, (client, handler, buf, responseSender) -> {
			SP_HologramUpdate_B message = SP_HologramUpdate_B.decode(buf);
			client.execute(() -> SP_HologramUpdate_B.handler(message, ctxClient()));
		});

		ClientPlayNetworking.registerGlobalReceiver(SP_HOLOGRAM_POSE_UPDATE_ID, (client, handler, buf, responseSender) -> {
			SP_HologramPoseUpdate message = SP_HologramPoseUpdate.decode(buf);
			client.execute(() -> SP_HologramPoseUpdate.handler(message, ctxClient()));
		});

		ClientPlayNetworking.registerGlobalReceiver(SP_HOLOGRAM_RENAME_ID, (client, handler, buf, responseSender) -> {
			SP_HologramRename message = SP_HologramRename.decode(buf);
			client.execute(() -> SP_HologramRename.handler(message, ctxClient()));
		});
	}

	// 创建上下文对象，用于兼容原有的处理器方法
	private static Supplier<NetworkContext> ctx(ServerPlayer player) {
		return () -> new NetworkContext(player);
	}

	@Environment(EnvType.CLIENT)
	private static Supplier<NetworkContext> ctxClient() {
		return () -> new NetworkContext(null);
	}

	// 网络上下文类，用于兼容原代码中的上下文参数
	public static class NetworkContext {
		private final ServerPlayer sender;

		public NetworkContext(ServerPlayer sender) {
			this.sender = sender;
		}

		public ServerPlayer getSender() {
			return sender;
		}
	}
}
