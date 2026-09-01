package main.java.net.kallen.solaris.math.vector;

public class Vector3 implements VectorLike<Vector3> {
    public float x, y, z;

    public final static Vector3 ZERO = new Vector3(0, 0, 0);
    public final static Vector3 ONE = new Vector3(1, 1, 1);
    public final static Vector3 UNIT_X = new Vector3(1, 0, 0);
    public final static Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public final static Vector3 UNIT_Z = new Vector3(0, 0, 1);

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public Vector3(Vector2 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = 0;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public static Vector2 get2D(Vector3 vector) {
        return new Vector2(vector.x, vector.y);
    }

    public static Vector3 to3D(Vector2 vector) {
        return new Vector3(vector.x, vector.y, 0);
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 multiply(Vector3 other) {
        return new Vector3(this.x * other.x, this.y * other.y, this.z * other.z);
    }

    public Vector3 divide(Vector3 other) {
        return new Vector3(this.x / other.x, this.y / other.y, this.z / other.z);
    }

    public Vector3 scale(float s) {
        return new Vector3(this.x * s, this.y * s, this.z * s);
    }

    public Vector3 negate() {
        return new Vector3(-this.x, -this.y, -this.z);
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3 normalize() {
        float len = length();
        if (len == 0) return new Vector3(0, 0, 0);
        return divide(new Vector3(len, len, len));
    }

    public float dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.y * other.z - this.z * other.y,
                this.z * other.x - this.x * other.z,
                this.x * other.y - this.y * other.x
        );
    }

    // Vector3-only: no 2D equivalent
    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        return new Vector3(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }

    public float distance(Vector3 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;
        float z = this.z - other.z;

        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public float distanceSquared(Vector3 other) {
        float x = this.x - other.x;
        float y = this.y - other.y;
        float z = this.z - other.z;

        return (x*x + y*y + z*z);
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        float x = vec1.x - vec2.x;
        float y = vec1.y - vec2.y;
        float z = vec1.z - vec2.z;

        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public static float distanceSquared(Vector3 vec1, Vector3 vec2) {
        float x = vec1.x - vec2.x;
        float y = vec1.y - vec2.y;
        float z = vec1.z - vec2.z;

        return (x*x + y*y + z*z);
    }

    public Vector3 center(Vector3 other) {
        float nx = this.x + other.x;
        float ny = this.y + other.y;
        float nz = this.z + other.z;

        return new Vector3(nx / 2, ny / 2, nz / 2);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        float nx = vec1.x + vec2.x;
        float ny = vec1.y + vec2.y;
        float nz = vec1.z + vec2.z;

        return new Vector3(nx / 2, ny / 2, nz / 2);
    }

    public Vector3 average(Vector3... vecs) {
        float x = this.x;
        float y = this.y;
        float z = this.z;

        for (Vector3 vec : vecs) {
            x += vec.x;
            y += vec.y;
            z += vec.z;
        }

        x /= vecs.length + 1;
        y /= vecs.length + 1;
        z /= vecs.length + 1;

        return new Vector3(x, y, z);
    }

    public Vector3 lerp(Vector3 other, float t) {
        return new Vector3(
                this.x + (other.x - this.x) * t,
                this.y + (other.y - this.y) * t,
                this.z + (other.z - this.z) * t
        );
    }

    public Vector3 min(Vector3 other) {
        return new Vector3(Math.min(this.x, other.x), Math.min(this.y, other.y), Math.min(this.z, other.z));
    }

    public Vector3 max(Vector3 other) {
        return new Vector3(Math.max(this.x, other.x), Math.max(this.y, other.y), Math.max(this.z, other.z));
    }

    public Vector3 clamp(Vector3 min, Vector3 max) {
        return new Vector3(
                Math.min(Math.max(this.x, min.x), max.x),
                Math.min(Math.max(this.y, min.y), max.y),
                Math.min(Math.max(this.z, min.z), max.z)
        );
    }

    public Vector3 clamp(float min, float max) {
        return new Vector3(
                Math.min(Math.max(this.x, min), max),
                Math.min(Math.max(this.y, min), max),
                Math.min(Math.max(this.z, min), max)
        );
    }

    public Vector3 abs() {
        return new Vector3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public static long pack(Vector3 vec) {  // 21 bits for each long
        long x = ((long) (int) vec.x) & 0x1FFFFFL;
        long y = ((long) (int) vec.y) & 0x1FFFFFL;
        long z = ((long) (int) vec.z) & 0x1FFFFFL;

        return (x << 42) | (y << 21) | z;
    }

    public static Vector3 unpack(long key) {
        long x = (key >> 42) & 0x1FFFFFL;
        long y = (key >> 21) & 0x1FFFFFL;
        long z = (key      ) & 0x1FFFFFL;

        if ((x & 0x100000L) != 0 ) x |= 0xFFFFFFFFFFE00000L;
        if ((y & 0x100000L) != 0 ) y |= 0xFFFFFFFFFFE00000L;
        if ((z & 0x100000L) != 0 ) z |= 0xFFFFFFFFFFE00000L;

        return new Vector3(x, y, z);
    }

    public Vector3 project(Vector3 onto) {
        float ontoLenSq = onto.dot(onto);
        if (ontoLenSq == 0) return new Vector3(0, 0, 0);
        return onto.scale(this.dot(onto) / ontoLenSq);
    }

    public Vector3 reflect(Vector3 normal) {
        return this.subtract(normal.scale(2 * this.dot(normal)));
    }

    public float angle(Vector3 other) {
        float lenProduct = this.length() * other.length();
        if (lenProduct == 0) return 0f;
        float cos = Math.max(-1f, Math.min(1f, this.dot(other) / lenProduct));
        return (float) Math.acos(cos);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vector3 other = (Vector3) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}