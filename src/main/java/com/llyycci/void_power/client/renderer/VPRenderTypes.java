package com.llyycci.void_power.client.renderer;

import com.llyycci.void_power.config.ModConfigs;
import com.llyycci.void_power.VoidPowerMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import dan200.computercraft.client.render.RenderTypes;
import dan200.computercraft.client.render.text.FixedWidthFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class VPRenderTypes {

	public static final ResourceLocation FONT = new ResourceLocation(VoidPowerMod.ID, "textures/block/term_font.png");
	public static final ResourceLocation FONT_NEG = new ResourceLocation(VoidPowerMod.ID, "textures/block/term_font_neg.png");

	//public static final ResourceLocation FONT = new ResourceLocation(VoidPowerMod.MODID, "textures/block/term_font.png");
	public static final RenderType TERMINAL = getText(VPRenderTypes.FONT);
	public static final RenderType TERMINAL_NEG = getText(VPRenderTypes.FONT_NEG);
	public static final RenderType HOLOGRAM = getHologram(FixedWidthFontRenderer.FONT);

	private static RenderType getText(ResourceLocation locationIn) {
		RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
				.setShaderState(new RenderStateShard.ShaderStateShard(VPRenderTypes::text))
				.setTextureState(new TextureStateWithFiltering(locationIn))
				.setTransparencyState(RenderType.ADDITIVE_TRANSPARENCY)
				.setLightmapState(RenderType.LIGHTMAP)
				.createCompositeState(false);
		return RenderType.create("cct_text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, rendertype$state);
	}

	public static RenderType getHologram(ResourceLocation locationIn) {
		RenderType.CompositeState rendertype$state = RenderType.CompositeState.builder()
				.setShaderState(new RenderStateShard.ShaderStateShard(VPRenderTypes::text))
				.setTextureState(new TextureStateWithFiltering(locationIn))
				.setTransparencyState(RenderType.ADDITIVE_TRANSPARENCY)
				.setLightmapState(RenderType.LIGHTMAP)
				.createCompositeState(false);
		return RenderType.create("cct_text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 256, false, true, rendertype$state);
	}

	// 替代CustomizableTextureState，因为在Fabric中字段是final的
	private static class TextureStateWithFiltering extends RenderStateShard.TextureStateShard {

		private final ResourceLocation textureLocation;

		private TextureStateWithFiltering(ResourceLocation resLoc) {
			super(resLoc, false, false);
			this.textureLocation = resLoc;
		}

		@Override
		public void setupRenderState() {
			// 在这里设置过滤器参数，而不是修改字段
			TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
			texturemanager.getTexture(this.textureLocation).setFilter(true, false);
			RenderSystem.setShaderTexture(0, this.textureLocation);
		}
	}

	public static ShaderInstance textShader;

	public static ShaderInstance text(){
		return ModConfigs.common().SCREEN_FORCED_USE_VANILLA_SHADER.get() ? textShader : RenderTypes.getTerminalShader();
	}

	public static ShaderInstance textNeg(){
		return ModConfigs.common().SCREEN_FORCED_USE_VANILLA_SHADER.get() ? textShader : RenderTypes.getTerminalShader();
	}
}
