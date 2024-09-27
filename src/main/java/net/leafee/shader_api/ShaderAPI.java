package net.leafee.shader_api;

import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.leafee.shader_api.shader.ShaderList;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class ShaderAPI implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shader_api");

	@Override
	public void onInitializeClient() {

		ShaderList.registerPostShader("blobs2");
		ShaderList.registerPostShader("neurosis");

		KeyBinding neurosisKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("neurosis", InputUtil.GLFW_KEY_F6, "shader api"));
		KeyBinding blobs2Key = KeyBindingHelper.registerKeyBinding(new KeyBinding("blobs2", InputUtil.GLFW_KEY_F7, "shader api"));
		KeyBinding disableAllKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("disableAll", InputUtil.GLFW_KEY_F8, "shader api"));
		KeyBinding blobs2KeyDURATION = KeyBindingHelper.registerKeyBinding(new KeyBinding("blobs2 duration test", InputUtil.GLFW_KEY_F9, "shader api"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			for (Map.Entry<String, Integer> entry : ShaderList.postShaderDurationList.entrySet()) {
				Integer duration = entry.getValue();
				if (duration > 0){
					duration -= 1;
					entry.setValue(duration);
					LOGGER.info(duration.toString());
				}
			}

			if (client.player != null) {
				if (neurosisKey.wasPressed()) {
					ShaderRenderer.toggle("neurosis");
				}

				if (blobs2Key.wasPressed()) {
					ShaderRenderer.toggle("blobs2");
				}

				if (blobs2KeyDURATION.wasPressed()) {
					ShaderRenderer.set("blobs2",60);
				}

				if (disableAllKey.wasPressed()) {
					ShaderRenderer.disableAll();
				}
			}
		});
	}
}