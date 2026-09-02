package net.kallen.solaris.test.data;

import net.kallen.solaris.math.box.AABB2;
import net.kallen.solaris.math.vector.Vector2;
import net.kallen.solaris.data.QuadTree;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class QuadTreeTest {

    private QuadTree createTree() {
        return new QuadTree(
                new AABB2(
                        new Vector2(0, 0),
                        new Vector2(100, 100)
                )
        );
    }

    @Test
    public void searchFindsInsertedPoint() {
        QuadTree tree = createTree();

        Vector2 point = new Vector2(25, 25);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void searchDoesNotFindPointThatWasNotInserted() {
        QuadTree tree = createTree();

        tree.add(new Vector2(25, 25));

        assertFalse(tree.search(new Vector2(75, 75)));
    }

    @Test
    public void pointOutsideBoundsIsNotFound() {
        QuadTree tree = createTree();

        tree.add(new Vector2(25, 25));

        assertFalse(tree.search(new Vector2(150, 150)));
    }

    @Test
    public void treeSubdividesWhenCapacityIsReached() {
        QuadTree tree = createTree();

        tree.add(new Vector2(10, 10));
        tree.add(new Vector2(20, 20));
        tree.add(new Vector2(30, 30));
        tree.add(new Vector2(40, 40));

        // All four points should still be searchable after subdivision.
        assertTrue(tree.search(new Vector2(10, 10)));
        assertTrue(tree.search(new Vector2(20, 20)));
        assertTrue(tree.search(new Vector2(30, 30)));
        assertTrue(tree.search(new Vector2(40, 40)));
    }

    @Test
    public void pointsInDifferentQuadrantsCanBeFound() {
        QuadTree tree = createTree();

        Vector2 nw = new Vector2(25, 75);
        Vector2 ne = new Vector2(75, 75);
        Vector2 sw = new Vector2(25, 25);
        Vector2 se = new Vector2(75, 25);

        tree.add(nw);
        tree.add(ne);
        tree.add(sw);
        tree.add(se);

        assertTrue(tree.search(nw));
        assertTrue(tree.search(ne));
        assertTrue(tree.search(sw));
        assertTrue(tree.search(se));
    }

    @Test
    public void queryReturnsPointsInsideRegion() {
        QuadTree tree = createTree();

        Vector2 p1 = new Vector2(10, 10);
        Vector2 p2 = new Vector2(20, 20);
        Vector2 p3 = new Vector2(80, 80);

        tree.add(p1);
        tree.add(p2);
        tree.add(p3);

        AABB2 region = new AABB2(
                new Vector2(0, 0),
                new Vector2(50, 50)
        );

        ArrayList<Vector2> results = tree.query(region);

        assertEquals(2, results.size());
        assertTrue(results.contains(p1));
        assertTrue(results.contains(p2));
        assertFalse(results.contains(p3));
    }

    @Test
    public void queryDoesNotReturnPointsOutsideRegion() {
        QuadTree tree = createTree();

        Vector2 point = new Vector2(75, 75);
        tree.add(point);

        AABB2 region = new AABB2(
                new Vector2(0, 0),
                new Vector2(50, 50)
        );

        ArrayList<Vector2> results = tree.query(region);

        assertTrue(results.isEmpty());
    }

    @Test
    public void queryWorksAcrossMultipleQuadrants() {
        QuadTree tree = createTree();

        Vector2 nw = new Vector2(25, 75);
        Vector2 ne = new Vector2(75, 75);
        Vector2 sw = new Vector2(25, 25);
        Vector2 se = new Vector2(75, 25);

        tree.add(nw);
        tree.add(ne);
        tree.add(sw);
        tree.add(se);

        AABB2 region = new AABB2(
                new Vector2(0, 0),
                new Vector2(100, 100)
        );

        ArrayList<Vector2> results = tree.query(region);

        assertEquals(4, results.size());
        assertTrue(results.contains(nw));
        assertTrue(results.contains(ne));
        assertTrue(results.contains(sw));
        assertTrue(results.contains(se));
    }

    @Test
    public void pointsOutsideTreeAreNotAdded() {
        QuadTree tree = createTree();

        Vector2 outside = new Vector2(150, 150);
        tree.add(outside);

        assertFalse(tree.search(outside));

        AABB2 region = new AABB2(
                new Vector2(100, 100),
                new Vector2(200, 200)
        );

        assertTrue(tree.query(region).isEmpty());
    }

    @Test
    public void pointOnBothMidpointsIsSearchable() {
        QuadTree tree = createTree();

        Vector2 point = new Vector2(50, 50);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointOnVerticalMidpointIsSearchable() {
        QuadTree tree = createTree();

        Vector2 point = new Vector2(50, 25);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointOnHorizontalMidpointIsSearchable() {
        QuadTree tree = createTree();

        Vector2 point = new Vector2(25, 50);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointsOnAllQuadrantBoundariesAreSearchable() {
        QuadTree tree = createTree();

        Vector2 nwBoundary = new Vector2(25, 50);
        Vector2 neBoundary = new Vector2(50, 50);
        Vector2 swBoundary = new Vector2(25, 25);
        Vector2 seBoundary = new Vector2(50, 25);

        tree.add(nwBoundary);
        tree.add(neBoundary);
        tree.add(swBoundary);
        tree.add(seBoundary);

        assertTrue(tree.search(nwBoundary));
        assertTrue(tree.search(neBoundary));
        assertTrue(tree.search(swBoundary));
        assertTrue(tree.search(seBoundary));
    }

    @Test
    public void pointsOnMidpointsRemainSearchableAfterSubdivision() {
        QuadTree tree = createTree();

        Vector2 p1 = new Vector2(50, 50);
        Vector2 p2 = new Vector2(50, 25);
        Vector2 p3 = new Vector2(25, 50);
        Vector2 p4 = new Vector2(25, 25);

        tree.add(p1);
        tree.add(p2);
        tree.add(p3);
        tree.add(p4);

        assertTrue(tree.search(p1));
        assertTrue(tree.search(p2));
        assertTrue(tree.search(p3));
        assertTrue(tree.search(p4));
    }

    @Test
    public void nearestReturnsClosestPoint() {
        QuadTree tree = createTree();

        Vector2 a = new Vector2(10, 10);
        Vector2 b = new Vector2(50, 50);
        Vector2 c = new Vector2(80, 80);

        tree.add(a);
        tree.add(b);
        tree.add(c);

        Vector2 result = tree.nearest(new Vector2(48, 48));

        assertEquals(b, result);
    }

    @Test
    public void nearestWorksAcrossQuadrants() {
        QuadTree tree = createTree();

        Vector2 nw = new Vector2(25, 75);
        Vector2 ne = new Vector2(75, 75);
        Vector2 sw = new Vector2(25, 25);
        Vector2 se = new Vector2(75, 25);

        tree.add(nw);
        tree.add(ne);
        tree.add(sw);
        tree.add(se);

        Vector2 result = tree.nearest(new Vector2(60, 60));

        assertEquals(ne, result);
    }

    @Test
    public void nearestCanFindPointInDifferentQuadrant() {
        QuadTree tree = createTree();

        Vector2 nw = new Vector2(49, 51);
        Vector2 ne = new Vector2(51, 51);

        tree.add(nw);
        tree.add(ne);

        Vector2 result = tree.nearest(new Vector2(50, 50));

        // Both are equally close. Either is a valid result.
        assertTrue(result.equals(nw) || result.equals(ne));
    }

    @Test
    public void nearestWorksAfterSubdivision() {
        QuadTree tree = createTree();

        Vector2 closest = new Vector2(75, 75);

        tree.add(new Vector2(5, 5));
        tree.add(new Vector2(95, 5));
        tree.add(new Vector2(5, 95));
        tree.add(closest);

        Vector2 result = tree.nearest(new Vector2(70, 70));

        assertEquals(closest, result);
    }

    @Test
    public void nearestReturnsNullForEmptyTree() {
        QuadTree tree = createTree();

        assertNull(tree.nearest(new Vector2(50, 50)));
    }

}
