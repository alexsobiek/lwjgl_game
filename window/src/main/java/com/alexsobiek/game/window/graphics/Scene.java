package com.alexsobiek.game.window.graphics;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Scene implements AutoCloseable {
    private Map<String, Mesh> meshes = new HashMap<>();

    public void addMesh(String name, Mesh mesh) {
        meshes.put(name, mesh);
    }

    @Override
    public void close() {
        meshes.values().forEach(Mesh::close);
    }
}
