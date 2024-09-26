package net.leafee.shader_api.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.shader.ShaderList;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateWorldIcon()V"))
    public void render(CallbackInfo ci) {
        for (Map.Entry<String, HashMap<PostEffectProcessor, Boolean>> entry : ShaderList.postShaderList.entrySet()) {
            String id = entry.getKey();

            PostEffectProcessor shader = ShaderRenderer.getShader(id);
            if (ShaderRenderer.isEnabled(id) && shader != null && !(ShaderList.postShaderDurationList.get(id) <= 0)) {
                RenderSystem.disableBlend();
                RenderSystem.disableDepthTest();
                RenderSystem.resetTextureMatrix();
                shader.render(ShaderRenderer.client.getTickDelta());
            }
        }
    }
}