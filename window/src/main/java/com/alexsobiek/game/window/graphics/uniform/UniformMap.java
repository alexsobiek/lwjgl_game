package com.alexsobiek.game.window.graphics.uniform;

import com.alexsobiek.game.window.MemUtil;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL46;

import java.util.HashMap;
import java.util.Map;

public class UniformMap {
    private final Map<String, Integer> uniforms = new HashMap<>();
    private final int programID;

    public UniformMap(int programID) {
        this.programID = programID;
    }

    public Integer create(String name) throws UniformException {
        int uniformLocation = GL46.glGetUniformLocation(programID, name);
        if (uniformLocation < 0)
            throw new UniformException("Could not find uniform: " + name + " in program: " + programID);
        return uniforms.put(name, uniformLocation);
    }

    public void set(Integer location, Matrix4f value) {
        MemUtil.push(stack -> {
            GL46.glUniformMatrix4fv(location, false, value.get(stack.mallocFloat(16)));
        });
    }

    public void set(String name, Matrix4f matrix) throws UniformException {
        Integer loc = uniforms.get(name);
        if (loc == null) throw new UniformException("Could not find uniform: " + name + " in program: " + programID);
        set(loc, matrix);
    }

    public void add(String name, Matrix4f value) throws UniformException {
        set(create(name), value);
    }
}
