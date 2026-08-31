package main.java.net.kallen.solaris.util;

import org.lwjgl.opengl.GL11;

public final class GLUtils {

    private static void checkGlError() {
        int e = GL11.glGetError();
        if (e != 0) throw new IllegalStateException();
    }
}
