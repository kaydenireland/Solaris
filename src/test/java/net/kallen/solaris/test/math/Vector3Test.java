package net.kallen.solaris.test.math;

import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.math.vector.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;


public class Vector3Test {

    private static final float EPSILON = 0.00001f;


    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    @Test
    public void testConstructor() {
        Vector3 vector = new Vector3(1, 2, 3);

        assertEquals(1.0f, vector.x, EPSILON);
        assertEquals(2.0f, vector.y, EPSILON);
        assertEquals(3.0f, vector.z, EPSILON);
    }

    @Test
    public void testCopyConstructor() {
        Vector3 source = new Vector3(1, 2, 3);
        Vector3 vector = new Vector3(source);

        assertEquals(source, vector);
    }

    @Test
    public void testVector2Constructor() {
        Vector2 source = new Vector2(1, 2);
        Vector3 vector = new Vector3(source);

        assertEquals(new Vector3(1, 2, 0), vector);
    }


    // ------------------------------------------------------------
    // Conversion
    // ------------------------------------------------------------

    @Test
    public void testGet2D() {
        Vector3 vector = new Vector3(1, 2, 3);

        assertEquals(
                new Vector2(1, 2),
                Vector3.get2D(vector)
        );
    }

    @Test
    public void testTo3D() {
        Vector2 vector = new Vector2(1, 2);

        assertEquals(
                new Vector3(1, 2, 0),
                Vector3.to3D(vector)
        );
    }


    // ------------------------------------------------------------
    // Set
    // ------------------------------------------------------------

    @Test
    public void testSetComponents() {
        Vector3 vector = new Vector3(1, 2, 3);

        vector.set(4, 5, 6);

        assertEquals(new Vector3(4, 5, 6), vector);
    }

    @Test
    public void testSetVector() {
        Vector3 vector = new Vector3(1, 2, 3);
        Vector3 other = new Vector3(4, 5, 6);

        vector.set(other);

        assertEquals(other, vector);
    }


    // ------------------------------------------------------------
    // Arithmetic
    // ------------------------------------------------------------

    @Test
    public void testAdd() {
        Vector3 a = new Vector3(1, 2, 3);
        Vector3 b = new Vector3(4, 5, 6);

        assertEquals(new Vector3(5, 7, 9), a.add(b));
    }

    @Test
    public void testSubtract() {
        Vector3 a = new Vector3(5, 7, 9);
        Vector3 b = new Vector3(1, 2, 3);

        assertEquals(new Vector3(4, 5, 6), a.subtract(b));
    }

    @Test
    public void testMultiply() {
        Vector3 a = new Vector3(2, 3, 4);
        Vector3 b = new Vector3(5, 6, 7);

        assertEquals(new Vector3(10, 18, 28), a.multiply(b));
    }

    @Test
    public void testDivide() {
        Vector3 a = new Vector3(10, 18, 28);
        Vector3 b = new Vector3(2, 3, 4);

        assertEquals(new Vector3(5, 6, 7), a.divide(b));
    }

    @Test
    public void testScale() {
        Vector3 vector = new Vector3(2, 3, 4);

        assertEquals(
                new Vector3(6, 9, 12),
                vector.scale(3)
        );
    }

    @Test
    public void testNegate() {
        Vector3 vector = new Vector3(2, -3, 4);

        assertEquals(
                new Vector3(-2, 3, -4),
                vector.negate()
        );
    }


    // ------------------------------------------------------------
    // Length / normalization
    // ------------------------------------------------------------

    @Test
    public void testLength() {
        Vector3 vector = new Vector3(2, 3, 6);

        assertEquals(
                7.0f,
                vector.length(),
                EPSILON
        );
    }

    @Test
    public void testNormalize() {
        Vector3 vector = new Vector3(2, 3, 6);

        Vector3 normalized = vector.normalize();

        assertEquals(
                2.0f / 7.0f,
                normalized.x,
                EPSILON
        );

        assertEquals(
                3.0f / 7.0f,
                normalized.y,
                EPSILON
        );

        assertEquals(
                6.0f / 7.0f,
                normalized.z,
                EPSILON
        );

        assertEquals(
                1.0f,
                normalized.length(),
                EPSILON
        );
    }

    @Test
    public void testNormalizeZeroVector() {
        assertEquals(
                Vector3.ZERO,
                Vector3.ZERO.normalize()
        );
    }


    // ------------------------------------------------------------
    // Dot product
    // ------------------------------------------------------------

