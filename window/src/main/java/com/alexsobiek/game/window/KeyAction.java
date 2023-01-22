package com.alexsobiek.game.window;

import org.lwjgl.glfw.GLFW;

public enum KeyAction {
    PRESS,
    RELEASE,
    REPEAT;

    public static KeyAction fromGLFW(int action) {
        return switch (action) {
            case GLFW.GLFW_PRESS -> PRESS;
            case GLFW.GLFW_RELEASE -> RELEASE;
            case GLFW.GLFW_REPEAT -> REPEAT;
            default -> throw new IllegalArgumentException("Invalid GLFW key action: " + action);
        };
    }
}
