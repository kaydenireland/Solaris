package main.java.net.kallen.solaris.math.vector;

public class Vector4 implements VectorLike<Vector4> {
    public float x, y, z, w;

    public final static Vector4 ZERO = new Vector4(0, 0, 0, 0);

    public Vector4(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Vector4(Vector3 other, float w) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = w;
    }

    public void set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public void set(Vector4 other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
        this.w = other.w;
    }

    public Vector4 add(Vector4 other) {
        return new Vector4(this.x + other.x, this.y + other.y, this.z + other.z, this.w + other.w);
    }

    public Vector4 subtract(Vector4 other) {
        return new Vector4(this.x - other.x, this.y - other.y, this.z - other.z, this.w - other.w);
    }

    public Vector4 multiply(Vector4 other) {
        return new Vector4(this.x * other.x, this.y * other.y, this.z * other.z, this.w * other.w);
    }

    public Vector4 divide(Vector4 other) {
        return new Vector4(this.x / other.x, this.y / other.y, this.z / other.z, this.w / other.w);
    }

    public Vector4 scale(float s) {
        return new Vector4(this.x * s, this.y * s, this.z * s, this.w * s);
    }

    public Vector4 negate() {
        return new Vector4(-this.x, -this.y, -this.z, -this.w);
    }

    public float length() {
        return (float) Math.sqrt(x*x + y*y + z*z + w*w);
    }

    public Vector4 normalize() {
        float len = length();
        if (len == 0) return new Vector4(0, 0, 0, 0);
        return divide(new Vector4(len, len, len, len));
    }

    public float dot(Vector4 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w;
    }

    public float distance(Vector4 other) {
        float dx = this.x - other.x, dy = this.y - other.y, dz = this.z - other.z, dw = this.w - other.w;
        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz + dw*dw);
    }

    public float distanceSquared(Vector4 other) {
        float dx = this.x - other.x, dy = this.y - other.y, dz = this.z - other.z, dw = this.w - other.w;
        return (dx*dx + dy*dy + dz*dz + dw*dw);
    }

    public Vector4 center(Vector4 other) {
        return new Vector4((x+other.x)/2, (y+other.y)/2, (z+other.z)/2, (w+other.w)/2);
    }

    public Vector4 average(Vector4... vecs) {
        float x = this.x;
        float y = this.y;
        float z = this.z;
        float w = this.w;

        for (Vector4 vec : vecs) {
            x += vec.x;
            y += vec.y;
            z += vec.z;
            w += vec.w;
        }

        x /= vecs.length;
        y /= vecs.length;
        z /= vecs.length;
        w /= vecs.length;

        return new Vector4(x, y, z, w);
    }

    public Vector4 lerp(Vector4 other, float t) {
        return new Vector4(
                x + (other.x - x) * t,
                y + (other.y - y) * t,
                z + (other.z - z) * t,
                w + (other.w - w) * t
        );
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        result = prime * result + Float.floatToIntBits(w);
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
        Vector4 other = (Vector4) obj;
        if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
            return false;
        if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
            return false;
        if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
            return false;
        if (Float.floatToIntBits(w) != Float.floatToIntBits(other.w))
            return false;
        return true;
    }

    public Vector4 min(Vector4 other) {
        return new Vector4(Math.min(this.x, other.x), Math.min(this.y, other.y), Math.min(this.z, other.z), Math.min(this.w, other.w));
    }

    public Vector4 max(Vector4 other) {
        return new Vector4(Math.max(this.x, other.x), Math.max(this.y, other.y), Math.max(this.z, other.z), Math.max(this.w, other.w));
    }

    public Vector4 clamp(Vector4 min, Vector4 max) {
        return new Vector4(
                Math.min(Math.max(this.x, min.x), max.x),
                Math.min(Math.max(this.y, min.y), max.y),
                Math.min(Math.max(this.z, min.z), max.z),
                Math.min(Math.max(this.w, min.w), max.w)
        );
    }

    public Vector4 clamp(float min, float max) {
        return new Vector4(
                Math.min(Math.max(this.x, min), max),
                Math.min(Math.max(this.y, min), max),
                Math.min(Math.max(this.z, min), max),
                Math.min(Math.max(this.w, min), max)
        );
    }

    public Vector4 abs() {
        return new Vector4(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z), Math.abs(this.w));
    }

    public Vector4 project(Vector4 onto) {
        float ontoLenSq = onto.dot(onto);
        if (ontoLenSq == 0) return new Vector4(0, 0, 0, 0);
        return onto.scale(this.dot(onto) / ontoLenSq);
    }

    public Vector4 reflect(Vector4 normal) {
        return this.subtract(normal.scale(2 * this.dot(normal)));
    }

    public float angle(Vector4 other) {
        float lenProduct = this.length() * other.length();
        if (lenProduct == 0) return 0f;
        float cos = Math.max(-1f, Math.min(1f, this.dot(other) / lenProduct));
        return (float) Math.acos(cos);
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ", w: " + w + ")";
    }
}