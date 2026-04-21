package main.java.net.kallen.solaris.io;

import javax.swing.*;

public class GameLoop implements Runnable {
    public Thread gameThread;
    private final Window window;

    public GameLoop(Window window) {
        this.window = window;
    }

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
            window.swapBuffers();
            render();
            window.update();

        }
        close();
        window.destroy();
    }

    private void init() {
        window.create();
        window.setBgColor(0.1f, 0.3f, 0.2f, 0.5f);
    }

    public void render() { }
    public void update() { }
    public void close() { }
}
