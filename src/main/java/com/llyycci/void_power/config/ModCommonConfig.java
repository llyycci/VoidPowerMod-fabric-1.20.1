package com.llyycci.void_power.config;

import com.simibubi.create.foundation.config.ConfigBase;

public class ModCommonConfig extends ConfigBase {

	public final ConfigFloat STRESS_PER_RPM = f(8, 0, Float.MAX_VALUE, "EngineStressPerRPM", Comments.stressPerRPM);
	public final ConfigFloat MASS_PER_STRESS = f(2500, 0, Float.MAX_VALUE, "EngineMassPerStress", Comments.massPerStress);
	public final ConfigFloat CC_DEFAULT_MIN_PERIOD_FACTOR = f(1, 0.5F, 8,"CC_DEFAULT_MIN_PERIOD_FACTOR", Comments.cc_default_min_period_factor);
	public final ConfigBool SCREEN_FORCED_USE_VANILLA_SHADER = b(false, "SCREEN_FORCED_USE_VANILLA_SHADER", Comments.screen_forced_use_vanilla_shader);
	public final ConfigBool RESET_CONTROLLER_WHEN_LEFT = b(true, "RESET_CONTROLLER_WHEN_LEFT", Comments.reset_controller_when_left);
	public final ConfigInt HOLOGRAM_RES_X_MAX = i(1024, 128, 2048, "HOLOGRAM_WIDTH_MAX", Comments.hologram_res_x_max);
	public final ConfigInt HOLOGRAM_RES_Y_MAX = i(1024, 128, 2048, "HOLOGRAM_HEIGHT_MAX", Comments.hologram_res_y_max);
	public final ConfigInt HOLOGRAM_BUFFER_COUNT = i(8, 2, 32, "HOLOGRAM_BUFFER_COUNT", Comments.hologram_buffer_count);
	public final ConfigBool WIRELESS_HUB_UNLIMITED = b(true, "WIRELESS_HUB_UNLIMITED", Comments.wireless_hub_unlimited);
	public final ConfigInt HOLOGRAM_FONT_COUNT = i(2, 0, 8, "HOLOGRAM_FONT_COUNT", Comments.hologram_font_count);
	public final ConfigInt HOLOGRAM_FORCE_FULL_UPDATE_TICK = i(20, -1, 400, "HOLOGRAM_FORCE_FULL_UPDATE_TICK", Comments.hologram_force_full_update_tick);
	@Override
	public String getName() {
		return "common";
	}

	private static class Comments {
		static String stressPerRPM = "Engine cost stress per rpm.";
		static String massPerStress = "The weight that can be driven of per stress*rpm cost.";
		static String cc_default_min_period_factor = "Time factor of CC‘s Computer can run max per time(default time is 5ms). If your CPU's clock speed is not high enough, don't change this";
		static String screen_forced_use_vanilla_shader = "Force glass screen render use vanilla shader. If you use shader pack and has some render issue with glass screen, try turn on this.";
		static String reset_controller_when_left = "Reset input state of tweaked controller block after player left.";
		static String hologram_res_x_max = "Max width can set for a hologram.";
		static String hologram_res_y_max = "Max height can set for a hologram.";
		static String hologram_buffer_count = "Max count of frame buffer in a hologram.";
		static String wireless_hub_unlimited = "Remove wireless peripheral hub distance limited.";
		static String hologram_font_count = "Max count of font for a hologram.";
		static String hologram_force_full_update_tick = "The max ticks between two force full sync(a tick after a call of 'hologram.Flush(true)') of hologram. set zero to disable forced full update.";
	}
}
