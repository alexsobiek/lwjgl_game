package com.alexsobiek.game.window;

import com.alexsobiek.game.window.graphics.window.Window;
import com.alexsobiek.game.window.graphics.Scene;

public interface LogicCallback {
    void onInit(Window window, Scene scene);

    void onWindowClosed(Window window);

    void onInputPoll(Window window, Scene scene, long diffTimeMillis);

    void onUpdate(Window window, Scene scene, long diffTimeMillis);
}
