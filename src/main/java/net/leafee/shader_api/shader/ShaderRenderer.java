package net.leafee.shader_api.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;

import java.util.HashMap;
import java.util.Map;

public class ShaderRenderer {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static Boolean hasRegistered = false;

    public static void set(String id, Boolean enabled){
        if (!ShaderRenderer.hasRegistered) {
            ShaderList.finalizeRegisterPostShader();
        }

        ShaderAPI.LOGGER.info(String.valueOf(hasRegistered));

        Map.Entry<PostEffectProcessor, Boolean> entry = ShaderList.postShaderList.get(id).entrySet().iterator().next();
        PostEffectProcessor shader = entry.getKey();

        if (shader == null) {
            ShaderAPI.LOGGER.error("postEffectProcessor is null");
            return;
        }

        if(enabled) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            ShaderList.postShaderList.get(id).entrySet().iterator().next().setValue(true);
        } else {
            shader.close();
            ShaderList.postShaderList.get(id).entrySet().iterator().next().setValue(false);
        }
        return;

    }

    public static void disableAll() {
        for (Map.Entry<String, HashMap<PostEffectProcessor, Boolean>> entry : ShaderList.postShaderList.entrySet()) {
            set(entry.getKey(), false);
        }
    }

    public static void register() {
        ShaderList.finalizeRegisterPostShader();
        hasRegistered = true;
    }

    public static Boolean isEnabled(String id) {
        return ShaderList.postShaderList.get(id).entrySet().iterator().next().getValue();
    }
}
