package net.kallen.solaris.test.data;

import net.kallen.solaris.data.Octree;
import net.kallen.solaris.math.box.AABB3;
import net.kallen.solaris.math.vector.Vector3;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OctreeTest {

    private Octree createTree() {
        return new Octree(
                new AABB3(
                        new Vector3(0, 0, 0),
                        new Vector3(100, 100, 100)
                )
        );
    }

    @Test
    public void searchFindsInsertedPoint() {
        Octree tree = createTree();

        Vector3 point = new Vector3(25, 25, 25);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void searchDoesNotFindPointThatWasNotInserted() {
        Octree tree = createTree();

        tree.add(new Vector3(25, 25, 25));

        assertFalse(tree.search(new Vector3(75, 75, 75)));
    }

    @Test
    public void pointOutsideBoundsIsNotFound() {
        Octree tree = createTree();

        tree.add(new Vector3(25, 25, 25));

        assertFalse(tree.search(new Vector3(150, 150, 150)));
    }

    @Test
    public void treeSubdividesWhenCapacityIsReached() {
        Octree tree = createTree();

        tree.add(new Vector3(10, 10, 10));
        tree.add(new Vector3(20, 20, 20));
        tree.add(new Vector3(30, 30, 30));
        tree.add(new Vector3(40, 40, 40));

        assertTrue(tree.search(new Vector3(10, 10, 10)));
        assertTrue(tree.search(new Vector3(20, 20, 20)));
        assertTrue(tree.search(new Vector3(30, 30, 30)));
        assertTrue(tree.search(new Vector3(40, 40, 40)));
    }

    @Test
    public void pointsInDifferentOctantsCanBeFound() {
        Octree tree = createTree();

        Vector3 fnw = new Vector3(25, 75, 25);
        Vector3 fne = new Vector3(75, 75, 25);
        Vector3 fsw = new Vector3(25, 25, 25);
        Vector3 fse = new Vector3(75, 25, 25);

        Vector3 bnw = new Vector3(25, 75, 75);
        Vector3 bne = new Vector3(75, 75, 75);
        Vector3 bsw = new Vector3(25, 25, 75);
        Vector3 bse = new Vector3(75, 25, 75);

        tree.add(fnw);
        tree.add(fne);
        tree.add(fsw);
        tree.add(fse);
        tree.add(bnw);
        tree.add(bne);
        tree.add(bsw);
        tree.add(bse);

        assertTrue(tree.search(fnw));
        assertTrue(tree.search(fne));
        assertTrue(tree.search(fsw));
        assertTrue(tree.search(fse));
        assertTrue(tree.search(bnw));
        assertTrue(tree.search(bne));
        assertTrue(tree.search(bsw));
        assertTrue(tree.search(bse));
    }

    @Test
    public void queryReturnsPointsInsideRegion() {
        Octree tree = createTree();

        Vector3 p1 = new Vector3(10, 10, 10);
        Vector3 p2 = new Vector3(20, 20, 20);
        Vector3 p3 = new Vector3(80, 80, 80);

        tree.add(p1);
        tree.add(p2);
        tree.add(p3);

        AABB3 region = new AABB3(
                new Vector3(0, 0, 0),
                new Vector3(50, 50, 50)
        );

        ArrayList<Vector3> results = tree.query(region);

        assertEquals(2, results.size());
        assertTrue(results.contains(p1));
        assertTrue(results.contains(p2));
        assertFalse(results.contains(p3));
    }

    @Test
    public void queryDoesNotReturnPointsOutsideRegion() {
        Octree tree = createTree();

        Vector3 point = new Vector3(75, 75, 75);
        tree.add(point);

        AABB3 region = new AABB3(
                new Vector3(0, 0, 0),
                new Vector3(50, 50, 50)
        );

        ArrayList<Vector3> results = tree.query(region);

        assertTrue(results.isEmpty());
    }

    @Test
    public void queryWorksAcrossMultipleOctants() {
        Octree tree = createTree();

        Vector3 fnw = new Vector3(25, 75, 25);
        Vector3 fne = new Vector3(75, 75, 25);
        Vector3 fsw = new Vector3(25, 25, 25);
        Vector3 fse = new Vector3(75, 25, 25);

        Vector3 bnw = new Vector3(25, 75, 75);
        Vector3 bne = new Vector3(75, 75, 75);
        Vector3 bsw = new Vector3(25, 25, 75);
        Vector3 bse = new Vector3(75, 25, 75);

        tree.add(fnw);
        tree.add(fne);
        tree.add(fsw);
        tree.add(fse);
        tree.add(bnw);
        tree.add(bne);
        tree.add(bsw);
        tree.add(bse);

        AABB3 region = new AABB3(
                new Vector3(0, 0, 0),
                new Vector3(100, 100, 100)
        );

        ArrayList<Vector3> results = tree.query(region);

        assertEquals(8, results.size());

        assertTrue(results.contains(fnw));
        assertTrue(results.contains(fne));
        assertTrue(results.contains(fsw));
        assertTrue(results.contains(fse));
        assertTrue(results.contains(bnw));
        assertTrue(results.contains(bne));
        assertTrue(results.contains(bsw));
        assertTrue(results.contains(bse));
    }

    @Test
    public void pointsOutsideTreeAreNotAdded() {
        Octree tree = createTree();

        Vector3 outside = new Vector3(150, 150, 150);
        tree.add(outside);

        assertFalse(tree.search(outside));

        AABB3 region = new AABB3(
                new Vector3(100, 100, 100),
                new Vector3(200, 200, 200)
        );

        assertTrue(tree.query(region).isEmpty());
    }

    @Test
    public void pointOnAllMidpointsIsSearchable() {
        Octree tree = createTree();

        Vector3 point = new Vector3(50, 50, 50);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointOnXMidpointIsSearchable() {
        Octree tree = createTree();

        Vector3 point = new Vector3(50, 25, 25);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointOnYMidpointIsSearchable() {
        Octree tree = createTree();

        Vector3 point = new Vector3(25, 50, 25);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointOnZMidpointIsSearchable() {
        Octree tree = createTree();

        Vector3 point = new Vector3(25, 25, 50);
        tree.add(point);

        assertTrue(tree.search(point));
    }

    @Test
    public void pointsOnAllMidpointPlanesAreSearchable() {
        Octree tree = createTree();

        Vector3 xMid = new Vector3(50, 25, 25);
        Vector3 yMid = new Vector3(25, 50, 25);
        Vector3 zMid = new Vector3(25, 25, 50);

        tree.add(xMid);
        tree.add(yMid);
        tree.add(zMid);

        assertTrue(tree.search(xMid));
        assertTrue(tree.search(yMid));
        assertTrue(tree.search(zMid));
    }

    @Test
    public void pointsOnMidpointsRemainSearchableAfterSubdivision() {
        Octree tree = createTree();

        Vector3 p1 = new Vector3(50, 50, 50);
        Vector3 p2 = new Vector3(50, 25, 25);
        Vector3 p3 = new Vector3(25, 50, 25);
        Vector3 p4 = new Vector3(25, 25, 50);

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
        Octree tree = createTree();

        Vector3 a = new Vector3(10, 10, 10);
        Vector3 b = new Vector3(50, 50, 50);
        Vector3 c = new Vector3(80, 80, 80);

        tree.add(a);
        tree.add(b);
        tree.add(c);

        Vector3 result = tree.nearest(new Vector3(48, 48, 48));

        assertEquals(b, result);
    }

    @Test
    public void nearestWorksAcrossOctants() {
        Octree tree = createTree();

        Vector3 fnw = new Vector3(25, 75, 25);
        Vector3 fne = new Vector3(75, 75, 25);
        Vector3 fsw = new Vector3(25, 25, 25);
        Vector3 fse = new Vector3(75, 25, 25);

        Vector3 bnw = new Vector3(25, 75, 75);
        Vector3 bne = new Vector3(75, 75, 75);
        Vector3 bsw = new Vector3(25, 25, 75);
        Vector3 bse = new Vector3(75, 25, 75);

        tree.add(fnw);
        tree.add(fne);
        tree.add(fsw);
        tree.add(fse);
        tree.add(bnw);
        tree.add(bne);
        tree.add(bsw);
        tree.add(bse);

        Vector3 result = tree.nearest(new Vector3(70, 70, 70));

        assertEquals(bne, result);
    }

    @Test
    public void nearestCanFindPointInDifferentOctant() {
        Octree tree = createTree();

        Vector3 a = new Vector3(49, 51, 51);
        Vector3 b = new Vector3(51, 51, 51);

        tree.add(a);
        tree.add(b);

        Vector3 result = tree.nearest(new Vector3(50, 50, 50));

        // Both are equally close. Either is a valid result.
        assertTrue(result.equals(a) || result.equals(b));
    }

    @Test
    public void nearestWorksAfterSubdivision() {
        Octree tree = createTree();

        Vector3 closest = new Vector3(75, 75, 75);

        tree.add(new Vector3(5, 5, 5));
        tree.add(new Vector3(95, 5, 5));
        tree.add(new Vector3(5, 95, 5));
        tree.add(closest);

        Vector3 result = tree.nearest(new Vector3(70, 70, 70));

        assertEquals(closest, result);
    }

    @Test
    public void nearestReturnsNullForEmptyTree() {
        Octree tree = createTree();

        assertNull(tree.nearest(new Vector3(50, 50, 50)));
    }

    @Test
    public void pointsOnAllEightOctantsAreSearchableAfterSubdivision() {
        Octree tree = createTree();

        Vector3[] points = {
                new Vector3(25, 75, 25), // FNW
                new Vector3(75, 75, 25), // FNE
                new Vector3(25, 25, 25), // FSW
                new Vector3(75, 25, 25), // FSE
                new Vector3(25, 75, 75), // BNW
                new Vector3(75, 75, 75), // BNE
                new Vector3(25, 25, 75), // BSW
                new Vector3(75, 25, 75)  // BSE
        };

        for (Vector3 point : points) {
            tree.add(point);
        }

        for (Vector3 point : points) {
            assertTrue(tree.search(point));
        }
    }
}