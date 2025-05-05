package com.llyycci.void_power.events;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.client.renderer.hud.VRGlassesHUDRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class RenderEvent {
	private static final Minecraft mc = Minecraft.getInstance();

	public static void register() {
		HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
			if (mc.player != null) {
				// 创建兼容的GuiGraphics对象
				GuiGraphics gg = new GuiGraphics(mc, drawContext.bufferSource());
				VRGlassesHUDRenderer.render(mc.player, gg, tickDelta);
			}
		});
	}
}

