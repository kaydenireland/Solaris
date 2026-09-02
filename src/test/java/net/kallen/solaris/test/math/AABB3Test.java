package net.kallen.solaris.test.math;

import net.kallen.solaris.math.vector.Vector3;
import net.kallen.solaris.math.box.AABB3;
import org.junit.Test;

import static org.junit.Assert.*;

public class AABB3Test {

    private final net.kallen.solaris.math.box.AABB3 box = new AABB3(
            new Vector3(10, 20, 30),
            new Vector3(40, 50, 60)
    );

    // -------------------------------------------------------------------------
    // contains()
    // -------------------------------------------------------------------------

    @Test
    public void containsPointInsideBox() {
        assertTrue(box.contains(new Vector3(25, 35, 45)));
    }

    @Test
    public void containsPointOnBoundary() {
        assertTrue(box.contains(new Vector3(10, 20, 30)));
        assertTrue(box.contains(new Vector3(40, 50, 60)));

        assertTrue(box.contains(new Vector3(10, 35, 45)));
        assertTrue(box.contains(new Vector3(40, 35, 45)));

        assertTrue(box.contains(new Vector3(25, 20, 45)));
        assertTrue(box.contains(new Vector3(25, 50, 45)));

        assertTrue(box.contains(new Vector3(25, 35, 30)));
        assertTrue(box.contains(new Vector3(25, 35, 60)));
    }

    @Test
    public void doesNotContainPointOutsideBox() {
        assertFalse(box.contains(new Vector3(9, 35, 45)));
        assertFalse(box.contains(new Vector3(41, 35, 45)));

        assertFalse(box.contains(new Vector3(25, 19, 45)));
        assertFalse(box.contains(new Vector3(25, 51, 45)));

        assertFalse(box.contains(new Vector3(25, 35, 29)));
        assertFalse(box.contains(new Vector3(25, 35, 61)));
    }

    // -------------------------------------------------------------------------
    // intersects()
    // -------------------------------------------------------------------------

    @Test
    public void intersectsOverlappingBox() {
        AABB3 other = new AABB3(
                new Vector3(30, 40, 50),
                new Vector3(50, 60, 70)
        );

        assertTrue(box.intersects(other));
    }

    @Test
    public void intersectsBoxContainedInside() {
        AABB3 other = new AABB3(
                new Vector3(15, 25, 35),
                new Vector3(35, 45, 55)
        );

        assertTrue(box.intersects(other));
    }

    @Test
    public void doesNotIntersectSeparatedBox() {
        AABB3 other = new AABB3(
                new Vector3(50, 60, 70),
                new Vector3(70, 80, 90)
        );

        assertFalse(box.intersects(other));
    }

    @Test
    public void intersectsTouchingBoundary() {
        AABB3 other = new AABB3(
                new Vector3(40, 30, 40),
                new Vector3(60, 45, 50)
        );

        assertTrue(box.intersects(other));
    }

    @Test
    public void doesNotIntersectSeparatedOnX() {
        AABB3 other = new AABB3(
                new Vector3(41, 30, 40),
                new Vector3(60, 45, 50)
        );

        assertFalse(box.intersects(other));
    }

    @Test
    public void doesNotIntersectSeparatedOnY() {
        AABB3 other = new AABB3(
                new Vector3(20, 51, 40),
                new Vector3(30, 60, 50)
        );

        assertFalse(box.intersects(other));
    }

    @Test
    public void doesNotIntersectSeparatedOnZ() {
        AABB3 other = new AABB3(
                new Vector3(20, 30, 61),
                new Vector3(30, 40, 70)
        );

        assertFalse(box.intersects(other));
    }

    // -------------------------------------------------------------------------
    // expand()
    // -------------------------------------------------------------------------

    @Test
    public void expandPositiveX() {
        AABB3 result = box.expand(new Vector3(5, 0, 0));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.min.z, 0.0001f);

