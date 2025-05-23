package com.llyycci.void_power.registry;

//import static com.llyycci.void_power.VoidPowerMod.REGISTRATE;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.client.gui.ChannelModifierGUI;
import com.llyycci.void_power.client.gui.HologramGUI;
import com.llyycci.void_power.client.renderer.tileentities.glass_screen.GlassScreenInstance;
import com.llyycci.void_power.client.renderer.tileentities.glass_screen.ScreenRenderer;
import com.llyycci.void_power.client.renderer.tileentities.hologram.HologramInstance;
import com.llyycci.void_power.client.renderer.tileentities.hologram.HologramRenderer;
import com.llyycci.void_power.client.renderer.tileentities.void_engine.VoidEngineTEInstance;
import com.llyycci.void_power.client.renderer.tileentities.void_engine.VoidEngineTERenderer;
import com.llyycci.void_power.menu.ChannelModifierMenu;
import com.llyycci.void_power.menu.HologramMenu;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerBlock;
import com.llyycci.void_power.world.blocks.engine_controller.EngineControllerTE;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenBlock;
import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;
import com.llyycci.void_power.world.blocks.hologram.HologramBlock;
import com.llyycci.void_power.world.blocks.hologram.HologramTE;
import com.llyycci.void_power.world.blocks.redstone_link.*;
import com.llyycci.void_power.world.blocks.void_engine.VoidEngineBlock;
import com.llyycci.void_power.world.blocks.void_engine.VoidEngineTE;
import com.llyycci.void_power.world.items.ChannelModifierItem;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.MenuEntry;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public class VPTileEntities {
	private static final CreateRegistrate REGISTRATE = VoidPowerMod.registrate();

    public static final BlockEntityEntry<VoidEngineTE> VOID_ENGINE_TE = REGISTRATE
            .blockEntity(VoidEngineBlock.ID, VoidEngineTE::new)
            .instance(() -> VoidEngineTEInstance::new, false)
            .validBlock(VPBlocks.VOID_ENGINE)
            .renderer(() -> VoidEngineTERenderer::new)
            .register();

    public static final BlockEntityEntry<EngineControllerTE> ENGINE_CONTROLLER_TE = REGISTRATE
            .blockEntity(EngineControllerBlock.ID, EngineControllerTE::new)
            .validBlock(VPBlocks.ENGINE_CONTROLLER_BLOCK)
            .register();

    public static final BlockEntityEntry<GlassScreenTE> GLASS_SCREEN_TE = REGISTRATE
            .blockEntity(GlassScreenBlock.ID, GlassScreenTE::new)
            .instance(() -> GlassScreenInstance::new, true)
            .validBlock(VPBlocks.GLASS_SCREEN_BLOCK)
            .renderer(() -> ScreenRenderer::new)
            .register();

    public static final BlockEntityEntry<HologramTE> HOLOGRAM_TE = REGISTRATE
            .blockEntity(HologramBlock.ID, HologramTE::new)
            .instance(() -> HologramInstance::new, true)
            .validBlock(VPBlocks.HOLOGRAM_BLOCK)
            .renderer(() -> HologramRenderer::new)
            .register();


    public static final BlockEntityEntry<RSBroadcasterTE> RS_BROADCASTER_TE = REGISTRATE
            .blockEntity(RSBroadcasterBlock.ID, RSBroadcasterTE::new)
            //.instance(() -> HologramInstance::new, true)
            .validBlock(VPBlocks.RS_BROADCASTER_BLOCK)
            //.renderer(() -> HologramRenderer::new)
            .register();

    public static final BlockEntityEntry<RSReceiverTE> RS_RECEIVER_TE = REGISTRATE
            .blockEntity(RSReceiverBlock.ID, RSReceiverTE::new)
            //.instance(() -> HologramInstance::new, true)
            .validBlock(VPBlocks.RS_RECEIVER_BLOCK)
            //.renderer(() -> HologramRenderer::new)
            .register();


    public static final BlockEntityEntry<RSRouterTE> RS_ROUTER_TE = REGISTRATE
            .blockEntity(RSRouterBlock.ID, RSRouterTE::new)
            //.instance(() -> HologramInstance::new, true)
            .validBlock(VPBlocks.RS_ROUTER_BLOCK)
            //.renderer(() -> HologramRenderer::new)
            .register();

    public static final MenuEntry<HologramMenu> HOLOGRAM_GUI = REGISTRATE
            .menu(HologramBlock.ID, HologramMenu::new, () -> HologramGUI::new).register();

    public static final MenuEntry<ChannelModifierMenu> CHANNEL_MODIFIER_GUI = REGISTRATE
            .menu(ChannelModifierItem.ID, ChannelModifierMenu::new, () -> ChannelModifierGUI::new).register();

    public static void register(){

    }




}
