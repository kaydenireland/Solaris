package test.java.net.kallen.solaris.demo;

import main.java.net.kallen.solaris.camera.Camera;
import main.java.net.kallen.solaris.camera.FreeCamera;
import main.java.net.kallen.solaris.graphics.*;
import main.java.net.kallen.solaris.io.GameLoop;
import main.java.net.kallen.solaris.io.Key;
import main.java.net.kallen.solaris.io.Window;
import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.util.file.ResourceLocation;

public class CubeDemo {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        Shader shader = new Shader(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".vert"),
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".frag")
        );
        Camera camera = new FreeCamera(new Vector3(0, 0, 0), new Vector3(0,0,0));

        Renderer renderer = new Renderer(window, shader, camera);

        Mesh mesh = Shapes.torus(8, 5);

        new GameLoop(window){

            @Override
            public void create() {
                shader.create();
                mesh.create();
                window.lockCursor(true);
            }

            @Override
            public void update() {
                camera.update();
            }

            @Override
            public void render() {
                renderer.beginFrame();
                renderer.renderMesh(mesh, new Vector3(0, 0, -2));
                renderer.endFrame();
            }

            @Override
            public void close() {
                mesh.destroy();
                shader.destroy();
            }

        }.start();
    }
}


