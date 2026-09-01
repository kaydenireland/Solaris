package test.java.net.kallen.solaris.test.math;

import main.java.net.kallen.solaris.math.vector.Vector3;
import main.java.net.kallen.solaris.math.vector.Vector4;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector4Test {

    private static final float EPSILON = 0.000001f;

    private void assertVectorEquals(Vector4 expected, Vector4 actual) {
        assertEquals(expected.x, actual.x, EPSILON);
        assertEquals(expected.y, actual.y, EPSILON);
        assertEquals(expected.z, actual.z, EPSILON);
        assertEquals(expected.w, actual.w, EPSILON);
    }

    @Test
    public void testConstructor() {
        Vector4 vector = new Vector4(1, 2, 3, 4);

        assertEquals(1, vector.x, EPSILON);
        assertEquals(2, vector.y, EPSILON);
        assertEquals(3, vector.z, EPSILON);
        assertEquals(4, vector.w, EPSILON);
    }

    @Test
    public void testVector3Constructor() {
        Vector3 vector = new Vector3(1, 2, 3);
        Vector4 result = new Vector4(vector, 4);

        assertVectorEquals(new Vector4(1, 2, 3, 4), result);
    }

    @Test
    public void testSetComponents() {
        Vector4 vector = new Vector4(1, 2, 3, 4);

        vector.set(5, 6, 7, 8);

        assertVectorEquals(new Vector4(5, 6, 7, 8), vector);
    }

    @Test
    public void testSetVector() {
        Vector4 vector = new Vector4(1, 2, 3, 4);
        Vector4 other = new Vector4(5, 6, 7, 8);

        vector.set(other);

        assertVectorEquals(other, vector);
    }

    @Test
    public void testAdd() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(5, 6, 7, 8);

        assertVectorEquals(
                new Vector4(6, 8, 10, 12),
                a.add(b)
        );
    }

    @Test
    public void testSubtract() {
        Vector4 a = new Vector4(5, 7, 9, 11);
        Vector4 b = new Vector4(1, 2, 3, 4);

        assertVectorEquals(
                new Vector4(4, 5, 6, 7),
                a.subtract(b)
        );
    }

    @Test
    public void testMultiply() {
        Vector4 a = new Vector4(2, 3, 4, 5);
        Vector4 b = new Vector4(5, 4, 3, 2);

        assertVectorEquals(
                new Vector4(10, 12, 12, 10),
                a.multiply(b)
        );
    }

    @Test
    public void testDivide() {
        Vector4 a = new Vector4(10, 12, 15, 20);
        Vector4 b = new Vector4(2, 3, 5, 4);

        assertVectorEquals(
                new Vector4(5, 4, 3, 5),
                a.divide(b)
        );
    }

    @Test
    public void testScale() {
        Vector4 vector = new Vector4(1, 2, 3, 4);

        assertVectorEquals(
                new Vector4(2, 4, 6, 8),
                vector.scale(2)
        );
    }

    @Test
    public void testNegate() {
        Vector4 vector = new Vector4(1, -2, 3, -4);

        assertVectorEquals(
                new Vector4(-1, 2, -3, 4),
                vector.negate()
        );
    }

    @Test
    public void testLength() {
        Vector4 vector = new Vector4(1, 2, 2, 4);

        assertEquals(5, vector.length(), EPSILON);
    }

    @Test
    public void testNormalize() {
        Vector4 vector = new Vector4(0, 0, 3, 4);

        assertVectorEquals(
                new Vector4(0, 0, 0.6f, 0.8f),
                vector.normalize()
        );
    }

    @Test
    public void testNormalizeZero() {
        assertVectorEquals(
                Vector4.ZERO,
                Vector4.ZERO.normalize()
        );
    }

    @Test
    public void testDot() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(5, 6, 7, 8);

        assertEquals(70, a.dot(b), EPSILON);
    }

    @Test
    public void testDistance() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(2, 4, 6, 8);

        assertEquals(Math.sqrt(30), a.distance(b), EPSILON);
    }

    @Test
    public void testDistanceSquared() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(2, 4, 6, 8);

        assertEquals(30, a.distanceSquared(b), EPSILON);
    }

    @Test
    public void testCenter() {
        Vector4 a = new Vector4(0, 2, 4, 6);
        Vector4 b = new Vector4(2, 4, 6, 8);

        assertVectorEquals(
                new Vector4(1, 3, 5, 7),
                a.center(b)
        );
    }

    @Test
    public void testAverage() {
        Vector4 a = new Vector4(0, 0, 0, 0);
        Vector4 b = new Vector4(2, 4, 6, 8);
        Vector4 c = new Vector4(4, 6, 8, 10);

        assertVectorEquals(
                new Vector4(2, 10f / 3f, 14f / 3f, 6),
                a.average(b, c)
        );
    }

    @Test
    public void testLerp() {
        Vector4 a = new Vector4(0, 0, 0, 0);
        Vector4 b = new Vector4(10, 20, 30, 40);

        assertVectorEquals(
                new Vector4(5, 10, 15, 20),
                a.lerp(b, 0.5f)
        );
    }

    @Test
    public void testMin() {
        Vector4 a = new Vector4(1, 5, 3, 8);
        Vector4 b = new Vector4(4, 2, 6, 7);

        assertVectorEquals(
                new Vector4(1, 2, 3, 7),
                a.min(b)
        );
    }

    @Test
    public void testMax() {
        Vector4 a = new Vector4(1, 5, 3, 8);
        Vector4 b = new Vector4(4, 2, 6, 7);

        assertVectorEquals(
                new Vector4(4, 5, 6, 8),
                a.max(b)
        );
    }

    @Test
    public void testClampVector() {
        Vector4 vector = new Vector4(-1, 2, 7, 10);

        assertVectorEquals(
                new Vector4(0, 2, 5, 5),
                vector.clamp(
                        new Vector4(0, 0, 0, 0),
                        new Vector4(5, 5, 5, 5)
                )
        );
    }

    @Test
    public void testClampScalar() {
        Vector4 vector = new Vector4(-1, 2, 7, 10);

        assertVectorEquals(
                new Vector4(0, 2, 5, 5),
                vector.clamp(0, 5)
        );
    }

    @Test
    public void testAbs() {
        Vector4 vector = new Vector4(-1, 2, -3, 4);

        assertVectorEquals(
                new Vector4(1, 2, 3, 4),
                vector.abs()
        );
    }

    @Test
    public void testProject() {
        Vector4 vector = new Vector4(2, 2, 0, 0);
        Vector4 onto = new Vector4(1, 0, 0, 0);

        assertVectorEquals(
                new Vector4(2, 0, 0, 0),
                vector.project(onto)
        );
    }

    @Test
    public void testProjectZero() {
        Vector4 vector = new Vector4(1, 2, 3, 4);

        assertVectorEquals(
                Vector4.ZERO,
                vector.project(Vector4.ZERO)
        );
    }

    @Test
    public void testReflect() {
        Vector4 vector = new Vector4(1, -1, 0, 0);
        Vector4 normal = new Vector4(0, 1, 0, 0);

        assertVectorEquals(
                new Vector4(1, 1, 0, 0),
                vector.reflect(normal)
        );
    }

    @Test
    public void testAngle() {
        Vector4 a = new Vector4(1, 0, 0, 0);
        Vector4 b = new Vector4(0, 1, 0, 0);

        assertEquals(
                Math.PI / 2,
                a.angle(b),
                EPSILON
        );
    }

    @Test
    public void testAngleZero() {
        Vector4 a = Vector4.ZERO;
        Vector4 b = Vector4.UNIT_X;

        assertEquals(0, a.angle(b), EPSILON);
    }

    @Test
    public void testEquals() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(1, 2, 3, 4);

        assertEquals(a, b);
    }

    @Test
    public void testNotEquals() {
        Vector4 a = new Vector4(1, 2, 3, 4);
        Vector4 b = new Vector4(1, 2, 3, 5);

        assertNotEquals(a, b);
    }

    @Test
    public void testZeroConstant() {
        assertVectorEquals(
                new Vector4(0, 0, 0, 0),
                Vector4.ZERO
        );
    }
}