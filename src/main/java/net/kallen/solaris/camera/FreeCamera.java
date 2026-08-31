package main.java.net.kallen.solaris.camera;

import main.java.net.kallen.solaris.io.Input;
import main.java.net.kallen.solaris.io.Key;
import main.java.net.kallen.solaris.io.Time;
import main.java.net.kallen.solaris.math.vector.Vector3;

public class FreeCamera extends Camera {
    private float moveSpeed = 5;

    public FreeCamera(Vector3 position, Vector3 rotation) {
        super(position, rotation);
    }

    @Override
    public void update() {

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

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

}