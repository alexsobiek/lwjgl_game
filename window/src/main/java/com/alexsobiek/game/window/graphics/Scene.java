package com.alexsobiek.game.window.graphics;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Scene implements AutoCloseable {
    private final Map<String, Mesh> meshes = new HashMap<>();
    private final Projection projection;

    public Scene(int width, int height) {
        this.projection = new Projection(width, height);
    }

    public void addMesh(String name, Mesh mesh) {
        meshes.put(name, mesh);
    }

    public void resize(int width, int height) {
        projection.resize(width, height);
    }

    @Override
    public void close() {
        meshes.values().forEach(Mesh::close);
    }
}
