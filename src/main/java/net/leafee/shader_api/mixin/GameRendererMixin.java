package net.leafee.shader_api.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateWorldIcon()V"))
    public void render(CallbackInfo ci) {
        if(ShaderRenderer.postShaderEnabled && ShaderRenderer.shader != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            ShaderRenderer.shader.render(ShaderRenderer.client.getTickDelta());
        }
        if(ShaderRenderer.postShaderEnabled && ShaderRenderer.shader2 != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            ShaderRenderer.shader2.render(ShaderRenderer.client.getTickDelta());
        }
    }
}