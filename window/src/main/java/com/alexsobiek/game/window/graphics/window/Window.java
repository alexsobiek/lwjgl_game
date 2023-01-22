package com.alexsobiek.game.window.graphics.window;

import com.alexsobiek.game.window.KeyAction;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Window implements AutoCloseable {
    private final List<AutoCloseable> closeables = new ArrayList<>();
    private final String name;
    private final WindowOptions options;
    private final Consumer<WindowSize> resizeFn;
    private WindowSize size;
    private long handle;
    private boolean created;

    public Window(String name, WindowOptions options, Consumer<WindowSize> resizeFn) {
        this.name = name;
        this.options = options;
        this.resizeFn = resizeFn;
    }

    public void create() throws WindowCreateException {
        if (this.created) throw new WindowCreateException("Window already created!");
        if (!GLFW.glfwInit()) throw new WindowCreateException("Failed to initialize GLFW!");

        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);        // Hide window for now
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);       // Set Resizable to true

        // Setup version
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
        if (options.isCompatibleProfile()) {
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
            GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        }


        if (options.getWidth() > 0 && options.getHeight() > 0) {        // Windowed mode
            this.size = options.getWindowSize().clone();
        } else {                                                        // Full screen
            GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            if (vidMode == null) throw new WindowCreateException("Failed to get video mode!");
            this.size = new WindowSize(vidMode.width(), vidMode.height());
        }

        this.handle = GLFW.glfwCreateWindow(size.getWidth(), size.getHeight(), name, MemoryUtil.NULL, MemoryUtil.NULL);
        if (this.handle == MemoryUtil.NULL) throw new WindowCreateException("Failed to create window!");

        this.closeables.add(GLFW.glfwSetFramebufferSizeCallback(this.handle, this::resize));
        this.closeables.add(GLFW.glfwSetErrorCallback(this::error));
        this.closeables.add(GLFW.glfwSetKeyCallback(this.handle, this::key));

        GLFW.glfwMakeContextCurrent(this.handle);

        GLFW.glfwSwapInterval(options.getFps() > 0 ? 1 : 0);            // Enable v-sync if FPS is not set

        GLFW.glfwShowWindow(this.handle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];

        GLFW.glfwGetFramebufferSize(handle, arrWidth, arrHeight);

        this.size.setWidth(arrWidth[0]);
        this.size.setHeight(arrHeight[0]);
        this.created = true;
    }

    private void resize(long handle, int height, int width) {
        this.size.setWidth(width);
        this.size.setHeight(height);
        try {
            this.resizeFn.accept(this.size);
        } catch (Exception e) {
            e.printStackTrace();                                        // TODO: Log error
        }
    }

    private void error(int code, long ptr) {
        String msg = MemoryUtil.memUTF8(ptr);
        System.err.printf("GLFW Error: %s (%d)%n", msg, code);          // TODO: use proper logger
    }

    private void key(long handle, int key, int scancode, int action, int mods) {
        if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_RELEASE) {
            GLFW.glfwSetWindowShouldClose(this.handle, true);
        }
    }

    public void poll() {
        GLFW.glfwPollEvents();
    }

    public KeyAction getKeyAction(int key) {
        return KeyAction.fromGLFW(GLFW.glfwGetKey(this.handle, key));
    }

    public void update() {
        GLFW.glfwSwapBuffers(this.handle);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.handle);
    }

    @Override
    public void close() throws Exception {
        for (AutoCloseable closeable : this.closeables) closeable.close();
        if (created) {
            Callbacks.glfwFreeCallbacks(handle);
            GLFW.glfwDestroyWindow(handle);
            GLFW.glfwTerminate();
        }
    }
}
