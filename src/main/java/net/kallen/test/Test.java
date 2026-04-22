package main.java.net.kallen.test;

import main.java.net.kallen.solaris.graphics.*;
import main.java.net.kallen.solaris.io.GameLoop;
import main.java.net.kallen.solaris.io.Window;
import main.java.net.kallen.solaris.math.Vector2;
import main.java.net.kallen.solaris.math.Vector3;
import main.java.net.kallen.solaris.util.ResourceLocation;

public class Test {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        Shader shader = new Shader(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".vert"),
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".frag")
        );
        Renderer renderer = new Renderer(window, shader);
        Texture texture = new Texture(ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.TEXTURES, "default").toImagePath());

        Mesh mesh = new Mesh(Positions.TRIANGLE, Faces.TRIANGLE, texture);
        // Mesh mesh = Shapes.TRIANGLE;
        new GameLoop(window){

            @Override
            public void create() {
                shader.create();
                mesh.create();
            }

            @Override
            public void update() {

            }

            @Override
            public void render() {
                renderer.beginFrame();
                renderer.renderMesh(mesh);
                renderer.endFrame();
            }

            @Override
            public void close() {
                mesh.destroy();
                shader.destroy();
            }

        }.start();
    }

    private void update() {

    }

    private void render() {

    }
}
