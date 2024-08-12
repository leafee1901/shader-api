package net.leafee.shader_api;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.leafee.shader_api.shader.ShaderList;
import net.leafee.shader_api.shader.ShaderRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ShaderAPI implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("shader_api");

	@Override
	public void onInitializeClient() {

		ShaderList.registerPostShader("blobs2");
		ShaderList.registerPostShader("neurosis");

		KeyBinding neurosisKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("neurosis", InputUtil.GLFW_KEY_F6, "shader api"));
		KeyBinding blobs2Key = KeyBindingHelper.registerKeyBinding(new KeyBinding("blobs2", InputUtil.GLFW_KEY_F7, "shader api"));
		KeyBinding disableAllKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("disableAll", InputUtil.GLFW_KEY_F8, "shader api"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (neurosisKey.wasPressed()) {
					if (!ShaderRenderer.hasRegistered) {
						ShaderList.finalizeRegisterPostShader();
					}

					if (ShaderRenderer.isEnabled("neurosis")) {
						ShaderRenderer.set("neurosis", false);
					} else {
						ShaderRenderer.set("neurosis", true);
					}
				}

				if (blobs2Key.wasPressed()) {
					if (!ShaderRenderer.hasRegistered) {
						ShaderList.finalizeRegisterPostShader();
					}

					if (ShaderRenderer.isEnabled("blobs2")) {
						ShaderRenderer.set("blobs2", false);
						LOGGER.info("disable");
					} else {
						ShaderRenderer.set("blobs2", true);
						LOGGER.info("enable");
					}
				}

				if (disableAllKey.wasPressed()) {
					ShaderRenderer.disableAll();
				}
			}
		});
	}

}