package com.alexsobiek.game.window.graphics;

import lombok.Getter;
import org.joml.Matrix4f;

@Getter
public class Projection {
    private static final float FOV = (float) Math.toRadians(60.0F);
    private static final float Z_NEAR = 0.01F;
    private static final float Z_FAR = 1000.0F;

    private final Matrix4f projectionMatrix;

    public Projection(int width, int height) {
        this.projectionMatrix = new Matrix4f();
        this.resize(width, height);
    }

    public void resize(int width, int height) {
        projectionMatrix.perspective(FOV, (float) width / (float) height, Z_NEAR, Z_FAR);
    }
}
