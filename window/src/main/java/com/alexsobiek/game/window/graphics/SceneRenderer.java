package com.alexsobiek.game.window.graphics;

import com.alexsobiek.game.window.shader.Shader;
import com.alexsobiek.game.window.shader.ShaderException;
import com.alexsobiek.game.window.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;

import java.io.IOException;
import java.util.List;

public class SceneRenderer implements AutoCloseable {
    private final ShaderProgram shaderProgram;

    public SceneRenderer() throws ShaderException, IOException {
        List<Shader> shaders = List.of(
                Shader.fromResource("shaders/scene.vert", GL20.GL_VERTEX_SHADER),
                Shader.fromResource("shaders/scene.frag", GL20.GL_FRAGMENT_SHADER)
        );

        shaderProgram = new ShaderProgram(shaders);
    }

    public void render(Scene scene) {
        shaderProgram.bind();

        scene.getMeshes().values().forEach(mesh -> {
            GL46.glBindVertexArray(mesh.getVaoId());
            GL46.glDrawArrays(GL46.GL_TRIANGLES, 0, mesh.getNumVertices());
        });

        shaderProgram.unbind();
    }

    @Override
    public void close() throws Exception {
        shaderProgram.close();
    }
}
