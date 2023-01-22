package com.alexsobiek.game.window.shader;

import lombok.Getter;
import org.lwjgl.opengl.GL46;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShaderProgram implements AutoCloseable {
    private final List<Shader> shaders;
    private final int programID;

    public ShaderProgram(List<Shader> shaders) throws ShaderException {
        programID = GL46.glCreateProgram();
        if (programID == 0) throw new ShaderException("Failed to create shader program");

        this.shaders = shaders;
        for (Shader s : this.shaders) GL46.glAttachShader(programID, s.compile());

        GL46.glLinkProgram(programID);

        if (GL46.glGetProgrami(programID, GL46.GL_LINK_STATUS) == 0)
            throw new ShaderException("Failed to link shader program: " + GL46.glGetProgramInfoLog(programID));

        this.shaders.forEach(s -> {                     // Free up shaders
            GL46.glDetachShader(programID, s.getId());
            GL46.glDeleteShader(s.getId());
        });
    }

    public void bind() {
        GL46.glUseProgram(programID);
    }

    public void unbind() {
        GL46.glUseProgram(0);
    }

    public void validate() throws ShaderException {
        GL46.glValidateProgram(programID);
        if (GL46.glGetProgrami(programID, GL46.GL_VALIDATE_STATUS) == 0)
            throw new ShaderException("Failed to validate shader program: " + GL46.glGetProgramInfoLog(programID));
    }

    @Override
    public void close() {
        unbind();
        if (programID != 0) GL46.glDeleteProgram(programID);
    }
}
