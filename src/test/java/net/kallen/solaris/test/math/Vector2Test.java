package net.kallen.solaris.test.math;

import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;


public class Vector2Test {

    private static final float EPSILON = 0.00001f;


    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    @Test
    public void testConstructor() {
        Vector2 vector = new Vector2(2.0f, 3.0f);

        assertEquals(2.0f, vector.x, EPSILON);
        assertEquals(3.0f, vector.y, EPSILON);
    }

    @Test
    public void testVector3Constructor() {
        Vector3 source = new Vector3(2.0f, 3.0f, 4.0f);
        Vector2 vector = new Vector2(source);

        assertEquals(2.0f, vector.x, EPSILON);
        assertEquals(3.0f, vector.y, EPSILON);
    }


    // ------------------------------------------------------------
    // Set
    // ------------------------------------------------------------

    @Test
    public void testSetComponents() {
        Vector2 vector = new Vector2(1, 2);

        vector.set(3, 4);

        assertEquals(new Vector2(3, 4), vector);
    }

    @Test
    public void testSetVector() {
        Vector2 vector = new Vector2(1, 2);
        Vector2 other = new Vector2(3, 4);

        vector.set(other);

        assertEquals(other, vector);
    }


    // ------------------------------------------------------------
    // Arithmetic
    // ------------------------------------------------------------

    @Test
    public void testAdd() {
        Vector2 a = new Vector2(1, 2);
        Vector2 b = new Vector2(3, 4);

        assertEquals(new Vector2(4, 6), a.add(b));
    }

    @Test
    public void testSubtract() {
        Vector2 a = new Vector2(5, 7);
        Vector2 b = new Vector2(2, 3);

        assertEquals(new Vector2(3, 4), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        Vector2 a = new Vector2(2, 3);
        Vector2 b = new Vector2(4, 5);

        assertEquals(new Vector2(8, 15), a.multiply(b));
    }

    @Test
    public void testDivide() {
        Vector2 a = new Vector2(8, 15);
        Vector2 b = new Vector2(2, 3);

        assertEquals(new Vector2(4, 5), a.divide(b));
    }

    @Test
    public void testScale() {
        Vector2 vector = new Vector2(2, 3);

        assertEquals(new Vector2(6, 9), vector.scale(3));
    }

    @Test
    public void testNegate() {
        Vector2 vector = new Vector2(2, -3);

        assertEquals(new Vector2(-2, 3), vector.negate());
    }


    // ------------------------------------------------------------
    // Length / normalization
    // ------------------------------------------------------------

    @Test
    public void testLength() {
        Vector2 vector = new Vector2(3, 4);

        assertEquals(5.0f, vector.length(), EPSILON);
    }

    @Test
    public void testNormalize() {
        Vector2 vector = new Vector2(3, 4);

        Vector2 normalized = vector.normalize();

        assertEquals(0.6f, normalized.x, EPSILON);
        assertEquals(0.8f, normalized.y, EPSILON);
        assertEquals(1.0f, normalized.length(), EPSILON);
    }

    @Test
    public void testNormalizeZeroVector() {
        Vector2 vector = new Vector2(0, 0);

        assertEquals(Vector2.ZERO, vector.normalize());
    }


    // ------------------------------------------------------------
    // Dot product
    // ------------------------------------------------------------

    @Test
    public void testDot() {
        Vector2 a = new Vector2(2, 3);
        Vector2 b = new Vector2(4, 5);

        assertEquals(23.0f, a.dot(b), EPSILON);
    }

    @Test
    public void testDotPerpendicular() {
        assertEquals(
                0.0f,
                Vector2.UNIT_X.dot(Vector2.UNIT_Y),
                EPSILON
        );
    }


    // ------------------------------------------------------------
    // Distance
    // ------------------------------------------------------------

    @Test
    public void testDistance() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(3, 4);

        assertEquals(5.0f, a.distance(b), EPSILON);
    }

    @Test
    public void testDistanceSquared() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(3, 4);

