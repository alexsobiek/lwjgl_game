package com.alexsobiek.game.window;

import com.alexsobiek.game.window.graphics.SceneRenderer;
import com.alexsobiek.game.window.graphics.window.Window;
import com.alexsobiek.game.window.graphics.window.WindowCreateException;
import com.alexsobiek.game.window.graphics.window.WindowOptions;
import com.alexsobiek.game.window.graphics.window.WindowSize;
import com.alexsobiek.game.window.graphics.Renderer;
import com.alexsobiek.game.window.graphics.Scene;

public class GraphicsEngine {
    public static final int TARGET_UPS = 30;
    private long initialTime = System.currentTimeMillis();
    private final Window window;
    private final Renderer renderer;
    private final LogicCallback callback;

    private final int targetFps;
    private final int targetUps;

    private final float timeU;
    private final float timeR;
    private float deltaUpdate = 0;
    private float deltaFps = 0;

    private boolean running;

    private Scene scene;

    public GraphicsEngine(String name, WindowOptions windowOptions, LogicCallback callback) throws Exception {
        this.window = new Window(name, windowOptions, this::resize);
        this.renderer = new Renderer();
        this.callback = callback;

        this.targetFps = windowOptions.getFps();
        this.targetUps = windowOptions.getUps();

        this.timeU = 1000.0F / this.targetUps;
        this.timeR = this.targetFps > 0 ? 1000.0F / this.targetFps : 0;
    }

    public void start() throws Exception {
        System.out.println("Starting engine...");
        try {
            this.window.create();
        } catch (WindowCreateException e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.renderer.start();

        WindowSize size = this.window.getSize();
        this.scene = new Scene(size.getWidth(), size.getHeight());
        callback.onInit(this.window, this.scene);
        this.running = true;
        this.loop();
    }

    private void loop() throws Exception {
        long updateTime = initialTime;

        while (running && !window.shouldClose()) {
            this.window.poll();

            long now = System.currentTimeMillis();

            this.deltaUpdate += (now - this.initialTime) / this.timeU;
            this.deltaFps += (now - this.initialTime) / this.timeR;

            if (this.targetFps <= 0 || this.deltaFps >= 1) {
                this.callback.onInputPoll(this.window, this.scene, now - updateTime);
            }

            if (this.deltaUpdate >= 1) {
                long delta = now - updateTime;
                this.callback.onUpdate(this.window, this.scene, delta);
                updateTime = now;
                this.deltaUpdate--;
            }

            if (this.targetFps <= 0 || this.deltaFps >= 1) {
                this.renderer.render(scene);
                this.deltaFps--;
                this.window.update();
            }
            this.initialTime = now;
        }
        this.onClose();
    }

    private void resize(WindowSize size) {
        System.out.println("Resizing window to " + size.getWidth() + "x" + size.getHeight());
        scene.resize(size.getWidth(), size.getHeight());
    }

    private void onClose() {
        this.callback.onWindowClosed(this.window);
    }

    public void stop() {
        this.running = false;
    }
}