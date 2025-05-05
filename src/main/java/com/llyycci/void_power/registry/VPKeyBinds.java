package com.llyycci.void_power.registry;

import com.llyycci.void_power.client.renderer.hud.VRGlassesHUDRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class VPKeyBinds {

	public static final List<KeyBind> kbs = new ArrayList<>();
	public static final KeyBind TOGGLE_VR_GLASSES = new KeyBind("vr_glasses", GLFW.GLFW_KEY_I, (pressed) -> {
		if(pressed) {
			VRGlassesHUDRenderer.On = !VRGlassesHUDRenderer.On;
		}
	});

	public static class KeyBind {
		static final String TAB_TITLE = "Void Power";
		public final KeyMapping km;
		public final Consumer<Boolean> callback;

		public KeyBind(String name, int default_key, Consumer<Boolean> callback) {
			this.callback = callback;
			km = new KeyMapping("key_bind." + name, default_key, TAB_TITLE);
			kbs.add(this);
		}
	}

	public static void register() {
		// 注册所有键位绑定
		kbs.forEach(keyBind -> {
			KeyBindingHelper.registerKeyBinding(keyBind.km);
		});

		// 注册按键检测事件
		ClientTickEvents.END_CLIENT_TICK.register(minecraft -> {
			for (KeyBind keyBind : kbs) {
				while (keyBind.km.consumeClick()) {
					keyBind.callback.accept(true);
				}
			}
		});
	}
}
