package test.java.net.kallen.solaris.demo;

import main.java.net.kallen.solaris.io.GameLoop;
import main.java.net.kallen.solaris.io.Window;

/**
 *  Demonstrates the first step of using the Solaris Engine.
 * <p>
 *  Creates the GameLoop, which controls the update, render, and close functions.
 */
public class FirstStepDemo {

    public static void main(String[] args) {
        Window win = new Window(1280, 780, "Solaris Test");
        new GameLoop(win){

            @Override
            public void create() {

            }

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
}