        assertEquals(45, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    @Test
    public void expandNegativeX() {
        AABB3 result = box.expand(new Vector3(-5, 0, 0));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    @Test
    public void expandPositiveY() {
        AABB3 result = box.expand(new Vector3(0, 5, 0));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(55, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    @Test
    public void expandNegativeY() {
        AABB3 result = box.expand(new Vector3(0, -5, 0));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(15, result.min.y, 0.0001f);
        assertEquals(30, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    @Test
    public void expandPositiveZ() {
        AABB3 result = box.expand(new Vector3(0, 0, 5));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
        assertEquals(65, result.max.z, 0.0001f);
    }

    @Test
    public void expandNegativeZ() {
        AABB3 result = box.expand(new Vector3(0, 0, -5));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(25, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    @Test
    public void expandMultipleAxes() {
        AABB3 result = box.expand(new Vector3(-5, 10, -15));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(15, result.min.z, 0.0001f);

        assertEquals(40, result.max.x, 0.0001f);
        assertEquals(60, result.max.y, 0.0001f);
        assertEquals(60, result.max.z, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // center()
    // -------------------------------------------------------------------------

    @Test
    public void centerReturnsCenterOfBox() {
        Vector3 center = box.center();

        assertEquals(25, center.x, 0.0001f);
        assertEquals(35, center.y, 0.0001f);
        assertEquals(45, center.z, 0.0001f);
    }

    @Test
    public void centerWorksWithNegativeCoordinates() {
        AABB3 negativeBox = new AABB3(
                new Vector3(-20, -40, -60),
                new Vector3(10, 20, 30)
        );

        Vector3 center = negativeBox.center();

        assertEquals(-5, center.x, 0.0001f);
        assertEquals(-10, center.y, 0.0001f);
        assertEquals(-15, center.z, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // offset()
    // -------------------------------------------------------------------------

    @Test
    public void offsetMovesBoxPositive() {
        AABB3 result = box.offset(new Vector3(5, 10, 15));

        assertEquals(15, result.min.x, 0.0001f);
        assertEquals(30, result.min.y, 0.0001f);
        assertEquals(45, result.min.z, 0.0001f);

        assertEquals(45, result.max.x, 0.0001f);
        assertEquals(60, result.max.y, 0.0001f);
        assertEquals(75, result.max.z, 0.0001f);
    }

    @Test
    public void offsetMovesBoxNegative() {
        AABB3 result = box.offset(new Vector3(-5, -10, -15));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(10, result.min.y, 0.0001f);
        assertEquals(15, result.min.z, 0.0001f);

        assertEquals(35, result.max.x, 0.0001f);
        assertEquals(40, result.max.y, 0.0001f);
        assertEquals(45, result.max.z, 0.0001f);
    }

    @Test
    public void offsetDoesNotModifyOriginalBox() {
        AABB3 result = box.offset(new Vector3(5, 10, 15));

        assertEquals(10, box.min.x, 0.0001f);
        assertEquals(20, box.min.y, 0.0001f);
        assertEquals(30, box.min.z, 0.0001f);

        assertEquals(40, box.max.x, 0.0001f);
        assertEquals(50, box.max.y, 0.0001f);
        assertEquals(60, box.max.z, 0.0001f);

        assertNotSame(box, result);
    }

    // -------------------------------------------------------------------------
    // calculateXOffset()
    // -------------------------------------------------------------------------

    @Test
    public void calculateXOffsetMovingRightIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(0, 25, 40),
                new Vector3(5, 35, 50)
        );

        float offset = box.calculateXOffset(other, 20);

        assertEquals(5, offset, 0.0001f);
    }

    @Test
    public void calculateXOffsetMovingLeftIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(45, 25, 40),
                new Vector3(50, 35, 50)
        );

        float offset = box.calculateXOffset(other, -20);

        assertEquals(-5, offset, 0.0001f);
    }

    @Test
    public void calculateXOffsetDoesNotChangeWhenMovingAway() {
        AABB3 other = new AABB3(
                new Vector3(0, 25, 40),
                new Vector3(5, 35, 50)
        );

        float offset = box.calculateXOffset(other, -20);

        assertEquals(-20, offset, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // calculateYOffset()
    // -------------------------------------------------------------------------

    @Test
    public void calculateYOffsetMovingUpIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(15, 0, 35),
                new Vector3(25, 10, 45)
        );

        float offset = box.calculateYOffset(other, 20);

        assertEquals(10, offset, 0.0001f);
    }

    @Test
    public void calculateYOffsetMovingDownIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(15, 55, 35),
                new Vector3(25, 65, 45)
        );

        float offset = box.calculateYOffset(other, -20);

        assertEquals(-5, offset, 0.0001f);
    }

    @Test
    public void calculateYOffsetDoesNotChangeWhenMovingAway() {
        AABB3 other = new AABB3(
                new Vector3(15, 0, 35),
                new Vector3(25, 10, 45)
        );

        float offset = box.calculateYOffset(other, -20);

        assertEquals(-20, offset, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // calculateZOffset()
    // -------------------------------------------------------------------------

    @Test
    public void calculateZOffsetMovingPositiveIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(15, 25, 0),
                new Vector3(25, 35, 10)
        );

        float offset = box.calculateZOffset(other, 20);

        assertEquals(20, offset, 0.0001f);
    }

    @Test
    public void calculateZOffsetMovingNegativeIntoBox() {
        AABB3 other = new AABB3(
                new Vector3(15, 25, 65),
                new Vector3(25, 35, 70)
        );

        float offset = box.calculateZOffset(other, -20);

        assertEquals(-5, offset, 0.0001f);
    }

    @Test
    public void calculateZOffsetDoesNotChangeWhenMovingAway() {
        AABB3 other = new AABB3(
                new Vector3(15, 25, 0),
                new Vector3(25, 35, 10)
        );

        float offset = box.calculateZOffset(other, -20);

        assertEquals(-20, offset, 0.0001f);
    }

    @Test
    public void calculateXOffsetIgnoresBoxWithNoYOverlap() {
        AABB3 other = new AABB3(
                new Vector3(0, 70, 40),
                new Vector3(5, 80, 50)
        );

        float offset = box.calculateXOffset(other, 20);

        assertEquals(20, offset, 0.0001f);
    }
}
