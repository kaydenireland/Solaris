package main.java.net.kallen.solaris.math;

public class Vector2 {
    private float x, y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector3 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
    }

    public static Vector2 add(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static Vector2 subtract(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public static Vector2 multiply(Vector2 vector1, Vector2 vector2) {
        return new Vector2(vector1.getX() * vector2.getX(), vector1.getY() * vector2.getY());
    }

    public static Vector2 multiply(Vector2 v, float scalar) {
        return new Vector2(v.getX() * scalar, v.getY() * scalar);
    }

    public static Vector2 divide(Vector2 vector1, Vector2 vector2) {
        if (vector2.getX() == 0 || vector2.getY() == 0) return null;
        return new Vector2(vector1.getX() / vector2.getX(), vector1.getY() / vector2.getY());
    }

    public static Vector2 divide(Vector2 v, float scalar) {
        if (scalar == 0) return null;
        return new Vector2(v.getX() / scalar, v.getY() / scalar);
    }

    public static Vector2 negate(Vector2 vector) {
        return new Vector2(-vector.getX(), -vector.getY());
    }

    public static float length(Vector2 vector) {
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
    }

    public static Vector2 normalize(Vector2 vector) {
        float len = Vector2.length(vector);
        if (len == 0) return new Vector2(0, 0);
        return Vector2.divide(vector, new Vector2(len, len));
    }

    public static float dot(Vector2 vector1, Vector2 vector2) {
        return vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY();
    }

    public static float distance(Vector2 vec1, Vector2 vec2) {
        float x = vec1.getX() - vec2.getX();
        float y = vec1.getY() - vec2.getY();

        return (float) Math.sqrt(x*x + y*y);
    }

    public static Vector2 center(Vector2 vec1, Vector2 vec2) {
        float nx = vec1.getX() + vec2.getX();
        float ny = vec1.getY() + vec2.getY();

        return new Vector2(nx /2, ny /2);
    }

    public static Vector2 average(Vector2... vecs) {
        float x = 0;
        float y = 0;

        for(Vector2 vec : vecs) {
            x += vec.getX();
            y += vec.getY();
        }

        x /= vecs.length;
        y /= vecs.length;

        return new Vector2(x, y);
    }

    public static Vector2 lerp(Vector2 vector1, Vector2 vector2, float t) {
        return new Vector2(
                vector1.getX() + (vector2.getX() - vector1.getX()) * t,
                vector1.getY() + (vector2.getY() - vector1.getY()) * t
        );
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

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 vec) {
        this.x = vec.getX();
        this.y = vec.getY();
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
}