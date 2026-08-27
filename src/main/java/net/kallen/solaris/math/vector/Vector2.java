package main.java.net.kallen.solaris.math.vector;

public class Vector2 implements VectorLike<Vector2> {
    public float x, y;

    public final static Vector2 ZERO = new Vector2(0, 0);
    public final static Vector2 ONE = new Vector2(0, 0);
    public final static Vector2 UNIT_X = new Vector2(1, 0);
    public final static Vector2 UNIT_Y = new Vector2(0, 1);

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector3 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 multiply(Vector2 other) {
        return new Vector2(this.x * other.x, this.y * other.y);
    }

    public Vector2 divide(Vector2 other) {
        return new Vector2(this.x / other.x, this.y / other.y);
    }

    public Vector2 scale(float s) {
        return new Vector2(this.x * s, this.y * s);
    }

    public Vector2 negate() {
        return new Vector2(-this.x, -this.y);
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Vector2 normalize() {
        float len = length();
        if (len == 0) return new Vector2(0, 0);
        return divide(new Vector2(len, len));
    }

    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public float distance(Vector2 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;

        return (float) Math.sqrt(x*x + y*y);
    }

    public float distanceSquared(Vector2 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;

        return (x*x + y*y);
    }

    public static float distance(Vector2 vec1, Vector2 vec2) {
        float x = vec1.x - vec2.x;
        float y = vec1.y - vec2.y;

        return (float) Math.sqrt(x*x + y*y);
    }

    public static float distanceSquared(Vector2 vec1, Vector2 vec2) {
        float x = vec1.x - vec2.x;
        float y = vec1.y - vec2.y;

        return (x*x + y*y);
    }

    public Vector2 center(Vector2 other) {
        float nx = this.x + other.x;
        float ny = this.y + other.y;

        return new Vector2(nx /2, ny /2);
    }

    public static Vector2 center(Vector2 vec1, Vector2 vec2) {
        float nx = vec1.x + vec2.x;
        float ny = vec1.y + vec2.y;

        return new Vector2(nx /2, ny /2);
    }

    public Vector2 average(Vector2... vecs) {
        float x = this.x;
        float y = this.y;

        for(Vector2 vec : vecs) {
            x += vec.x;
            y += vec.y;
        }

        x /= vecs.length;
        y /= vecs.length;

        return new Vector2(x, y);
    }

    public Vector2 lerp(Vector2 other, float t) {
        return new Vector2(
                this.x + (other.x - this.x * t),
                this.y + (other.y - this.y) * t
        );
    }

    public Vector2 min(Vector2 other) {
        return new Vector2(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2 max(Vector2 other) {
        return new Vector2(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2 clamp(Vector2 min, Vector2 max) {
        return new Vector2(
                Math.min(Math.max(this.x, min.x), max.x),
                Math.min(Math.max(this.y, min.y), max.y)
        );
    }

    public Vector2 clamp(float min, float max) {
        return new Vector2(
                Math.min(Math.max(this.x, min), max),
                Math.min(Math.max(this.y, min), max)
        );
    }

    public Vector2 abs() {
        return new Vector2(Math.abs(this.x), Math.abs(this.y));
    }

    public Vector2 rotate(float radians) {

        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        return new Vector2(
                this.x * cos - this.y * sin,
                this.x * sin + this.y * cos
        );
    }

    public Vector2 perpendicular() {
        return new Vector2(
                -this.y,
                this.x
        );
    }

    public Vector2 project(Vector2 onto) {
        float ontoLenSq = onto.dot(onto);
        if (ontoLenSq == 0) return new Vector2(0, 0);
        return onto.scale(this.dot(onto) / ontoLenSq);
    }

    public Vector2 reflect(Vector2 normal) {
        return this.subtract(normal.scale(2 * this.dot(normal)));
    }

    public float angle(Vector2 other) {
        float lenProduct = this.length() * other.length();
        if (lenProduct == 0) return 0f;
        float cos = Math.max(-1f, Math.min(1f, this.dot(other) / lenProduct));
        return (float) Math.acos(cos);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector2 other = (Vector2) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        return result;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }

}