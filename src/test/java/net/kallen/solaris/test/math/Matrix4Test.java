package test.java.net.kallen.solaris.test.math;

import main.java.net.kallen.solaris.math.vector.Matrix4;
import main.java.net.kallen.solaris.math.vector.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;

public class Matrix4Test {

    private static final float EPSILON = 0.0001f;

    private void assertMatrixEquals(Matrix4 expected, Matrix4 actual) {
        for (int y = 0; y < Matrix4.SIZE; y++) {
            for (int x = 0; x < Matrix4.SIZE; x++) {
                assertEquals(
                        "Mismatch at (" + x + ", " + y + ")",
                        expected.get(x, y),
                        actual.get(x, y),
                        EPSILON
                );
            }
        }
    }

    @Test
    public void testIdentity() {
        Matrix4 matrix = Matrix4.identity();

        for (int y = 0; y < Matrix4.SIZE; y++) {
            for (int x = 0; x < Matrix4.SIZE; x++) {
                float expected = (x == y) ? 1f : 0f;
                assertEquals(expected, matrix.get(x, y), EPSILON);
            }
        }
    }

    @Test
    public void testTranslate() {
        Vector3 translation = new Vector3(2, 3, 4);

        Matrix4 matrix = Matrix4.translate(translation);

        assertEquals(1f, matrix.get(0, 0), EPSILON);
        assertEquals(1f, matrix.get(1, 1), EPSILON);
        assertEquals(1f, matrix.get(2, 2), EPSILON);
        assertEquals(1f, matrix.get(3, 3), EPSILON);

        // Translation is now in the fourth column.
        assertEquals(2f, matrix.get(3, 0), EPSILON);
        assertEquals(3f, matrix.get(3, 1), EPSILON);
        assertEquals(4f, matrix.get(3, 2), EPSILON);

        // Everything else should be zero.
        assertEquals(0f, matrix.get(0, 3), EPSILON);
        assertEquals(0f, matrix.get(1, 3), EPSILON);
        assertEquals(0f, matrix.get(2, 3), EPSILON);
    }

    @Test
    public void testScale() {
        Vector3 scale = new Vector3(2, 3, 4);

        Matrix4 matrix = Matrix4.scale(scale);

        assertEquals(2f, matrix.get(0, 0), EPSILON);
        assertEquals(3f, matrix.get(1, 1), EPSILON);
        assertEquals(4f, matrix.get(2, 2), EPSILON);
        assertEquals(1f, matrix.get(3, 3), EPSILON);
    }

    @Test
    public void testRotateIdentity() {
        Matrix4 matrix = Matrix4.rotate(0, new Vector3(1, 0, 0));

        assertMatrixEquals(Matrix4.identity(), matrix);
    }

    @Test
    public void testRotateX() {
        Matrix4 matrix = Matrix4.rotate(90, new Vector3(1, 0, 0));

        // Rotation around X:
        //
        // [ 1  0  0  0 ]
        // [ 0  0 -1  0 ]
        // [ 0  1  0  0 ]
        // [ 0  0  0  1 ]

        assertEquals(1f, matrix.get(0, 0), EPSILON);

        assertEquals(0f, matrix.get(1, 1), EPSILON);
        assertEquals(-1f, matrix.get(2, 1), EPSILON);

        assertEquals(1f, matrix.get(1, 2), EPSILON);
        assertEquals(0f, matrix.get(2, 2), EPSILON);
    }

    @Test
    public void testRotateY() {
        Matrix4 matrix = Matrix4.rotate(90, new Vector3(0, 1, 0));

        // Rotation around Y:
        //
        // [ 0  0  1  0 ]
        // [ 0  1  0  0 ]
        // [-1  0  0  0 ]
        // [ 0  0  0  1 ]

        assertEquals(0f, matrix.get(0, 0), EPSILON);
        assertEquals(1f, matrix.get(2, 0), EPSILON);

        assertEquals(1f, matrix.get(1, 1), EPSILON);

        assertEquals(-1f, matrix.get(0, 2), EPSILON);
        assertEquals(0f, matrix.get(2, 2), EPSILON);
    }

    @Test
    public void testRotateZ() {
        Matrix4 matrix = Matrix4.rotate(90, new Vector3(0, 0, 1));

        // Rotation around Z:
        //
        // [ 0 -1  0  0 ]
        // [ 1  0  0  0 ]
        // [ 0  0  1  0 ]
        // [ 0  0  0  1 ]

        assertEquals(0f, matrix.get(0, 0), EPSILON);
        assertEquals(-1f, matrix.get(1, 0), EPSILON);

        assertEquals(1f, matrix.get(0, 1), EPSILON);
        assertEquals(0f, matrix.get(1, 1), EPSILON);

        assertEquals(1f, matrix.get(2, 2), EPSILON);
    }

