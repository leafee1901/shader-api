package net.leafee.shader_api.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/GameRenderer;updateWorldIcon()V"))
    public void render(CallbackInfo ci) {
        if(ShaderAPI.postShaderEnabled && ShaderAPI.shader != null) {
            RenderSystem.disableBlend();
            RenderSystem.disableDepthTest();
            RenderSystem.resetTextureMatrix();
            ShaderAPI.shader.render(ShaderAPI.client.getTickDelta());
        }
    }
}