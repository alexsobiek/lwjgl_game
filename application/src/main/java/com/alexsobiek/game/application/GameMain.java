package com.alexsobiek.game.application;

import com.alexsobiek.game.window.GraphicsEngine;
import com.alexsobiek.game.window.LogicCallback;
import com.alexsobiek.game.window.graphics.Mesh;
import com.alexsobiek.game.window.graphics.Scene;
import com.alexsobiek.game.window.graphics.window.Window;
import com.alexsobiek.game.window.graphics.window.WindowOptions;
import com.alexsobiek.game.window.graphics.window.WindowSize;

public class GameMain {
    public static void main(String[] args) throws Exception {
        WindowOptions options = new WindowOptions();
        options.setWindowSize(new WindowSize(300, 300));
        new GraphicsEngine("3D Game", options, new LogicCallback() {
            @Override
            public void onInit(Window window, Scene scene) {
                System.out.println("Initialized!");

                float[] positions = new float[] {   // square
                        -0.5f,  0.5f, 0.0f,
                        -0.5f, -0.5f, 0.0f,
                        0.5f, -0.5f, 0.0f,
                        0.5f,  0.5f, 0.0f,
                };

                float[] colors = new float[] {
                        0.5f, 0.0f, 0.0f,
                        0.0f, 0.5f, 0.0f,
                        0.0f, 0.0f, 0.5f,
                        0.0f, 0.5f, 0.5f,
                };

                int[] indices = new int[] {
                        0, 1, 3,
                        3, 1, 2
                };

                scene.addMesh("quad", new Mesh(positions, colors, indices));
            }

            @Override
            public void onWindowClosed(Window window) {
                System.out.println("Window closed!");
            }

            @Override
            public void onInputPoll(Window window, Scene scene, long diffTimeMillis) {
                // System.out.println(window.getKeyAction(79));
            }

            @Override
            public void onUpdate(Window window, Scene scene, long diffTimeMillis) {
                // System.out.println("Update!");
            }
        }).start();
    }
}