    @Test
    public void testMultiplyIdentity() {
        Matrix4 matrix = Matrix4.translate(new Vector3(2, 3, 4));

        assertMatrixEquals(
                matrix,
                Matrix4.multiply(Matrix4.identity(), matrix)
        );

        assertMatrixEquals(
                matrix,
                Matrix4.multiply(matrix, Matrix4.identity())
        );
    }

    @Test
    public void testMultiply() {
        Matrix4 translation = Matrix4.translate(new Vector3(2, 3, 4));
        Matrix4 scale = Matrix4.scale(new Vector3(2, 3, 4));

        Matrix4 result = Matrix4.multiply(translation, scale);

        assertEquals(2f, result.get(0, 0), EPSILON);
        assertEquals(3f, result.get(1, 1), EPSILON);
        assertEquals(4f, result.get(2, 2), EPSILON);

        assertEquals(2f, result.get(3, 0), EPSILON);
        assertEquals(3f, result.get(3, 1), EPSILON);
        assertEquals(4f, result.get(3, 2), EPSILON);

        assertEquals(1f, result.get(3, 3), EPSILON);
    }

    @Test
    public void testProjection() {
        float fov = 90f;
        float aspect = 16f / 9f;
        float near = 0.1f;
        float far = 100f;

        Matrix4 matrix = Matrix4.projection(fov, aspect, near, far);

        float tanFOV = (float) Math.tan(Math.toRadians(fov / 2));
        float range = far - near;

        assertEquals(
                1f / (aspect * tanFOV),
                matrix.get(0, 0),
                EPSILON
        );

        assertEquals(
                1f / tanFOV,
                matrix.get(1, 1),
                EPSILON
        );

        assertEquals(
                -((far + near) / range),
                matrix.get(2, 2),
                EPSILON
        );

        assertEquals(
                -(2 * far * near / range),
                matrix.get(3, 2),
                EPSILON
        );

        assertEquals(-1f, matrix.get(2, 3), EPSILON);
        assertEquals(0f, matrix.get(3, 3), EPSILON);
    }

    @Test
    public void testTransformIdentity() {
        Matrix4 matrix = Matrix4.transform(
                new Vector3(0, 0, 0),
                new Vector3(0, 0, 0),
                new Vector3(1, 1, 1)
        );

        assertMatrixEquals(Matrix4.identity(), matrix);
    }

    @Test
    public void testTransformTranslation() {
        Matrix4 matrix = Matrix4.transform(
                new Vector3(2, 3, 4),
                new Vector3(0, 0, 0),
                new Vector3(1, 1, 1)
        );

        Matrix4 expected = Matrix4.translate(
                new Vector3(2, 3, 4)
        );

        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testTransformScale() {
        Matrix4 matrix = Matrix4.transform(
                new Vector3(0, 0, 0),
                new Vector3(0, 0, 0),
                new Vector3(2, 3, 4)
        );

        Matrix4 expected = Matrix4.scale(
                new Vector3(2, 3, 4)
        );

        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testViewIdentity() {
        Matrix4 matrix = Matrix4.view(
                new Vector3(0, 0, 0),
                new Vector3(0, 0, 0)
        );

        assertMatrixEquals(Matrix4.identity(), matrix);
    }

    @Test
    public void testViewTranslation() {
        Matrix4 matrix = Matrix4.view(
                new Vector3(2, 3, 4),
                new Vector3(0, 0, 0)
        );

        Matrix4 expected = Matrix4.translate(
                new Vector3(-2, -3, -4)
        );

        assertMatrixEquals(expected, matrix);
    }

    @Test
    public void testGetAndSet() {
        Matrix4 matrix = new Matrix4();

        matrix.set(2, 3, 42f);

        assertEquals(42f, matrix.get(2, 3), EPSILON);
    }

    @Test
    public void testGetAll() {
        Matrix4 matrix = Matrix4.identity();

        float[] elements = matrix.getAll();

        assertEquals(16, elements.length);

        assertEquals(1f, elements[0], EPSILON);
        assertEquals(1f, elements[5], EPSILON);
        assertEquals(1f, elements[10], EPSILON);
        assertEquals(1f, elements[15], EPSILON);
    }

    @Test
    public void testEquals() {
        Matrix4 matrix1 = Matrix4.translate(new Vector3(1, 2, 3));
        Matrix4 matrix2 = Matrix4.translate(new Vector3(1, 2, 3));
        Matrix4 matrix3 = Matrix4.translate(new Vector3(3, 2, 1));

        assertEquals(matrix1, matrix2);
        assertNotEquals(matrix1, matrix3);
    }

    @Test
    public void testHashCode() {
        Matrix4 matrix1 = Matrix4.translate(new Vector3(1, 2, 3));
        Matrix4 matrix2 = Matrix4.translate(new Vector3(1, 2, 3));

        assertEquals(matrix1.hashCode(), matrix2.hashCode());
    }
}