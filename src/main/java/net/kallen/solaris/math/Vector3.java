package main.java.net.kallen.solaris.math;

public class Vector3 {
    private float x, y, z;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = other.getZ();
    }

    public Vector3(Vector2 other) {
        this.x = other.getX();
        this.y = other.getY();
        this.z = 0;
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public static Vector2 get2D(Vector3 vector) {
        return new Vector2(vector.getX(), vector.getY());
    }

    public static Vector3 to3D(Vector2 vector) {
        return new Vector3(vector.getX(), vector.getY(), 0);
    }

    public static Vector3 add(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY(), vector1.getZ() + vector2.getZ());
    }

    public static Vector3 subtract(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY(), vector1.getZ() - vector2.getZ());
    }

    public static Vector3 multiply(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY(), vector1.getZ() * vector2.getZ());
    }

    public static Vector3 multiply(Vector3 vector, float scalar) {
        return new Vector3(vector.getX() * scalar, vector.getY() * scalar, vector.getZ() * scalar);
    }

    public static Vector3 divide(Vector3 vector1, Vector3 vector2) {
        return new Vector3(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY(), vector1.getZ() / vector2.getZ());
    }

    public static Vector3 divide(Vector3 vector, float scalar) {
        if (scalar == 0) return null;
        return new Vector3(vector.getX() / scalar, vector.getY() / scalar, vector.getZ() / scalar);
    }

    public static Vector3 negate(Vector3 vector) {
        return new Vector3(-vector.getX(), -vector.getY(), -vector.getZ());
    }

    public static float length(Vector3 vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY() + vector.getZ() * vector.getZ());
    }

    public static Vector3 normalize(Vector3 vector) {
        float len = Vector3.length(vector);
        if (len == 0) return new Vector3(0, 0, 0);
        return Vector3.divide(vector, new Vector3(len, len, len));
    }

    public static float dot(Vector3 vector1, Vector3 vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY() + vector1.getZ() * vector2.getZ();
    }

    public static Vector3 cross(Vector3 v1, Vector3 v2) {
        return new Vector3(
                v1.getY() * v2.getZ() - v1.getZ() * v2.getY(),
                v1.getZ() * v2.getX() - v1.getX() * v2.getZ(),
                v1.getX() * v2.getY() - v1.getY() * v2.getX()
        );
    }

    public static float distance(Vector3 vec1, Vector3 vec2) {
        float x = vec1.getX() - vec2.getX();
        float y = vec1.getY() - vec2.getY();
        float z = vec1.getZ() - vec2.getZ();

        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public static Vector3 center(Vector3 vec1, Vector3 vec2) {
        float nx = vec1.getX() + vec2.getX();
        float ny = vec1.getY() + vec2.getY();
        float nz = vec1.getZ() + vec2.getZ();

        return new Vector3(nx/2, ny/2, nz/2);
    }

    public static Vector3 average(Vector3... vecs) {
        float x = 0;
        float y = 0;
        float z = 0;

        for(Vector3 vec : vecs) {
            x += vec.getX();
            y += vec.getY();
            z += vec.getZ();
        }

        x /= vecs.length;
        y /= vecs.length;
        z /= vecs.length;

        return new Vector3(x, y, z);
    }

    public static Vector3 lerp(Vector3 vector1, Vector3 vector2, float t) {
        return new Vector3(
                vector1.getX() + (vector2.getX() - vector1.getX()) * t,
                vector1.getY() + (vector2.getY() - vector1.getY()) * t,
                vector1.getZ() + (vector2.getZ() - vector1.getZ()) * t
        );
    }

    public static long pack(Vector3 vec) {  // 21 bits for each long
        long x = ((long) (int) vec.getX()) & 0x1FFFFFL;
        long y = ((long) (int) vec.getY()) & 0x1FFFFFL;
        long z = ((long) (int) vec.getZ()) & 0x1FFFFFL;

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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }


}