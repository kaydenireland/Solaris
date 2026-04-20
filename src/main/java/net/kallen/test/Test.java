package main.java.net.kallen.test;

import main.java.net.kallen.solaris.io.Input;
import main.java.net.kallen.solaris.io.Key;
import main.java.net.kallen.solaris.io.Window;

import javax.swing.*;

public class Test implements Runnable {

    public Thread gameThread;
    private Window window;

    public void start() {
        gameThread = new Thread(this,"game");
        gameThread.start();
    }

    @Override
    public void run() {
        try{
            this.init();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Failed to Start Kosmos", 0);
        }

        while (!window.shouldClose() && !Input.isKeyDown(Key.ESCAPE)) {

            update();
            render();

        }
        close();
    }
    public void init() {
        window = new Window(1280, 780, "Solaris Test");
        window.create();
        window.setBgColor(0.1f, 0.3f, 0.2f, 0.5f);
    }

    public void render() {
        window.swapBuffers();
    }

    public void update() {
        window.update();
    }

    public void close() {
        window.destroy();
    }

    public static void main(String[] args) {
        new Test().start();
    }
}
