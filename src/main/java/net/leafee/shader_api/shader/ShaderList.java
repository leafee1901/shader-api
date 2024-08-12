package net.leafee.shader_api.shader;

import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.util.HashMap;

public class ShaderList {
    public static HashMap<String, HashMap<PostEffectProcessor, Boolean>> postShaderList = new HashMap<String, HashMap<PostEffectProcessor, Boolean>>();


    public static void registerPostShader(String id) {
        postShaderList.put(id, new HashMap<PostEffectProcessor, Boolean>());
        postShaderList.get(id).put(createPostShader(id), false);
    }


    public static PostEffectProcessor createPostShader(String id) {
        try {
            return new PostEffectProcessor(ShaderRenderer.client.getTextureManager(), ShaderRenderer.client.getResourceManager(), ShaderRenderer.client.getFramebuffer(), new Identifier("shaders/post/" + id +".json"));
        } catch (IOException e) {
            return null;

        }
    }

}
