package main.java.net.kallen.solaris.io;

import main.java.net.kallen.solaris.math.vector.Matrix4;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private int width, height;
    private String title;
    private boolean fullscreen;
    private boolean resized;

    private long window;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    public Input input;

    private int frames;
    private static long time;
    private boolean showFps = true;

    private GLFWWindowSizeCallback sizeCallback;
    private Matrix4 projectionMatrix;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create() {
        System.out.println("Creating Window");
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: GLFW wasn't initialized");
        }

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, 24);

        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, fullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

        if (window == 0) {
            System.err.println("ERROR: Window wasn't created");
        }

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        updateProjectionMatrix();
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallbacks();

        GLFW.glfwShowWindow(window);

        time = System.currentTimeMillis();
    }

    private void createCallbacks() {
        sizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                resized = true;
                updateProjectionMatrix();
            }
        };

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallback());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
        GLFW.glfwSetScrollCallback(window, input.getMouseScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void update() {
        if (resized) {
            GL11.glViewport(0, 0, width, height);
            resized = false;
        }
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GLFW.glfwPollEvents();
        Input.update();

        frames++;
        if (System.currentTimeMillis() > time + 1000) {
            if (showFps) GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        sizeCallback.free();
        GLFW.glfwSetWindowShouldClose(window, true);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setFullscreen(boolean fullscreen) {
        this.fullscreen = fullscreen;
        resized = true;
        if (fullscreen) {
            GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
            GLFWVidMode vm = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(),
                    0, 0, vm.width(), vm.height(), vm.refreshRate());
        } else {
            GLFW.glfwSetWindowMonitor(window, 0,
                    windowPosX[0], windowPosY[0], width, height, 0);
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public void setBgColor(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
    }

    public void updateProjectionMatrix() {
        projectionMatrix = Matrix4.projection(70f, (float) width / height, 0.1f, 1000f);
    }

    public Matrix4 getProjectionMatrix() {
        return projectionMatrix;
    }

    public void shouldShowFps(boolean showFps) {
        this.showFps = showFps;
    }

    public long getHandle() {
        return window;
    }

    public void lockCursor(boolean locked) {
        Input.lockCursor(window, locked);
    }

}
