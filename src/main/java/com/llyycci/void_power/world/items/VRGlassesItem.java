package com.llyycci.void_power.world.items;

import org.jetbrains.annotations.NotNull;

import com.llyycci.void_power.utils.NBTUtils;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;
import com.simibubi.create.content.equipment.armor.BaseArmorItem;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


public class VRGlassesItem extends BaseArmorItem {
    public static final EquipmentSlot SLOT = EquipmentSlot.HEAD;
    public static final Type TYPE = Type.HELMET;
    public static final String ID = "vr_glasses";

    public VRGlassesItem(ArmorMaterial armorMaterial, Properties properties, ResourceLocation textureLoc) {
        super(armorMaterial, TYPE, properties, textureLoc);
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag tag) {

    }

    static final String T_VR_HUB = "vr_hub_pos";
    static final String T_VR_DIM = "vr_hub_dim";

    public static BlockPos getTE(Player player, ItemStack item){
        CompoundTag nbt = item.getOrCreateTag();
        if(nbt.contains(T_VR_HUB)){
            return NBTUtils.BlockPos(nbt.get(T_VR_HUB));
        }
        return null;
    }

    public static ResourceLocation getDim(ItemStack item){
        CompoundTag nbt = item.getOrCreateTag();
        if(nbt.contains(T_VR_DIM)){
            return new ResourceLocation(nbt.getString(T_VR_DIM));
        }
        return null;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext uoc) {
        BlockPos bp = uoc.getClickedPos();
        Player player = uoc.getPlayer();
        if(player != null && player.isShiftKeyDown() && uoc.getLevel().getBlockEntity(bp) instanceof HologramTE){
            CompoundTag nbt =  uoc.getItemInHand().getOrCreateTag();
            nbt.put(T_VR_HUB, NBTUtils.NBT(bp));
            nbt.putString(T_VR_DIM, uoc.getLevel().dimension().location().toString());
            System.out.println("Bind.");
        }
        return super.useOn(uoc);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, InteractionHand hand) {
        if(player.isShiftKeyDown()) return InteractionResultHolder.success(player.getItemInHand(hand));
        return super.use(level, player, hand);
    }

	// Set in properties 设置让VR眼镜能戴在头上 来源于机械动力的工程师护目镜Engineer Goggles
	public static EquipmentSlot getEquipmentSlot(ItemStack stack) {
		return EquipmentSlot.HEAD;
	}
}
