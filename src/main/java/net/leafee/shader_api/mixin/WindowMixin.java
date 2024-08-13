package net.leafee.shader_api.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.shader.ShaderList;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(Window.class)
public class WindowMixin {

    @Inject(at = @At("TAIL"), method = "onFramebufferSizeChanged")
    private void updateShaderSize(CallbackInfo ci) {
        for (Map.Entry<String, HashMap<PostEffectProcessor, Boolean>> entry : ShaderList.postShaderList.entrySet()) {
            String id = entry.getKey();
            PostEffectProcessor shader = ShaderRenderer.getShader(id);
            if (ShaderRenderer.isEnabled(id)) {
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.resetTextureMatrix();
                shader.setupDimensions(ShaderRenderer.client.getWindow().getFramebufferWidth(), ShaderRenderer.client.getWindow().getFramebufferHeight());

            }
        }
    }
}
