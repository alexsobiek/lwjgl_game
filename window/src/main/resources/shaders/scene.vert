#version 330

layout(location=0) in vec3 inPosition;      // Starting at location 0, we have a vec3
layout(location=1) in vec3 color;           // Starting at location 1, we have a vec3

out vec3 outColor;                          // We will pass the color to the fragment shader

uniform mat4 projectionMatrix;

void main() {
    gl_Position = projectionMatrix * vec4(inPosition, 1.0);     // Multiply the position by the matrix to get the final position
    outColor = color;                                           // Pass the color to the fragment shader
}