    @Test
    public void testDot() {
        Vector3 a = new Vector3(1, 2, 3);
        Vector3 b = new Vector3(4, 5, 6);

        assertEquals(
                32.0f,
                a.dot(b),
                EPSILON
        );
    }

    @Test
    public void testDotPerpendicular() {
        assertEquals(
                0.0f,
                Vector3.UNIT_X.dot(Vector3.UNIT_Y),
                EPSILON
        );
    }


    // ------------------------------------------------------------
    // Cross product
    // ------------------------------------------------------------

    @Test
    public void testCross() {
        Vector3 result =
                Vector3.UNIT_X.cross(Vector3.UNIT_Y);

        assertEquals(Vector3.UNIT_Z, result);
    }

    @Test
    public void testCrossReverse() {
        Vector3 result =
                Vector3.UNIT_Y.cross(Vector3.UNIT_X);

        assertEquals(
                new Vector3(0, 0, -1),
                result
        );
    }

    @Test
    public void testCrossParallelVectors() {
        Vector3 result =
                new Vector3(1, 2, 3)
                        .cross(new Vector3(2, 4, 6));

        assertEquals(Vector3.ZERO, result);
    }

    @Test
    public void testStaticCross() {
        Vector3 result = Vector3.cross(
                Vector3.UNIT_X,
                Vector3.UNIT_Y
        );

        assertEquals(Vector3.UNIT_Z, result);
    }


    // ------------------------------------------------------------
    // Distance
    // ------------------------------------------------------------

    @Test
    public void testDistance() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(2, 3, 6);

