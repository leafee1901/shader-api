package net.leafee.shader_api;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.leafee.shader_api.shader.ShaderList;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShaderAPI implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shader_api");

	//blobs2 = id:2, name:blobs2

	@Override
	public void onInitialize() {

		KeyBinding toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("toggle", InputUtil.GLFW_KEY_F7, "shader api"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (toggleKey.wasPressed()) {

					ShaderList.postShaderList.clear();
					ShaderList.registerPostShader("blobs2");
					ShaderList.registerPostShader("neurosis");
					LOGGER.info(String.valueOf(ShaderList.postShaderList));


					if (ShaderRenderer.postShaderEnabled) {
						ShaderRenderer.shader.close();
						ShaderRenderer.postShaderEnabled = false;
					} else {
						ShaderRenderer.postShaderEnabled = true;
						ShaderRenderer.set("neurosis", true);
						ShaderRenderer.set("blobs2", true);
						LOGGER.info("toggled post shader");
					}

				}
			}
		});
	}

}