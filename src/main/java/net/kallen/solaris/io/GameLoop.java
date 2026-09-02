package net.kallen.solaris.io;

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
            JOptionPane.showMessageDialog(null, e.toString(), "Failed to Start", 0);
            return;
        }

        long lastTime = System.nanoTime();

        while (!window.shouldClose() && !Input.isKeyDown(Key.ESCAPE)) {
            long currentTime = System.nanoTime();
            float dt = (currentTime - lastTime) / 1_000_000_000.0f;
            lastTime = currentTime;
            Time.update(dt);

            window.update();

            update();
            render();

        }
        close();
        window.destroy();
    }

    private void init() {
        window.create();
        window.setBgColor(0.1f, 0.3f, 0.2f, 0.5f);
        create();
    }

    public void create() { }
    public void render() { }
    public void update() { }
    public void close() { }
}
