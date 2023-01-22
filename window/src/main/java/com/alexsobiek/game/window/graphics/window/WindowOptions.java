package com.alexsobiek.game.window.graphics.window;

import com.alexsobiek.game.window.GraphicsEngine;
import lombok.Data;

@Data
public class WindowOptions {
    private boolean compatibleProfile;                   // Whether to use a compatible profile
    private int fps;                                     // Target FPS
    private int ups = GraphicsEngine.TARGET_UPS;         // Target
    private WindowSize windowSize;                       // Window width and height

    public int getWidth() {
        return windowSize.getWidth();
    }

    public int getHeight() {
        return windowSize.getHeight();
    }
}