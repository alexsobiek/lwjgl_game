package com.alexsobiek.game.window.graphics;

import com.alexsobiek.game.window.graphics.window.Window;
import com.alexsobiek.game.window.shader.ShaderException;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

import java.io.IOException;

public class Renderer {
    private SceneRenderer sceneRenderer;

    public void start() throws Exception {
        GL.createCapabilities();
        this.sceneRenderer = new SceneRenderer();
    }

    public void render(Scene scene) throws Exception {
        sceneRenderer.render(scene);
    }
}
