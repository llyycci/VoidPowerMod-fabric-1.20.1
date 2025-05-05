package com.llyycci.void_power.registry;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.world.items.ChannelModifierItem;
import com.llyycci.void_power.world.items.VRGlassesItem;
import com.simibubi.create.content.equipment.armor.AllArmorMaterials;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

public class VPItems {
	private static final CreateRegistrate REGISTRATE = VoidPowerMod.registrate();

	public static final ItemEntry<VRGlassesItem> VR_GLASSES =
			REGISTRATE.item(VRGlassesItem.ID,
							p -> new VRGlassesItem(AllArmorMaterials.COPPER, p, VoidPowerMod.getRL(VRGlassesItem.ID)))
					.properties(p -> {
						if (p instanceof FabricItemSettings fp) {
							fp.equipmentSlot(VRGlassesItem::getEquipmentSlot);
						}
						return p;
					})
					.register();//TODO: 研究fabric如何设置tags

	public static final ItemEntry<ChannelModifierItem> CHANNEL_MODIFIER =
			REGISTRATE.item(ChannelModifierItem.ID, ChannelModifierItem::new)
					.register();

	static {
		REGISTRATE.setCreativeTab(VPCreativeTabs.TAB.key());
		ItemGroupEvents.modifyEntriesEvent(VPCreativeTabs.TAB.key()).register(content -> {
			content.accept(VR_GLASSES);
			content.accept(CHANNEL_MODIFIER);
		});
	}
	public static void register() {}
}
