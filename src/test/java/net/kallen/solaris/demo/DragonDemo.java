package net.kallen.solaris.demo;

import net.kallen.solaris.camera.Camera;
import net.kallen.solaris.camera.FreeCamera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.Renderer;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.GameLoop;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.util.file.ModelLoader;
import net.kallen.solaris.util.file.ResourceLocation;

public class DragonDemo {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        StaticShader shader = new StaticShader(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".vert"),
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.SHADERS, "default").toFilePath(".frag")
        );
        Camera camera = new FreeCamera(new Vector3(0, 4, 0), new Vector3(0,0,0));

        Renderer renderer = new Renderer(window, shader, camera);

        Mesh mesh = ModelLoader.loadModel(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.MODELS, "dragon").toSystemFilePath(".obj")
        );

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
                renderer.renderMesh(mesh, new Vector3(0, 0, -10));
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


