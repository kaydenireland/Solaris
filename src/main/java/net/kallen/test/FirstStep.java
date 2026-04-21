package main.java.net.kallen.test;

import main.java.net.kallen.solaris.io.GameLoop;
import main.java.net.kallen.solaris.io.Window;

public class FirstStep {

    public static void main(String[] args) {
        Window win = new Window(1280, 780, "Solaris Test");
        new GameLoop(win){
            @Override
            public void update() {

            }

            @Override
            public void render() {

            }

            @Override
            public void close() {

            }

        }.start();
    }

    private void update() {

    }

    private void render() {

    }
}
