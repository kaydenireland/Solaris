package net.kallen.solaris.demo;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.camera.FreeCamera;
import net.kallen.solaris.graphics.scene.Light;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.render.Renderer;
import net.kallen.solaris.graphics.scene.Entity;
import net.kallen.solaris.graphics.scene.Scene;
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

        Light light = new Light(
                new Vector3(0, 0, -5),
                new Vector3(1, 1, 1)
        );

        Mesh mesh = ModelLoader.loadModel(
                ResourceLocation.fromNamespaceAndDirectory("solaris", ResourceLocation.MODELS, "dragon").toSystemFilePath(".obj")
        );
        Entity dragon = new Entity(
                new Vector3(0, 0, -10),
                new Vector3(0, 0, 0),
                new Vector3(1, 1, 1),
                mesh
        );
        Scene scene = new Scene(light);
        scene.addEntity(dragon);
        scene.setAmbientLightStrength(0.9f);


        new GameLoop(window){

            @Override
            public void create() {
                shader.create();
                scene.create();
                window.lockCursor(true);
            }

            @Override
            public void update() {
                camera.update();
            }

            @Override
            public void render() {
                renderer.beginFrame();

                scene.render(renderer);

                renderer.endFrame();
            }

            @Override
            public void close() {
                scene.destroy();
                shader.destroy();
            }

        }.start();
    }
}