        assertEquals(
                7.0f,
                a.distance(b),
                EPSILON
        );
    }

    @Test
    public void testDistanceSquared() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(2, 3, 6);

        assertEquals(
                49.0f,
                a.distanceSquared(b),
                EPSILON
        );
    }

    @Test
    public void testStaticDistance() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(2, 3, 6);

        assertEquals(
                7.0f,
                Vector3.distance(a, b),
                EPSILON
        );
    }

    @Test
    public void testStaticDistanceSquared() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(2, 3, 6);

        assertEquals(
                49.0f,
                Vector3.distanceSquared(a, b),
                EPSILON
        );
    }


    // ------------------------------------------------------------
    // Center / average
    // ------------------------------------------------------------

    @Test
    public void testCenter() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(4, 6, 8);

        assertEquals(
                new Vector3(2, 3, 4),
                a.center(b)
        );
    }

    @Test
    public void testStaticCenter() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(4, 6, 8);

        assertEquals(
                new Vector3(2, 3, 4),
                Vector3.center(a, b)
        );
    }

    @Test
    public void testAverage() {
        Vector3 vector = new Vector3(2, 2, 2);

        Vector3 result = vector.average(
                new Vector3(4, 4, 4),
                new Vector3(6, 6, 6)
        );

        assertEquals(
                new Vector3(4, 4, 4),
                result
        );
    }


    // ------------------------------------------------------------
    // Interpolation
    // ------------------------------------------------------------

    @Test
    public void testLerpBeginning() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(10, 20, 30);

        assertEquals(
                new Vector3(0, 0, 0),
                a.lerp(b, 0)
        );
    }

    @Test
    public void testLerpMiddle() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(10, 20, 30);

        assertEquals(
                new Vector3(5, 10, 15),
                a.lerp(b, 0.5f)
        );
    }

    @Test
    public void testLerpEnd() {
        Vector3 a = new Vector3(0, 0, 0);
        Vector3 b = new Vector3(10, 20, 30);

        assertEquals(
                new Vector3(10, 20, 30),
                a.lerp(b, 1)
        );
    }


    // ------------------------------------------------------------
    // Min / max / clamp
    // ------------------------------------------------------------

    @Test
    public void testMin() {
        Vector3 a = new Vector3(5, 2, 8);
        Vector3 b = new Vector3(3, 4, 6);

        assertEquals(
                new Vector3(3, 2, 6),
                a.min(b)
        );
    }

    @Test
    public void testMax() {
        Vector3 a = new Vector3(5, 2, 8);
        Vector3 b = new Vector3(3, 4, 6);

        assertEquals(
                new Vector3(5, 4, 8),
                a.max(b)
        );
    }

    @Test
    public void testClampVector() {
        Vector3 vector = new Vector3(5, -2, 10);

        assertEquals(
                new Vector3(3, 0, 4),
                vector.clamp(
                        new Vector3(0, 0, 0),
                        new Vector3(3, 4, 4)
                )
        );
    }

    @Test
    public void testClampScalar() {
        Vector3 vector = new Vector3(5, -2, 10);

        assertEquals(
                new Vector3(3, 0, 3),
                vector.clamp(0, 3)
        );
    }

    @Test
    public void testAbs() {
        Vector3 vector = new Vector3(-5, 3, -7);

        assertEquals(
                new Vector3(5, 3, 7),
                vector.abs()
        );
    }


    // ------------------------------------------------------------
    // Projection / reflection
    // ------------------------------------------------------------

    @Test
    public void testProject() {
        Vector3 vector = new Vector3(3, 4, 5);
        Vector3 onto = new Vector3(1, 0, 0);

        assertEquals(
                new Vector3(3, 0, 0),
                vector.project(onto)
        );
    }

    @Test
    public void testProjectOntoZero() {
        Vector3 vector = new Vector3(3, 4, 5);

        assertEquals(
                Vector3.ZERO,
                vector.project(Vector3.ZERO)
        );
    }

    @Test
    public void testReflect() {
        Vector3 vector = new Vector3(1, -1, 0);
        Vector3 normal = Vector3.UNIT_Y;

        assertEquals(
                new Vector3(1, 1, 0),
                vector.reflect(normal)
        );
    }


    // ------------------------------------------------------------
    // Angle
    // ------------------------------------------------------------

    @Test
    public void testAngle() {
        assertEquals(
                Math.PI / 2,
                Vector3.UNIT_X.angle(Vector3.UNIT_Y),
                EPSILON
        );
    }

    @Test
    public void testAngleParallel() {
        assertEquals(
                0.0f,
                Vector3.UNIT_X.angle(
                        new Vector3(2, 0, 0)
                ),
                EPSILON
        );
    }

    @Test
    public void testAngleOpposite() {
        assertEquals(
                Math.PI,
                Vector3.UNIT_X.angle(
                        new Vector3(-1, 0, 0)
                ),
                EPSILON
        );
    }

    @Test
    public void testAngleWithZeroVector() {
        assertEquals(
                0.0f,
                Vector3.ZERO.angle(Vector3.UNIT_X),
                EPSILON
        );
    }


    // ------------------------------------------------------------
    // Packing
    // ------------------------------------------------------------

    @Test
    public void testPackUnpack() {
        Vector3 original = new Vector3(10, 20, 30);

        long packed = Vector3.pack(original);
        Vector3 unpacked = Vector3.unpack(packed);

        assertEquals(original, unpacked);
    }

    @Test
    public void testPackUnpackNegativeValues() {
        Vector3 original = new Vector3(-10, -20, -30);

        long packed = Vector3.pack(original);
        Vector3 unpacked = Vector3.unpack(packed);

        assertEquals(original, unpacked);
    }

    @Test
    public void testPackUnpackZero() {
        Vector3 original = Vector3.ZERO;

        long packed = Vector3.pack(original);
        Vector3 unpacked = Vector3.unpack(packed);

        assertEquals(original, unpacked);
    }


    // ------------------------------------------------------------
    // Constants
    // ------------------------------------------------------------

    @Test
    public void testConstants() {
        assertEquals(
                new Vector3(0, 0, 0),
                Vector3.ZERO
        );

        assertEquals(
                new Vector3(1, 1, 1),
                Vector3.ONE
        );

        assertEquals(
                new Vector3(1, 0, 0),
                Vector3.UNIT_X
        );

        assertEquals(
                new Vector3(0, 1, 0),
                Vector3.UNIT_Y
        );

        assertEquals(
                new Vector3(0, 0, 1),
                Vector3.UNIT_Z
        );
    }


    // ------------------------------------------------------------
    // Equality / string
    // ------------------------------------------------------------

    @Test
    public void testEquals() {
        assertEquals(
                new Vector3(1, 2, 3),
                new Vector3(1, 2, 3)
        );

        assertNotEquals(
                new Vector3(1, 2, 3),
                new Vector3(3, 2, 1)
        );
    }

    @Test
    public void testHashCode() {
        Vector3 a = new Vector3(1, 2, 3);
        Vector3 b = new Vector3(1, 2, 3);

        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void testToString() {
        Vector3 vector = new Vector3(1, 2, 3);

        assertEquals(
                "(x: 1.0, y: 2.0, z: 3.0)",
                vector.toString()
        );
    }

    @Test
    public void testPackUnpackBoundaries() {
        Vector3 vector = new Vector3(
                -1048576,
                1048575,
                0
        );

        assertEquals(
                vector,
                Vector3.unpack(Vector3.pack(vector))
        );
    }

}