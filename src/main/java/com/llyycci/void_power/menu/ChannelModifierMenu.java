package com.llyycci.void_power.menu;

import com.llyycci.void_power.network.CP.CP_RSI_ChannelModify;
import com.llyycci.void_power.network.PacketManager;
import com.llyycci.void_power.registry.VPItems;
import com.llyycci.void_power.world.redstone.ChannelNetworkHandler;
import com.simibubi.create.foundation.gui.menu.MenuBase;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ChannelModifierMenu extends MenuBase<ItemStack> {

    public ResourceLocation channel;

    public ChannelModifierMenu(MenuType<?> type, int windowId, Inventory inv, @javax.annotation.Nullable FriendlyByteBuf buffer) {
        super(type, windowId, inv, buffer);
    }

    public ChannelModifierMenu(MenuType<?> type, int windowId, Inventory inv, ItemStack holder) {
        super(type, windowId, inv, holder);

    }

    @Override
    protected ItemStack createOnClient(FriendlyByteBuf extraData) {

        return extraData.readItem();
    }

    @Override
    protected void initAndReadInventory(ItemStack contentHolder) {
        CompoundTag nbt = contentHolder.getOrCreateTag();
        if(nbt.contains("channel")){
            channel = new ResourceLocation(nbt.getString("channel"));
        }
        else {
            channel = ChannelNetworkHandler.NULL_CHANNEL;
        }
    }

    @Override
    protected void addSlots() {

    }

    @Override
    protected void saveData(ItemStack contentHolder) {
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    public void SendHandUpdatePack(){
        PacketManager.sendToServer(new CP_RSI_ChannelModify(channel));
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().is(VPItems.CHANNEL_MODIFIER.get());
    }
}
