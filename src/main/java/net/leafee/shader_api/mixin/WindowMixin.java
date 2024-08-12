package net.leafee.shader_api.mixin;

import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(at = @At("TAIL"), method = "onFramebufferSizeChanged")
    private void updateShaderSize(CallbackInfo ci) {
        if(ShaderAPI.postShaderEnabled)
            ShaderAPI.shader.setupDimensions(ShaderAPI.client.getWindow().getFramebufferWidth(), ShaderAPI.client.getWindow().getFramebufferHeight());
    }
}
