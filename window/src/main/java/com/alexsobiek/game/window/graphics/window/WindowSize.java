package com.alexsobiek.game.window.graphics.window;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WindowSize implements Cloneable {
    private int width, height;

    @Override
    public WindowSize clone() {
        try {
            return (WindowSize) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
