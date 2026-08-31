package main.java.net.kallen.solaris.io;

public final class Time {

    private static float deltaTime;
    private static float totalTime;

    public static void update(float deltaTime) {
        Time.deltaTime = deltaTime;
        Time.totalTime += deltaTime;
    }

    public static float deltaTime() {
        return Math.min(deltaTime, 0.5f);
    }

    public static float totalTime() {
        return totalTime;
    }
}