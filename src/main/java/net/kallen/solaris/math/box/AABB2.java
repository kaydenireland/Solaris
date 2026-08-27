package main.java.net.kallen.solaris.math.box;

import main.java.net.kallen.solaris.math.vector.Vector2;

public class AABB2 {

    public Vector2 min;
    public Vector2 max;
    private final float EPSILON = 0.0001f;

    public AABB2(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max;
    }

    public AABB2(float minX, float minY, float maxX, float maxY) {
        this.min = new Vector2(minX, minY);
        this.max = new Vector2(maxX, maxY);
    }

    public boolean contains(Vector2 point) {
        return this.min.x <= point.x && this.max.x >= point.x
                && this.min.y <= point.y && this.max.y >= point.y;
    }

    public boolean intersects(AABB2 other) {
        return this.max.x >= other.min.x && this.min.x <= other.max.x &&
                this.max.y >= other.min.y && this.min.y <= other.max.y;
    }

    public AABB2 expand(Vector2 point) {
        float newMinX = point.x < 0 ? min.x + point.x : min.x;
        float newMinY = point.y < 0 ? min.y + point.y : min.y;
        float newMaxX = point.x > 0 ? max.x + point.x : max.x;
        float newMaxY = point.y > 0 ? max.y + point.y : max.y;

        return new AABB2(newMinX, newMinY, newMaxX, newMaxY);
    }

    public Vector2 middle() {
        return new Vector2(
                (min.x + max.x) / 2f,
                (min.y + max.y) / 2f
        );
    }

    public AABB2 offset(Vector2 point) {
        return new AABB2(min.x + point.x, min.y + point.y, max.x + point.x, max.y + point.y);
    }

    public float calculateYOffset(AABB2 other, float offsetY) {
        if (other.max.x > this.min.x && other.min.x < this.max.x) {

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

    public float calculateXOffset(AABB2 other, float offsetX) {
        if (other.max.y > this.min.y && other.min.y < this.max.y) {

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

    public Vector2 getClosestPoint(Vector2 point) {
        float closeX = Math.max(min.x, Math.min(point.x, max.x));
        float closeY = Math.max(min.y, Math.min(point.y, max.y));
        return new Vector2(closeX, closeY);
    }

    @Override
    public String toString() {
        return "AABB2{" + "min=" + min + ", max=" + max + '}';
    }

}
