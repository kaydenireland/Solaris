package net.kallen.solaris.math.vector;

import java.util.Arrays;

public class Matrix4 {

    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public static Matrix4 identity() {

        Matrix4 result = new Matrix4();

        result.set(0, 0, 1f);
        result.set(1, 1, 1f);
        result.set(2, 2, 1f);
        result.set(3, 3, 1f);

        return result;
    }

    public static Matrix4 translate(Vector3 translate) {
        Matrix4 result = Matrix4.identity();

        result.set(3, 0, translate.x);
        result.set(3, 1, translate.y);
        result.set(3, 2, translate.z);

        return result;
    }

    public static Matrix4 rotate(float angle, Vector3 axis) {
        Matrix4 result = Matrix4.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));
        float C = 1 - cos;

        result.set(0, 0, cos + axis.x * axis.x * C);
        result.set(1, 0, axis.x * axis.y * C - axis.z * sin);
        result.set(2, 0, axis.x * axis.z * C + axis.y * sin);
        result.set(0, 1, axis.y * axis.x * C + axis.z * sin);
        result.set(1, 1, cos + axis.y * axis.y * C);
        result.set(2, 1, axis.y * axis.z * C - axis.x * sin);
        result.set(0, 2, axis.z * axis.x * C - axis.y * sin);
        result.set(1, 2, axis.z * axis.y * C + axis.x * sin);
        result.set(2, 2, cos + axis.z * axis.z * C);

        return result;
    }

    public static Matrix4 scale(Vector3 scalar) {
        Matrix4 result = Matrix4.identity();

        result.set(0, 0, scalar.x);
        result.set(1, 1, scalar.y);
        result.set(2, 2, scalar.z);

        return result;
    }

    public static Matrix4 transform(Vector3 position, Vector3 rotation, Vector3 scale) {
        Matrix4 result;

        Matrix4 translationMatrix = Matrix4.translate(position);
        Matrix4 rotXMatrix = Matrix4.rotate(rotation.x, new Vector3(1, 0, 0));
        Matrix4 rotYMatrix = Matrix4.rotate(rotation.y, new Vector3(0, 1, 0));
        Matrix4 rotZMatrix = Matrix4.rotate(rotation.z, new Vector3(0, 0, 1));
        Matrix4 scaleMatrix = Matrix4.scale(scale);

        Matrix4 rotationMatrix = Matrix4.multiply(rotYMatrix, Matrix4.multiply(rotXMatrix, rotZMatrix));

        result = Matrix4.multiply(translationMatrix, Matrix4.multiply(rotationMatrix, scaleMatrix));

        return result;
    }

    public static Matrix4 multiply(Matrix4 matrix, Matrix4 other) {
        Matrix4 result = Matrix4.identity();

        for (int col = 0; col < SIZE; col++) {
            for (int row = 0; row < SIZE; row++) {
                float sum = 0;
                for (int k = 0; k < SIZE; k++) {
                    sum += matrix.get(k, row) * other.get(col, k);
                }
                result.set(col, row, sum);
            }
        }

        return result;
    }

    public static Matrix4 projection(float fov, float aspect, float near, float far) {
        Matrix4 result = Matrix4.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        result.set(0, 0, 1.0f / (aspect * tanFOV));
        result.set(1, 1, 1.0f / tanFOV);
        result.set(2, 2, -((far + near) / range));
        result.set(3, 2, -((2 * far * near) / range));
        result.set(3, 3, 0.0f);
        result.set(2, 3, -1.0f);

        return result;
    }

    public static Matrix4 view(Vector3 position, Vector3 rotation) {
        Matrix4 result;

        Matrix4 translationMatrix = Matrix4.translate(position.negate());
        Matrix4 rotXMatrix = Matrix4.rotate(rotation.x, new Vector3(1, 0, 0));
        Matrix4 rotYMatrix = Matrix4.rotate(rotation.y, new Vector3(0, 1, 0));
        Matrix4 rotZMatrix = Matrix4.rotate(rotation.z, new Vector3(0, 0, 1));

        Matrix4 rotationMatrix = Matrix4.multiply(rotZMatrix, Matrix4.multiply(rotYMatrix, rotXMatrix));

        result = Matrix4.multiply(rotationMatrix, translationMatrix);

        return result;
    }

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elements);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Matrix4 other = (Matrix4) obj;
        return Arrays.equals(elements, other.elements);
    }

}