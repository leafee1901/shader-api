package net.leafee.shader_api.shader;

import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.Map;

public class ShaderRenderer {
    public static MinecraftClient client = MinecraftClient.getInstance();

    public static void set(String id, Boolean enabled){
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

}
