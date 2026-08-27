package main.java.net.kallen.solaris.data;

import main.java.net.kallen.solaris.math.box.AABB2;
import main.java.net.kallen.solaris.math.vector.Vector2;

import java.util.*;

public class QuadTree {

    private static final int MAX_DEPTH = 64;
    private static final int CAPACITY = 4;

    private QuadTree[] children = { null, null, null, null };
    private final AABB2 bounds;
    private ArrayList<Vector2> points = new ArrayList<>();
    private final int depth;

    public QuadTree(AABB2 bounds) {
        this.bounds = bounds;
        this.depth = 1;
    }

    private QuadTree(AABB2 bounds, int depth) {
        this.bounds = bounds;
        this.depth = depth;
    }

    public void add(Vector2 newPoint) {

        if (children[0] != null) {
            addToChild(newPoint);
            return;
        }

        if (!bounds.contains(newPoint)) return;

        points.add(newPoint);
        if (points.size() >= CAPACITY && depth < MAX_DEPTH) {
            subdivide();
        }

    }

    private void subdivide() {

        Vector2 mid = bounds.middle();
        Vector2 min = bounds.min;
        Vector2 max = bounds.max;

        // Northwest
        AABB2 nw = new AABB2(
                new Vector2(min.x, mid.y),
                new Vector2(mid.x, max.y)
        );
        children[0] = new QuadTree(nw, depth + 1);

        // Northeast
        AABB2 ne = new AABB2(
                new Vector2(mid.x, mid.y),
                new Vector2(max.x, max.y)
        );
        children[1] = new QuadTree(ne, depth + 1);

        // Southwest
        AABB2 sw = new AABB2(
                new Vector2(min.x, min.y),
                new Vector2(mid.x, mid.y)
        );
        children[2] = new QuadTree(sw, depth + 1);

        // Southeast
        AABB2 se = new AABB2(
                new Vector2(mid.x, min.y),
                new Vector2(max.x, mid.y)
        );
        children[3] = new QuadTree(se, depth + 1);

        for(Vector2 point : points) {
            addToChild(point);
        }

        points.clear();
    }

    private void addToChild(Vector2 point) {
        children[getQuadrant(point)].add(point);
    }

    private int getQuadrant(Vector2 point) {
        Vector2 mid = bounds.middle();

        if (point.x < mid.x) {
            return point.y < mid.y ? 2 : 0; // SW : NW
        } else {
            return point.y < mid.y ? 3 : 1; // SE : NE
        }
    }

    public boolean search(Vector2 point) {
        if (!bounds.contains(point)) return false;

        if (children[0] == null) {
            for (Vector2 p : points) {
                if (point.equals(p)) return true;
            }
            return false;
        }

        return children[getQuadrant(point)].search(point);
    }

    public ArrayList<Vector2> query(AABB2 region) {
        ArrayList<Vector2> queried = new ArrayList<>();
        query(region, queried);
        return queried;
    }

    private void query(AABB2 region, ArrayList<Vector2> queried) {
        if (!bounds.intersects(region)) {
            return;
        }

        if (children[0] == null) {
            for (Vector2 point : points) {
                if (region.contains(point)) {
                    queried.add(point);
                }
            }
            return;
        }

        for (QuadTree child : children) {
            child.query(region, queried);
        }
    }

    private static class NearestResult {
        Vector2 point = null;
        float distanceSquared = Float.POSITIVE_INFINITY;
    }

    private static class ChildDistance {
        QuadTree child;
        float distanceSquared;

        ChildDistance(QuadTree child, float distanceSquared) {
            this.child = child;
            this.distanceSquared = distanceSquared;
        }
    }

    public Vector2 nearest(Vector2 point) {
        NearestResult result = new NearestResult();

        nearest(point, result);

        return result.point;
    }

    private void nearest(Vector2 point, NearestResult result) {

        // Leaf
        if (children[0] == null) {

            for (Vector2 p : points) {

                float distanceSquared = p.distanceSquared(point);

                if (distanceSquared < result.distanceSquared) {
                    result.point = p;
                    result.distanceSquared = distanceSquared;
                }
            }

            return;
        }

        // Has children

        List<ChildDistance> orderedChildren = new ArrayList<>();

        for (QuadTree child : children) {

            Vector2 closestPoint = child.bounds.getClosestPoint(point);

            float distanceSquared = closestPoint.distanceSquared(point);

            orderedChildren.add(
                    new ChildDistance(child, distanceSquared)
            );
        }

        orderedChildren.sort(
                Comparator.comparingDouble(
                        child -> child.distanceSquared
                )
        );

        for (ChildDistance childDistance : orderedChildren) {

            if (childDistance.distanceSquared >= result.distanceSquared) {
                break;
            }

            childDistance.child.nearest(point, result);
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        toString(builder, "");

        return builder.toString();
    }

    private void toString(StringBuilder builder, String indent) {
        builder.append(indent)
                .append("QuadTree{")
                .append("depth=").append(depth)
                .append(", bounds=").append(bounds);

        if (children[0] == null) {
            builder.append(", points=").append(points);
            builder.append("}\n");
            return;
        }

        builder.append("}\n");

        builder.append(indent).append("├── NW\n");
        children[0].toString(builder, indent + "│   ");

        builder.append(indent).append("├── NE\n");
        children[1].toString(builder, indent + "│   ");

        builder.append(indent).append("├── SW\n");
        children[2].toString(builder, indent + "│   ");

        builder.append(indent).append("└── SE\n");
        children[3].toString(builder, indent + "    ");
    }
}