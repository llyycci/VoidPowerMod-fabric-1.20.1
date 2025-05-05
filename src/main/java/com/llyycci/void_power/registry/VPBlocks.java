package com.llyycci.void_power.registry;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerBlock;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenBlock;
import com.llyycci.void_power.world.blocks.hologram.HologramBlock;
import com.llyycci.void_power.world.blocks.redstone_link.RSBroadcasterBlock;
import com.llyycci.void_power.world.blocks.redstone_link.RSReceiverBlock;
import com.llyycci.void_power.world.blocks.redstone_link.RSRouterBlock;
import com.llyycci.void_power.world.blocks.void_engine.VoidEngineBlock;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.renderer.RenderType;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.axeOrPickaxe;

public class VPBlocks {
	private static final CreateRegistrate REGISTRATE = VoidPowerMod.registrate();

	public static final BlockEntry<VoidEngineBlock> VOID_ENGINE = REGISTRATE.block(VoidEngineBlock.ID, VoidEngineBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();

	//Blocks.GLASS

	public static final BlockEntry<EngineControllerBlock> ENGINE_CONTROLLER_BLOCK = REGISTRATE.block(EngineControllerBlock.ID, EngineControllerBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();


	public static final BlockEntry<GlassScreenBlock> GLASS_SCREEN_BLOCK = REGISTRATE.block(GlassScreenBlock.ID, GlassScreenBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
//			.blockstate(BlockStateGen.horizontalBlockProvider(true))//test
			//.blockstate(BlockStateGen.)
//			.addLayer(() -> RenderType::solid)
			.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<HologramBlock> HOLOGRAM_BLOCK = REGISTRATE.block(HologramBlock.ID, HologramBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();


	public static final BlockEntry<RSBroadcasterBlock> RS_BROADCASTER_BLOCK = REGISTRATE.block(RSBroadcasterBlock.ID, RSBroadcasterBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			//.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<RSReceiverBlock> RS_RECEIVER_BLOCK = REGISTRATE.block(RSReceiverBlock.ID, RSReceiverBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			//.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();


	public static final BlockEntry<RSRouterBlock> RS_ROUTER_BLOCK = REGISTRATE.block(RSRouterBlock.ID, RSRouterBlock::new)
			.initialProperties(SharedProperties::stone)
			.transform(axeOrPickaxe())
			//.blockstate(BlockStateGen.horizontalBlockProvider(true))
			//.addLayer(() -> RenderType::translucent)
			//.transform(BlockStressDefaults.setImpact(8.0))
			.item()
			.transform(customItemModel())
			.register();

	static {
		REGISTRATE.setCreativeTab(VPCreativeTabs.TAB.key());
		ItemGroupEvents.modifyEntriesEvent(VPCreativeTabs.TAB.key()).register(content -> {
			content.accept(VOID_ENGINE);
			content.accept(ENGINE_CONTROLLER_BLOCK);
			content.accept(GLASS_SCREEN_BLOCK);
			content.accept(HOLOGRAM_BLOCK);
			content.accept(RS_BROADCASTER_BLOCK);
			content.accept(RS_RECEIVER_BLOCK);
			content.accept(RS_ROUTER_BLOCK);
		});
	}
	public static void register(){}
}
