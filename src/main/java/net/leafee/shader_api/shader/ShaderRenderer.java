package net.leafee.shader_api.shader;

import com.mojang.blaze3d.systems.RenderSystem;
import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;

import java.util.HashMap;
import java.util.Map;

public class ShaderRenderer {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    private static Boolean hasRegistered = false;

    public static void set(String id, Boolean enabled){
        finalizeRegistering();
        PostEffectProcessor shader = null;
        try {
            Map.Entry<PostEffectProcessor, Boolean> entry = ShaderList.postShaderList.get(id).entrySet().iterator().next();
            shader = entry.getKey();
        } catch(NullPointerException e){
            ShaderAPI.LOGGER.warn("INVALID ID:" + id + ", NullPointerException: " + e);
        }
        
        if (shader == null) {
            ShaderAPI.LOGGER.error(id + "postEffectProcessor is null");
            return;
        }

        if(enabled) {
            shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
            ShaderList.postShaderList.get(id).entrySet().iterator().next().setValue(true);
        } else {
            ShaderList.postShaderList.get(id).entrySet().iterator().next().setValue(false);
        }
        return;

    }


    public static void disableAll() {
        finalizeRegistering();

        for (Map.Entry<String, HashMap<PostEffectProcessor, Boolean>> entry : ShaderList.postShaderList.entrySet()) {
            set(entry.getKey(), false);
        }
    }


    public static Boolean isEnabled(String id) {
        finalizeRegistering();
        try {
            return ShaderList.postShaderList.get(id).entrySet().iterator().next().getValue();
        } catch (NullPointerException e) {
            ShaderAPI.LOGGER.warn("Cannot check if shader is enabled!, INVALID ID:" + id + "NullPointerException: " + e);
        }
        return false;
    }

    public static PostEffectProcessor getShader(String id) {
        finalizeRegistering();
        try {
            return ShaderList.postShaderList.get(id).entrySet().iterator().next().getKey();
        } catch (NullPointerException e) {
            ShaderAPI.LOGGER.warn("Cannot get shader!, INVALID ID:" + id + "NullPointerException: " + e);
        }
        return null;
    }


    public static void toggle(String id) {
        set(id, !isEnabled(id));
    }


    private static void finalizeRegistering() {
        if (!ShaderRenderer.hasRegistered) {
            ShaderList.finalizeRegisterPostShader();
            hasRegistered = true;
        }
    }
}
