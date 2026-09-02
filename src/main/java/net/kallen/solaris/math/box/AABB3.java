package net.kallen.solaris.math.box;

import net.kallen.solaris.math.vector.Vector3;

public class AABB3 {
    public Vector3 min;
    public Vector3 max;
    private final float EPSILON = 0.0001f;

    public AABB3(Vector3 min, Vector3 max) {
        this.min = min;
        this.max = max;
    }

    public AABB3(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        this.min = new Vector3(minX, minY, minZ);
        this.max = new Vector3(maxX, maxY, maxZ);
    }

    public boolean contains(Vector3 point) {
        return this.min.x <= point.x && this.max.x >= point.x
                && this.min.y <= point.y && this.max.y >= point.y
                && this.min.z <= point.z && this.max.z >= point.z;
    }

    public boolean intersects(AABB3 other) {
        return this.max.x >= other.min.x && this.min.x <= other.max.x &&
                this.max.y >= other.min.y && this.min.y <= other.max.y &&
                this.max.z >= other.min.z && this.min.z <= other.max.z;
    }

    public AABB3 expand(Vector3 point) {
        float newMinX = point.x < 0 ? min.x + point.x : min.x;
        float newMinY = point.y < 0 ? min.y + point.y : min.y;
        float newMinZ = point.z < 0 ? min.z + point.z : min.z;
        float newMaxX = point.x > 0 ? max.x + point.x : max.x;
        float newMaxY = point.y > 0 ? max.y + point.y : max.y;
        float newMaxZ = point.z > 0 ? max.z + point.z : max.z;

        return new AABB3(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    public Vector3 center() {
        return new Vector3(
                (min.x + max.x) / 2f,
                (min.y + max.y) / 2f,
                (min.z + max.z) / 2f
        );
    }

    public AABB3 offset(Vector3 point) {
        return new AABB3(min.x + point.x, min.y + point.y, min.z + point.z, max.x + point.x, max.y + point.y, max.z + point.z);
    }

    public float calculateYOffset(AABB3 other, float offsetY) {
        if (other.max.x > this.min.x && other.min.x < this.max.x &&
                other.max.z > this.min.z && other.min.z < this.max.z) {

            if (offsetY < 0 && other.min.y >= this.max.y - EPSILON) {
                float diff = this.max.y - other.min.y;  // negative value
                if (diff > offsetY) {
                    offsetY = diff;
                }
            }
            else if (offsetY > 0 && other.max.y <= this.min.y + EPSILON) {
                float diff = this.min.y - other.max.y;  // positive value
                if (diff < offsetY) {
                    offsetY = diff;
                }
            }
        }
        return offsetY;
    }

    public float calculateXOffset(AABB3 other, float offsetX) {
        if (other.max.y > this.min.y && other.min.y < this.max.y &&
                other.max.z > this.min.z && other.min.z < this.max.z) {

            if (offsetX < 0 && other.min.x >= this.max.x - EPSILON) {
                float diff = this.max.x - other.min.x;
                if (diff > offsetX) {
                    offsetX = diff;
                }
            } else if (offsetX > 0 && other.max.x <= this.min.x + EPSILON) {
                float diff = this.min.x - other.max.x;
                if (diff < offsetX) {
                    offsetX = diff;
                }
            }
        }
        return offsetX;
    }

    public float calculateZOffset(AABB3 other, float offsetZ) {
        if (other.max.x > this.min.x && other.min.x < this.max.x &&
                other.max.y > this.min.y && other.min.y < this.max.y) {

            if (offsetZ < 0 && other.min.z >= this.max.z - EPSILON) {
                float diff = this.max.z - other.min.z;
                if (diff > offsetZ) {
                    offsetZ = diff;
                }
            } else if (offsetZ > 0 && other.max.z <= this.min.z + EPSILON) {
                float diff = this.min.z - other.max.z;
                if (diff < offsetZ) {
                    offsetZ = diff;
                }
            }
        }
        return offsetZ;
    }

    public Vector3 getClosestPoint(Vector3 point) {
        float closeX = Math.max(min.x, Math.min(point.x, max.x));
        float closeY = Math.max(min.y, Math.min(point.y, max.y));
        float closeZ = Math.max(min.z, Math.min(point.z, max.z));
        return new Vector3(closeX, closeY, closeZ);
    }

    @Override
    public String toString() {
        return "AABB3{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}