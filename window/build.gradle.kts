plugins {
    id("com.alexsobiek.game.java-library-conventions")
    id("org.lwjgl.plugin") version "0.0.30"
}

val lwjglVersion = "3.3.1"
val lwjglNatives = "natives-linux"

dependencies {
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-opengl")

    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-windows")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = "natives-macos")

    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-macos")
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = "natives-windows")

    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-linux")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-macos")
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = "natives-windows")
}