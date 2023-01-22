package com.alexsobiek.game.window.shader;

import lombok.Getter;
import org.lwjgl.opengl.GL46;

import java.io.IOException;
import java.io.InputStream;

public class Shader {
    private final int type;
    private final String source;
    @Getter
    private int id = -1;

    protected Shader(String source, int type) {
        this.source = source;
        this.type = type;
    }

    public int compile() throws ShaderException {
        if (id != -1) return id;
        int shaderID = GL46.glCreateShader(type);
        if (shaderID == 0) throw new ShaderException("Failed to create shader");

        GL46.glShaderSource(shaderID, source);
        GL46.glCompileShader(shaderID);

        if (GL46.glGetShaderi(shaderID, GL46.GL_COMPILE_STATUS) == 0)
            throw new ShaderException("Failed to compile shader: " + GL46.glGetShaderInfoLog(shaderID));

        this.id = shaderID;
        return this.id;
    }

    private static String getResourceAsString(String path) throws IOException {
        try (InputStream is = ClassLoader.getSystemResourceAsStream(path)) {
            if (is == null) throw new IOException("Resource not found: " + path);
            return new String(is.readAllBytes());
        }
    }

    public static Shader fromResource(String path, int type) throws IOException {
        return new Shader(getResourceAsString(path), type);
    }
}
