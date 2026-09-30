package net.kallen.solaris.demo;

import net.kallen.solaris.graphics.camera.Camera;
import net.kallen.solaris.graphics.camera.FreeCamera;
import net.kallen.solaris.graphics.mesh.Mesh;
import net.kallen.solaris.graphics.mesh.Shapes;
import net.kallen.solaris.graphics.render.MasterRenderer;
import net.kallen.solaris.graphics.shader.StaticShader;
import net.kallen.solaris.io.GameLoop;
import net.kallen.solaris.io.Window;
import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.util.file.ResourceLocation;

public class CubeDemo {

    public static void main(String[] args) {
        Window window = new Window(1280, 780, "Solaris Test");
        Camera camera = new FreeCamera(new Vector3(0, 0, 0), new Vector3(0,0,0));

        MasterRenderer renderer = new MasterRenderer(window, camera);

        Mesh mesh = Shapes.CUBE;

        new GameLoop(window){

            @Override
            public void create() {
                renderer.create();
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
                renderer.getEntityRenderer().renderMesh(mesh, new Vector3(0, 0, -2));
                renderer.endFrame();
            }

            @Override
            public void close() {
                mesh.destroy();
                renderer.destroy();
            }

        }.start();
    }
}


