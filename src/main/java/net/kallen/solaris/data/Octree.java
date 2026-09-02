package net.kallen.solaris.data;

import net.kallen.solaris.math.box.AABB3;
import net.kallen.solaris.math.vector.Vector3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Octree {

    private static final int MAX_DEPTH = 64;
    private static final int CAPACITY = 4;

    private Octree[] children = { null, null, null, null, null, null, null, null };
    private final AABB3 bounds;
    private ArrayList<Vector3> points = new ArrayList<>();
    private final int depth;

    public Octree(AABB3 bounds) {
        this.bounds = bounds;
        this.depth = 1;
    }

    private Octree(AABB3 bounds, int depth) {
        this.bounds = bounds;
        this.depth = depth;
    }

    public void add(Vector3 newPoint) {

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

        Vector3 mid = bounds.center();
        Vector3 min = bounds.min;
        Vector3 max = bounds.max;

        // Front Northwest
        AABB3 fnw = new AABB3(
                new Vector3(min.x, mid.y, min.z),
                new Vector3(mid.x, max.y, mid.z)
        );
        children[0] = new Octree(fnw, depth + 1);

        // Front Northeast
        AABB3 fne = new AABB3(
                new Vector3(mid.x, mid.y, min.z),
                new Vector3(max.x, max.y, mid.z)
        );
        children[1] = new Octree(fne, depth + 1);

        // Front Southwest
        AABB3 fsw = new AABB3(
                new Vector3(min.x, min.y, min.z),
                new Vector3(mid.x, mid.y, mid.z)
        );
        children[2] = new Octree(fsw, depth + 1);

        // Front Southeast
        AABB3 fse = new AABB3(
                new Vector3(mid.x, min.y, min.z),
                new Vector3(max.x, mid.y, mid.z)
        );
        children[3] = new Octree(fse, depth + 1);

        // Back Northwest
        AABB3 bnw = new AABB3(
                new Vector3(min.x, mid.y, mid.z),
                new Vector3(mid.x, max.y, max.z)
        );
        children[4] = new Octree(bnw, depth + 1);

        // Back Northeast
        AABB3 bne = new AABB3(
                new Vector3(mid.x, mid.y, mid.z),
                new Vector3(max.x, max.y, max.z)
        );
        children[5] = new Octree(bne, depth + 1);

        // Back Southwest
        AABB3 bsw = new AABB3(
                new Vector3(min.x, min.y, mid.z),
                new Vector3(mid.x, mid.y, max.z)
        );
        children[6] = new Octree(bsw, depth + 1);

        // Southeast
        AABB3 bse = new AABB3(
                new Vector3(mid.x, min.y, mid.z),
                new Vector3(max.x, mid.y, max.z)
        );
        children[7] = new Octree(bse, depth + 1);

        for(Vector3 point : points) {
            addToChild(point);
        }

        points.clear();
    }

    private void addToChild(Vector3 point) {
        children[getOctant(point)].add(point);
    }

    private int getOctant(Vector3 point) {
        Vector3 mid = bounds.center();

        if (point.x < mid.x) {
            if (point.z < mid.z) return point.y < mid.y ? 2 : 0; // FSW : FNW
            else return point.y < mid.y ? 6 : 4; // BSW : BNW
        } else {
            if (point.z < mid.z) return point.y < mid.y ? 3 : 1; // FSE : FNE
            else return point.y < mid.y ? 7 : 5; // BSE : BNE
        }
    }

    public boolean search(Vector3 point) {
        if (!bounds.contains(point)) return false;

        if (children[0] == null) {
            for (Vector3 p : points) {
                if (point.equals(p)) return true;
            }
            return false;
        }

        return children[getOctant(point)].search(point);
    }

    public ArrayList<Vector3> query(AABB3 region) {
        ArrayList<Vector3> queried = new ArrayList<>();
        query(region, queried);
        return queried;
    }

    private void query(AABB3 region, ArrayList<Vector3> queried) {
        if (!bounds.intersects(region)) {
            return;
        }

        if (children[0] == null) {
            for (Vector3 point : points) {
                if (region.contains(point)) {
                    queried.add(point);
                }
            }
            return;
        }

        for (Octree child : children) {
            child.query(region, queried);
        }
    }

    private static class NearestResult {
        Vector3 point = null;
        float distanceSquared = Float.POSITIVE_INFINITY;
    }

    private static class ChildDistance {
        Octree child;
        float distanceSquared;

        ChildDistance(Octree child, float distanceSquared) {
            this.child = child;
            this.distanceSquared = distanceSquared;
        }
    }

    public Vector3 nearest(Vector3 point) {
        NearestResult result = new NearestResult();

        nearest(point, result);

        return result.point;
    }

    private void nearest(Vector3 point, NearestResult result) {

        // Leaf
        if (children[0] == null) {

            for (Vector3 p : points) {

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

        for (Octree child : children) {

            Vector3 closestPoint = child.bounds.getClosestPoint(point);

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
                .append("Octree{")
                .append("depth=").append(depth)
                .append(", bounds=").append(bounds);

        if (children[0] == null) {
            builder.append(", points=").append(points);
            builder.append("}\n");
            return;
        }

        builder.append("}\n");

        builder.append(indent).append("├── FNW\n");
        children[0].toString(builder, indent + "│   ");

        builder.append(indent).append("├── FNE\n");
        children[1].toString(builder, indent + "│   ");

        builder.append(indent).append("├── FSW\n");
        children[2].toString(builder, indent + "│   ");

        builder.append(indent).append("├── FSE\n");
        children[3].toString(builder, indent + "│   ");

        builder.append(indent).append("├── BNW\n");
        children[4].toString(builder, indent + "│   ");

        builder.append(indent).append("├── BNE\n");
        children[5].toString(builder, indent + "│   ");

        builder.append(indent).append("├── BSW\n");
        children[6].toString(builder, indent + "│   ");

        builder.append(indent).append("└── BSE\n");
        children[7].toString(builder, indent + "    ");
    }
}