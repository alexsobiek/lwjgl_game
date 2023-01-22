package com.alexsobiek.game.window.graphics;

import com.alexsobiek.game.window.MemUtil;
import lombok.Getter;
import org.lwjgl.opengl.GL46;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Mesh implements AutoCloseable {
    private final float[] positions;
    private final int numVertices;
    private int vaoId;
    private List<Integer> vboIdList;


    public Mesh(float[] positions, int numVertices) {
        this.numVertices = numVertices;
        this.positions = positions;


        MemUtil.push(stack -> {

            vboIdList = new ArrayList<>();

            vaoId = GL46.glGenVertexArrays();
            GL46.glBindVertexArray(vaoId);

            int vboId = GL46.glGenBuffers();
            vboIdList.add(vboId);

            FloatBuffer posBuffer = stack.mallocFloat(positions.length);
            posBuffer.put(0, positions);

            GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER, vboId);
            GL46.glBufferData(GL46.GL_ARRAY_BUFFER, posBuffer, GL46.GL_STATIC_DRAW);

            GL46.glEnableVertexAttribArray(0);
            GL46.glVertexAttribPointer(0, 3, GL46.GL_FLOAT, false, 0, 0);

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