        assertEquals(25.0f, a.distanceSquared(b), EPSILON);
    }

    @Test
    public void testStaticDistance() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(3, 4);

        assertEquals(5.0f, Vector2.distance(a, b), EPSILON);
    }

    @Test
    public void testStaticDistanceSquared() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(3, 4);

        assertEquals(25.0f, Vector2.distanceSquared(a, b), EPSILON);
    }


    // ------------------------------------------------------------
    // Center / average
    // ------------------------------------------------------------

    @Test
    public void testCenter() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(4, 6);

        assertEquals(new Vector2(2, 3), a.center(b));
    }

    @Test
    public void testStaticCenter() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(4, 6);

        assertEquals(new Vector2(2, 3), Vector2.center(a, b));
    }

    @Test
    public void testAverage() {
        Vector2 vector = new Vector2(2, 2);

        Vector2 result = vector.average(
                new Vector2(4, 4),
                new Vector2(6, 6)
        );

        assertEquals(new Vector2(4, 4), result);
    }


    // ------------------------------------------------------------
    // Interpolation
    // ------------------------------------------------------------

    @Test
    public void testLerpBeginning() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(10, 20);

        assertEquals(
                new Vector2(0, 0),
                a.lerp(b, 0.0f)
        );
    }

    @Test
    public void testLerpMiddle() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(10, 20);

        assertEquals(
                new Vector2(5, 10),
                a.lerp(b, 0.5f)
        );
    }

    @Test
    public void testLerpEnd() {
        Vector2 a = new Vector2(0, 0);
        Vector2 b = new Vector2(10, 20);

        assertEquals(
                new Vector2(10, 20),
                a.lerp(b, 1.0f)
        );
    }


    // ------------------------------------------------------------
    // Min / max / clamp
    // ------------------------------------------------------------

    @Test
    public void testMin() {
        Vector2 a = new Vector2(5, 2);
        Vector2 b = new Vector2(3, 4);

        assertEquals(new Vector2(3, 2), a.min(b));
    }

    @Test
    public void testMax() {
        Vector2 a = new Vector2(5, 2);
        Vector2 b = new Vector2(3, 4);

        assertEquals(new Vector2(5, 4), a.max(b));
    }

    @Test
    public void testClampVector() {
        Vector2 vector = new Vector2(5, -2);

        assertEquals(
                new Vector2(3, 0),
                vector.clamp(
                        new Vector2(0, 0),
                        new Vector2(3, 4)
                )
        );
    }

    @Test
    public void testClampScalar() {
        Vector2 vector = new Vector2(5, -2);

        assertEquals(
                new Vector2(3, 0),
                vector.clamp(0, 3)
        );
    }

    @Test
    public void testAbs() {
        Vector2 vector = new Vector2(-5, 3);

        assertEquals(new Vector2(5, 3), vector.abs());
    }


    // ------------------------------------------------------------
    // Rotation
    // ------------------------------------------------------------

    @Test
    public void testRotate90Degrees() {
        Vector2 vector = new Vector2(1, 0);

        Vector2 result = vector.rotate((float) Math.PI / 2);

        assertEquals(0.0f, result.x, EPSILON);
        assertEquals(1.0f, result.y, EPSILON);
    }

    @Test
    public void testRotate180Degrees() {
        Vector2 vector = new Vector2(1, 0);

        Vector2 result = vector.rotate((float) Math.PI);

        assertEquals(-1.0f, result.x, EPSILON);
        assertEquals(0.0f, result.y, EPSILON);
    }

    @Test
    public void testPerpendicular() {
        Vector2 vector = new Vector2(1, 2);

        assertEquals(
                new Vector2(-2, 1),
                vector.perpendicular()
        );
    }


    // ------------------------------------------------------------
    // Projection / reflection
    // ------------------------------------------------------------

    @Test
    public void testProject() {
        Vector2 vector = new Vector2(3, 4);
        Vector2 onto = new Vector2(1, 0);

        assertEquals(
                new Vector2(3, 0),
                vector.project(onto)
        );
    }

    @Test
    public void testProjectOntoZero() {
        Vector2 vector = new Vector2(3, 4);

        assertEquals(
                Vector2.ZERO,
                vector.project(Vector2.ZERO)
        );
    }

    @Test
    public void testReflect() {
        Vector2 vector = new Vector2(1, -1);
        Vector2 normal = new Vector2(0, 1);

        assertEquals(
                new Vector2(1, 1),
                vector.reflect(normal)
        );
    }


    // ------------------------------------------------------------
    // Angle
    // ------------------------------------------------------------

    @Test
    public void testAngle() {
        Vector2 x = new Vector2(1, 0);
        Vector2 y = new Vector2(0, 1);

        assertEquals(
                Math.PI / 2,
                x.angle(y),
                EPSILON
        );
    }

    @Test
    public void testAngleParallel() {
        Vector2 a = new Vector2(1, 0);
        Vector2 b = new Vector2(2, 0);

        assertEquals(0.0f, a.angle(b), EPSILON);
    }

    @Test
    public void testAngleOpposite() {
        Vector2 a = new Vector2(1, 0);
        Vector2 b = new Vector2(-1, 0);

        assertEquals(Math.PI, a.angle(b), EPSILON);
    }

    @Test
    public void testAngleWithZeroVector() {
        assertEquals(
                0.0f,
                Vector2.ZERO.angle(Vector2.UNIT_X),
                EPSILON
        );
    }


    // ------------------------------------------------------------
    // Constants
    // ------------------------------------------------------------

    @Test
    public void testConstants() {
        assertEquals(new Vector2(0, 0), Vector2.ZERO);
        assertEquals(new Vector2(1, 1), Vector2.ONE);
        assertEquals(new Vector2(1, 0), Vector2.UNIT_X);
        assertEquals(new Vector2(0, 1), Vector2.UNIT_Y);
    }


    // ------------------------------------------------------------
    // Equality / string
    // ------------------------------------------------------------

    @Test
    public void testEquals() {
        assertEquals(
                new Vector2(1, 2),
                new Vector2(1, 2)
        );

        assertNotEquals(
                new Vector2(1, 2),
                new Vector2(2, 1)
        );
    }

    @Test
    public void testHashCode() {
        Vector2 a = new Vector2(1, 2);
        Vector2 b = new Vector2(1, 2);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testToString() {
        Vector2 vector = new Vector2(1, 2);

        assertEquals(
                "(x: 1.0, y: 2.0)",
                vector.toString()
        );
    }
}
