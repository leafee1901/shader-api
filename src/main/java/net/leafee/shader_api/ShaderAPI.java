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

		KeyBinding enableKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("enable", InputUtil.GLFW_KEY_F7, "shader api"));
		KeyBinding disableKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("disable", InputUtil.GLFW_KEY_F8, "shader api"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (enableKey.wasPressed()) {

					ShaderList.postShaderList.clear();
					ShaderList.registerPostShader("blobs2");
					ShaderList.registerPostShader("neurosis");
					LOGGER.info(String.valueOf(ShaderList.postShaderList));
					ShaderRenderer.set("neurosis", true);
					ShaderRenderer.set("blobs2", true);
				}
				if (disableKey.wasPressed()) {

					ShaderList.postShaderList.clear();
					ShaderList.registerPostShader("blobs2");
					ShaderList.registerPostShader("neurosis");
					LOGGER.info(String.valueOf(ShaderList.postShaderList));
					ShaderRenderer.set("neurosis", false);
					ShaderRenderer.set("blobs2", false);
				}
			}
		});
	}

}