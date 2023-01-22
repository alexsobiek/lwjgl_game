#version 330

in vec3 outColor;                               // Input color
out vec4 fragColor;                             // Output color

void main() {
    fragColor = vec4(outColor, 1.0);            // Set output color
}