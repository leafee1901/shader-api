package net.leafee.shader_api.shader;

import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class ShaderRenderer {
    public static MinecraftClient client = MinecraftClient.getInstance();
    public static PostEffectProcessor shader;
    public static PostEffectProcessor shader2;
    public static boolean postShaderEnabled = false;

    private static PostEffectProcessor getShader(String id) {
        try {
            return new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), new Identifier("shaders/post/" + id +".json"));
        } catch (IOException e) {
            return null;

        }
    }

    public static void set(String id, Boolean enabled){
        if(shader != null)
            shader.close();

        shader = getShader(id);
        if (shader == null) {
            ShaderAPI.LOGGER.error("Shader is null");
            return;
        }

        if(enabled) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            postShaderEnabled = true;
        } else {
            shader.close();
            postShaderEnabled = false;
        }
        return;

    }
    public static void set2(String id, Boolean enabled){
        if(shader2 != null)
            shader2.close();

        shader2 = getShader(id);
        if (shader2 == null) {
            ShaderAPI.LOGGER.error("Shader is null2");
            return;
        }

        if(enabled) {
            shader2.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            postShaderEnabled = true;
        } else {
            shader2.close();
            postShaderEnabled = false;
        }
        return;

    }

}
