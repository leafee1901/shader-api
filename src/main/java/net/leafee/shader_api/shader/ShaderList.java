package net.leafee.shader_api.shader;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public enum ShaderList {
    DISABLED("disabled");


    private final String id;

    ShaderList(String id) {
        this.id = id;
    }

    public Identifier getPath() {
        return new Identifier("shaders/post/" + this.id + ".json");
    }

}
