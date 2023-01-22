package com.alexsobiek.game.window;

import org.lwjgl.system.MemoryStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class MemUtil {
    public static void push(Consumer<MemoryStack> stack) {
        try (MemoryStack stack1 = MemoryStack.stackPush()) {
            stack.accept(stack1);
        }
    }

    public static <T> T push(Function<MemoryStack, T> fn) {
        try (MemoryStack stack1 = MemoryStack.stackPush()) {
            return fn.apply(stack1);
        }
    }
}
