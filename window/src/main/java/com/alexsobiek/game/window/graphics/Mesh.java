package com.alexsobiek.game.window.graphics;

import com.alexsobiek.game.window.MemUtil;
import lombok.Getter;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Mesh implements AutoCloseable {
    private final List<Integer> vboIdList = new ArrayList<>();
    private final float[] positions;
    private final int numVertices;
    private int vaoId;

    public Mesh(float[] positions, float[] colors, int[] indices) {
        this.numVertices = indices.length;
        this.positions = positions;

        MemUtil.push(stack -> {
            vaoId = GL46.glGenVertexArrays();
            GL46.glBindVertexArray(vaoId);

            // Positions VBO
            int vboId = GL46.glGenBuffers();
            vboIdList.add(vboId);

            FloatBuffer posBuffer = stack.mallocFloat(positions.length);
            posBuffer.put(0, positions);

            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboId);
            GL46.glBufferData(GL46.GL_ARRAY_BUFFER, posBuffer, GL46.GL_STATIC_DRAW);

            GL46.glEnableVertexAttribArray(0);
            GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 0, 0);


            // Colors VBO
            vboId = GL46.glGenBuffers();
            vboIdList.add(vboId);
            FloatBuffer colorsBuffer = stack.mallocFloat(colors.length);
            colorsBuffer.put(0, colors);
            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboId);
            GL46.glBufferData(GL46.GL_ARRAY_BUFFER, colorsBuffer, GL46.GL_STATIC_DRAW);
            GL46.glEnableVertexAttribArray(1);
            GL46.glVertexAttribPointer(1, 3, GL46.GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = GL46.glGenBuffers();
            vboIdList.add(vboId);
            IntBuffer indicesBuffer = stack.mallocInt(indices.length);
            indicesBuffer.put(0, indices);
            GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER, vboId);
            GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL46.GL_STATIC_DRAW);

            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, 0);
            GL46.glBindVertexArray(0);
        });
    }

    @Override
    public void close() {
        vboIdList.forEach(GL46::glDeleteBuffers);
        GL46.glDeleteVertexArrays(vaoId);
    }
}
