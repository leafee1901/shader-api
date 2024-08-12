package net.leafee.shader_api;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.PostEffectProcessor;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ShaderAPI implements ModInitializer {
	public static MinecraftClient client = MinecraftClient.getInstance();
	public static PostEffectProcessor shader;
	public static boolean postShaderEnabled = false;
    public static final Logger LOGGER = LoggerFactory.getLogger("shader_api");

	//blobs2 = id:2, name:blobs2

	@Override
	public void onInitialize() {

		KeyBinding toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("toggle", InputUtil.GLFW_KEY_F7, "button"));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null) {
				if (toggleKey.wasPressed()) {
					postShaderEnabled ^= true;
					load();
					LOGGER.info("toggled post shader");
				}
			}
		});
	}


	private static PostEffectProcessor getShader() {
		try {
			return new PostEffectProcessor(client.getTextureManager(), client.getResourceManager(), client.getFramebuffer(), new Identifier("shaders/post/blobs2.json"));
		} catch (IOException e) {
			return null;

		}
	}

	public static void load() {
		if(shader != null)
			shader.close();
		shader = getShader();
		if(shader != null) {
			shader.setupDimensions(client.getWindow().getFramebufferWidth(), client.getWindow().getFramebufferHeight());
			postShaderEnabled = true;
			return;
		}
		postShaderEnabled = false;
	}
}