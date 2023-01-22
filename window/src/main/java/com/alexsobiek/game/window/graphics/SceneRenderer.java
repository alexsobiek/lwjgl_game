package com.alexsobiek.game.window.graphics;

import com.alexsobiek.game.window.graphics.uniform.UniformException;
import com.alexsobiek.game.window.graphics.uniform.UniformMap;
import com.alexsobiek.game.window.shader.Shader;
import com.alexsobiek.game.window.shader.ShaderException;
import com.alexsobiek.game.window.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL46;

import java.io.IOException;
import java.util.List;

public class SceneRenderer implements AutoCloseable {
    private final ShaderProgram shaderProgram;
    private final UniformMap uniformMap;

    public SceneRenderer() throws ShaderException, IOException, UniformException {
        List<Shader> shaders = List.of(
                Shader.fromResource("shaders/scene.vert", GL20.GL_VERTEX_SHADER),
                Shader.fromResource("shaders/scene.frag", GL20.GL_FRAGMENT_SHADER)
        );

        shaderProgram = new ShaderProgram(shaders);
        uniformMap = new UniformMap(shaderProgram.getProgramID());
        uniformMap.create("projectionMatrix");
    }

    public void render(Scene scene) throws UniformException {
        shaderProgram.bind();

        uniformMap.set("projectionMatrix", scene.getProjection().getProjectionMatrix());

        scene.getMeshes().values().forEach(mesh -> {
            GL46.glBindVertexArray(mesh.getVaoId());
            GL46.glDrawElements(GL46.GL_TRIANGLES, mesh.getNumVertices(), GL46.GL_UNSIGNED_INT, 0);
        });

        shaderProgram.unbind();
    }

    @Override
    public void close() throws Exception {
        shaderProgram.close();
    }
}
