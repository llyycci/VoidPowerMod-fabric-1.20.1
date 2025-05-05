package com.llyycci.void_power.network.CP;

import java.util.function.Supplier;

import com.llyycci.void_power.network.PacketManager.NetworkContext;
import com.llyycci.void_power.registry.VPItems;
import com.llyycci.void_power.world.items.ChannelModifierItem;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class CP_RSI_ChannelModify {

	final ResourceLocation channel;

	public CP_RSI_ChannelModify(ResourceLocation channel){
		this.channel = channel;
	}

	public static CP_RSI_ChannelModify decode(FriendlyByteBuf buf) {
		ResourceLocation c = buf.readResourceLocation();
		return new CP_RSI_ChannelModify(c);
	}

	public void encode(FriendlyByteBuf buf) {
		buf.writeResourceLocation(channel);
	}

	public static void handler(CP_RSI_ChannelModify msg, Supplier<NetworkContext> context){
		NetworkContext ctx = context.get();
		ServerPlayer player = ctx.getSender();
		if(player != null){
			ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
			if(item.is(VPItems.CHANNEL_MODIFIER.get())){
				ChannelModifierItem.setChannel(item, msg.channel);
				System.out.println("BBB");
			}
		}
	}
}
