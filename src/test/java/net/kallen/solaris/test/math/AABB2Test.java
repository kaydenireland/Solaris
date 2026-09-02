package net.kallen.solaris.test.math;


import net.kallen.solaris.math.box.AABB2;
import net.kallen.solaris.math.vector.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class AABB2Test {

    private final AABB2 box = new AABB2(
            new Vector2(10, 20),
            new Vector2(30, 40)
    );

    // -------------------------------------------------------------------------
    // contains()
    // -------------------------------------------------------------------------

    @Test
    public void containsPointInsideBox() {
        assertTrue(box.contains(new Vector2(20, 30)));
    }

    @Test
    public void containsPointOnBoundary() {
        assertTrue(box.contains(new Vector2(10, 20)));
        assertTrue(box.contains(new Vector2(30, 40)));
        assertTrue(box.contains(new Vector2(10, 30)));
        assertTrue(box.contains(new Vector2(30, 30)));
        assertTrue(box.contains(new Vector2(20, 20)));
        assertTrue(box.contains(new Vector2(20, 40)));
    }

    @Test
    public void doesNotContainPointOutsideBox() {
        assertFalse(box.contains(new Vector2(9, 30)));
        assertFalse(box.contains(new Vector2(31, 30)));
        assertFalse(box.contains(new Vector2(20, 19)));
        assertFalse(box.contains(new Vector2(20, 41)));
    }

    // -------------------------------------------------------------------------
    // intersects()
    // -------------------------------------------------------------------------

    @Test
    public void intersectsOverlappingBox() {
        AABB2 other = new AABB2(
                new Vector2(20, 30),
                new Vector2(40, 50)
        );

        assertTrue(box.intersects(other));
    }

    @Test
    public void intersectsBoxContainedInside() {
        AABB2 other = new AABB2(
                new Vector2(15, 25),
                new Vector2(25, 35)
        );

        assertTrue(box.intersects(other));
    }

    @Test
    public void doesNotIntersectSeparatedBox() {
        AABB2 other = new AABB2(
                new Vector2(40, 50),
                new Vector2(60, 70)
        );

        assertFalse(box.intersects(other));
    }

    @Test
    public void intersectsTouchingBoundary() {
        AABB2 other = new AABB2(
                new Vector2(30, 25),
                new Vector2(50, 35)
        );

        assertTrue(box.intersects(other));
    }

    // -------------------------------------------------------------------------
    // expand()
    // -------------------------------------------------------------------------

    @Test
    public void expandPositiveX() {
        AABB2 result = box.expand(new Vector2(5, 0));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(35, result.max.x, 0.0001f);
        assertEquals(40, result.max.y, 0.0001f);
    }

    @Test
    public void expandNegativeX() {
        AABB2 result = box.expand(new Vector2(-5, 0));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.max.x, 0.0001f);
        assertEquals(40, result.max.y, 0.0001f);
    }

    @Test
    public void expandPositiveY() {
        AABB2 result = box.expand(new Vector2(0, 5));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.max.x, 0.0001f);
        assertEquals(45, result.max.y, 0.0001f);
    }

    @Test
    public void expandNegativeY() {
        AABB2 result = box.expand(new Vector2(0, -5));

        assertEquals(10, result.min.x, 0.0001f);
        assertEquals(15, result.min.y, 0.0001f);
        assertEquals(30, result.max.x, 0.0001f);
        assertEquals(40, result.max.y, 0.0001f);
    }

    @Test
    public void expandBothAxes() {
        AABB2 result = box.expand(new Vector2(-5, 10));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(20, result.min.y, 0.0001f);
        assertEquals(30, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // middle()
    // -------------------------------------------------------------------------

    @Test
    public void middleReturnsCenterOfBox() {
        Vector2 middle = box.middle();

        assertEquals(20, middle.x, 0.0001f);
        assertEquals(30, middle.y, 0.0001f);
    }

    @Test
    public void middleWorksWithNegativeCoordinates() {
        AABB2 negativeBox = new AABB2(
                new Vector2(-20, -40),
                new Vector2(10, 20)
        );

        Vector2 middle = negativeBox.middle();

        assertEquals(-5, middle.x, 0.0001f);
        assertEquals(-10, middle.y, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // offset()
    // -------------------------------------------------------------------------

    @Test
    public void offsetMovesBoxPositive() {
        AABB2 result = box.offset(new Vector2(5, 10));

        assertEquals(15, result.min.x, 0.0001f);
        assertEquals(30, result.min.y, 0.0001f);
        assertEquals(35, result.max.x, 0.0001f);
        assertEquals(50, result.max.y, 0.0001f);
    }

    @Test
    public void offsetMovesBoxNegative() {
        AABB2 result = box.offset(new Vector2(-5, -10));

        assertEquals(5, result.min.x, 0.0001f);
        assertEquals(10, result.min.y, 0.0001f);
        assertEquals(25, result.max.x, 0.0001f);
        assertEquals(30, result.max.y, 0.0001f);
    }

    @Test
    public void offsetDoesNotChangeOriginalBox() {
        AABB2 result = box.offset(new Vector2(5, 10));

        assertEquals(10, box.min.x, 0.0001f);
        assertEquals(20, box.min.y, 0.0001f);
        assertEquals(30, box.max.x, 0.0001f);
        assertEquals(40, box.max.y, 0.0001f);

        assertNotSame(box, result);
    }

    // -------------------------------------------------------------------------
    // calculateXOffset()
    // -------------------------------------------------------------------------

    @Test
    public void calculateXOffsetMovingRightIntoBox() {
        AABB2 other = new AABB2(
                new Vector2(0, 25),
                new Vector2(10, 35)
        );

        float offset = box.calculateXOffset(other, 20);

        assertEquals(0, offset, 0.0001f);
    }

    @Test
    public void calculateXOffsetMovingLeftIntoBox() {
        AABB2 other = new AABB2(
                new Vector2(30, 25),
                new Vector2(40, 35)
        );

        float offset = box.calculateXOffset(other, -20);

        assertEquals(0, offset, 0.0001f);
    }

    @Test
    public void calculateXOffsetDoesNotChangeWhenMovingAway() {
        AABB2 other = new AABB2(
                new Vector2(0, 25),
                new Vector2(10, 35)
        );

        float offset = box.calculateXOffset(other, -20);

        assertEquals(-20, offset, 0.0001f);
    }

    // -------------------------------------------------------------------------
    // calculateYOffset()
    // -------------------------------------------------------------------------

    @Test
    public void calculateYOffsetMovingUpIntoBox() {
        AABB2 other = new AABB2(
                new Vector2(15, 0),
                new Vector2(25, 10)
        );

        float offset = box.calculateYOffset(other, 20);

        assertEquals(10, offset, 0.0001f);
    }

    @Test
    public void calculateYOffsetMovingDownIntoBox() {
        AABB2 other = new AABB2(
                new Vector2(15, 45),
                new Vector2(25, 55)
        );

        float offset = box.calculateYOffset(other, -20);

        assertEquals(-5, offset, 0.0001f);
    }

    @Test
    public void calculateYOffsetDoesNotChangeWhenMovingAway() {
        AABB2 other = new AABB2(
                new Vector2(15, 0),
                new Vector2(25, 10)
        );

        float offset = box.calculateYOffset(other, -20);

        assertEquals(-20, offset, 0.0001f);
    }
}