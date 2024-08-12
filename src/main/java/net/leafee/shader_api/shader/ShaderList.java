package net.leafee.shader_api.shader;

import net.leafee.shader_api.ShaderAPI;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.util.Identifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShaderList {
    public static HashMap<String, HashMap<PostEffectProcessor, Boolean>> postShaderList = new HashMap<String, HashMap<PostEffectProcessor, Boolean>>();
    public static List<String> postShaderIdMemory = new ArrayList<String>();

    public static void registerPostShader(String id) {
        postShaderIdMemory.add(id);
    }

    public static void finalizeRegisterPostShader() {
        for (String id : postShaderIdMemory) {
            postShaderList.put(id, new HashMap<PostEffectProcessor, Boolean>());
            postShaderList.get(id).put(createPostShader(id), false);
        }
    }




    public static PostEffectProcessor createPostShader(String id) {
        try {
            return new PostEffectProcessor(ShaderRenderer.client.getTextureManager(), ShaderRenderer.client.getResourceManager(), ShaderRenderer.client.getFramebuffer(), new Identifier("shaders/post/" + id +".json"));
        } catch (IOException e) {
            return null;

        }
    }

}
