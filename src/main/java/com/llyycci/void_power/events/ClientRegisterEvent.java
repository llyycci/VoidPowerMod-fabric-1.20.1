package com.llyycci.void_power.events;

import com.llyycci.void_power.VoidPowerMod;
import com.llyycci.void_power.client.renderer.VPRenderTypes;

import com.llyycci.void_power.network.PacketManager;
import com.llyycci.void_power.registry.VPKeyBinds;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.renderer.GameRenderer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourceReloadListenerKeys;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class ClientRegisterEvent implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// 注册着色器加载器
		registerShaders();
		// 注册按键绑定
		VPKeyBinds.register();
		// 注册VR眼镜HUD渲染事件
		RenderEvent.register();
		// 注册服务器世界加载事件
		ServerWorldEvent.register();
		PacketManager.ClientInit();
	}

	private void registerShaders(){
		// 使用Fabric API的资源重载监听器
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public ResourceLocation getFabricId() {
				return new ResourceLocation(VoidPowerMod.ID, "shaders");
			}

			@Override
			public void onResourceManagerReload(ResourceManager resourceManager) {
				try {
					// 创建着色器实例
					ShaderInstance textShader = new ShaderInstance(
							resourceManager,
							VoidPowerMod.ID + "/rendertype_text", // 使用字符串格式的路径
//							new ResourceLocation(VoidPowerMod.ID, "rendertype_text").toString(),
							DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP
					);

					// 设置全局着色器实例
					VPRenderTypes.textShader = textShader;

					VoidPowerMod.LOGGER.info("Successfully loaded VoidPower text shader");
				} catch (IOException e) {
					VoidPowerMod.LOGGER.error("Failed to load VoidPower text shader", e);
				}
			}
		});
	}
}
