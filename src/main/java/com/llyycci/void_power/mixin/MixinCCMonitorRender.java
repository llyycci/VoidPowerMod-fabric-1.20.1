package com.llyycci.void_power.mixin;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llyycci.void_power.world.blocks.glass_screen.GlassScreenTE;
import com.mojang.blaze3d.vertex.PoseStack;

import dan200.computercraft.client.render.monitor.MonitorBlockEntityRenderer;
import dan200.computercraft.shared.peripheral.monitor.ClientMonitor;
import dan200.computercraft.shared.peripheral.monitor.MonitorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;

@Mixin(value = MonitorBlockEntityRenderer.class, remap = false)
public abstract class MixinCCMonitorRender {
//由于注入冲突，此Mixin暂时不启用，如需启用，请在void_power.mixin.json的Client部分添加
    @Inject(method = "render(Ldan200/computercraft/shared/peripheral/monitor/MonitorBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At("HEAD"), cancellable = true
    )
    private void renderPatch(MonitorBlockEntity monitor, float partialTicks, PoseStack transform, MultiBufferSource bufferSource, int lightmapCoord, int overlayLight, CallbackInfo ci){
        ClientMonitor originTerminal = monitor.getOriginClientMonitor();
        if(originTerminal == null){
            ci.cancel();
        }
        else {
            // if state is not created and not type for cc monitor.
            MonitorBlockEntity te = originTerminal.getOrigin();
            if(te instanceof GlassScreenTE gste){
            }
        }
    }


}
