package net.kallen.solaris.graphics.camera;

import net.kallen.solaris.io.Input;
import net.kallen.solaris.io.Key;
import net.kallen.solaris.io.Time;
import net.kallen.solaris.math.vector.Vector3;

public class FreeCamera extends Camera {

    private float moveSpeed = 5;

    // Mouse Settings
    private float maxPitch = 89f;
    private boolean limitPitch = true;
    private float mouseSensitivity = 0.1f;
    private boolean invertY = false;
    private boolean mouseEnabled = true;

    public FreeCamera() {
        super();
    }

    public FreeCamera(Vector3 position, Vector3 rotation) {
        super(position, rotation);
    }

    @Override
    public void update() {

        if (mouseEnabled) {
            handleMouse();
        }

        float speed = moveSpeed * Time.deltaTime();
        float yaw = (float) Math.toRadians(rotation.y);

        float x = (float) Math.sin(yaw) * speed;
        float z = (float) Math.cos(yaw) * speed;


        if (Input.isKeyDown(Key.W)) position = position.add(new Vector3(-x, 0, -z));
        if (Input.isKeyDown(Key.A)) position = position.add(new Vector3(-z, 0, x));
        if (Input.isKeyDown(Key.S)) position = position.add(new Vector3(x, 0, z));
        if (Input.isKeyDown(Key.D)) position = position.add(new Vector3(z, 0, -x));

        if (Input.isKeyDown(Key.SPACE)) position = position.add(new Vector3(0, speed, 0));
        if (Input.isKeyDown(Key.LEFT_SHIFT)) position = position.add(new Vector3(0, -speed, 0));
    }

    private void handleMouse() {

        float dx = (float) Input.getMouseDeltaX();
        float dy = (float) Input.getMouseDeltaY();

        rotation.y += dx * mouseSensitivity;
        rotation.x += (invertY ? -dy : dy) * mouseSensitivity;

        rotation.y = rotation.y % 360f;

        if (rotation.x > maxPitch && limitPitch) rotation.x = maxPitch;
        if (rotation.x < -maxPitch && limitPitch) rotation.x = -maxPitch;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setMouseSensitivity(float mouseSensitivity) {
        this.mouseSensitivity = mouseSensitivity;
    }

    public void setInvertY(boolean invertY) {
        this.invertY = invertY;
    }

    public void setMouseEnabled(boolean mouseEnabled) {
        this.mouseEnabled = mouseEnabled;
    }

    public void setMaxPitch(float maxPitch) {
        this.maxPitch = maxPitch;
    }

    public void setLimitPitch(boolean limitPitch) {
        this.limitPitch = limitPitch;
    }


}