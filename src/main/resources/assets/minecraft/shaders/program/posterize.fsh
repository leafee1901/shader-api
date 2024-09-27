#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 LeftTop;
uniform vec2 RightDown;
uniform vec4 Luminance;

out vec4 fragColor;


vec3 posterize(vec3 rgb) {
    vec3 steps = vec3(12);
    return vec3(round(rgb * steps) / steps);
}


void main(){

    vec4 initialColor = texture(DiffuseSampler, texCoord);

    fragColor = vec4(posterize(initialColor.rgb), initialColor.a);


}

