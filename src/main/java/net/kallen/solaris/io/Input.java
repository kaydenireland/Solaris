package net.kallen.solaris.io;

import org.lwjgl.glfw.*;

public final class Input {

    private final static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private final static boolean[] lastKeys = new boolean[GLFW.GLFW_KEY_LAST];
    private final static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private final static boolean[] lastButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double currentMouseX, currentMouseY;
    private static double scrollX, scrollY;
    private static double lastMouseX, lastMouseY;
    private static double mouseDeltaX, mouseDeltaY;

    private final GLFWKeyCallback keyboard;
    private final GLFWCursorPosCallback mouseMove;
    private final GLFWMouseButtonCallback mouseButtons;
    private final GLFWScrollCallback mouseScroll;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key >= 0 && key < keys.length) {
                    keys[key] = (action != GLFW.GLFW_RELEASE);
                }
            }
        };

        mouseMove = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                currentMouseX = xpos;
                currentMouseY = ypos;
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouseScroll = new GLFWScrollCallback() {
            public void invoke(long window, double offsetx, double offsety) {
                scrollX += offsetx;
                scrollY += offsety;
            }
        };
    }

    public static void update() {
        System.arraycopy(keys, 0, lastKeys, 0, keys.length);
        System.arraycopy(buttons, 0, lastButtons, 0, buttons.length);

        scrollX = 0;
        scrollY = 0;

        mouseDeltaX = currentMouseX - lastMouseX;
        mouseDeltaY = currentMouseY - lastMouseY;
        lastMouseX = currentMouseX;
        lastMouseY = currentMouseY;
    }

    // Keys

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isAnyKeyDown() {
        for (boolean key : keys) {
            if (key) return true;
        }
        return false;
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] && !lastKeys[key];
    }

    public static boolean isKeyReleased(int key) {
        return !keys[key] && lastKeys[key];
    }

    // Buttons

    public static boolean isButtonDown(int button) {
        return buttons[button];
    }

    public static boolean isAnyButtonDown() {
        for (boolean button : buttons) {
            if (button) return true;
        }
        return false;
    }

    public static boolean isButtonPressed(int button) {
        return buttons[button] && !lastButtons[button];
    }

    public static boolean isButtonReleased(int button) {
        return !buttons[button] && lastButtons[button];
    }

    // Mouse

    public static double getCurrentMouseX() {
        return currentMouseX;
    }

    public static double getCurrentMouseY() {
        return currentMouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public static double getMouseDeltaX() {
        return mouseDeltaX;
    }

    public static double getMouseDeltaY() {
        return mouseDeltaY;
    }

    public static void lockCursor(long window, boolean locked) {
        GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, locked ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }

    // Callbacks

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }

    public GLFWScrollCallback getMouseScrollCallback() {
        return mouseScroll;
    }

    public void destroy() {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

}