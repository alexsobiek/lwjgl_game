#version 330

layout(location=0) in vec3 inPosition;      // Starting at location 0, we have a vec3

void main() {
    gl_Position = vec4(inPosition, 1.0);    // Convert to vec4, setting extra dimension (w) to 1.0
}