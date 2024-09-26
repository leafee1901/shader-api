package net.leafee.shader_api.shader;

import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;

import java.util.HashMap;
import java.util.Map;

public class ShaderRenderer {
    public static final MinecraftClient client = MinecraftClient.getInstance();
    private static Boolean hasRegistered = false;

    private static void finalizeRegistering() {
        if (!ShaderRenderer.hasRegistered) {
            ShaderList.finalizeRegisterPostShader();
            hasRegistered = true;
        }
    }

    public static void set(String id, int duration){
        set(id, true, duration);
    }

    public static void set(String id, Boolean enabled){
        set(id, enabled, -1);
    }

    public static void set(String id, Boolean enabled, int duration){
        finalizeRegistering();
        if (duration == -1) {
            duration = 2147483647;
        }

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
            ShaderList.postShaderDurationList.replace(id, duration);
        } else {
            ShaderList.postShaderList.get(id).entrySet().iterator().next().setValue(false);
            ShaderList.postShaderDurationList.replace(id, 0);
        }
    }


    public static void disableAll() {
        finalizeRegistering();

        for (Map.Entry<String, HashMap<PostEffectProcessor, Boolean>> entry : ShaderList.postShaderList.entrySet()) {
            set(entry.getKey(), false);
        }
    }


    public static void toggle(String id) {
        set(id, !isEnabled(id));

    }




    public static int getDuration(String id) {
        finalizeRegistering();
        try {
            return ShaderList.postShaderDurationList.get(id);
        } catch (NullPointerException e) {
            ShaderAPI.LOGGER.warn("Cannot check shader duration!, INVALID ID:" + id + "NullPointerException: " + e);
        }
        return 0;
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
}
