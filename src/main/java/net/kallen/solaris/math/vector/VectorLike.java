package main.java.net.kallen.solaris.math.vector;

public interface VectorLike<V> {
    void set(V other);
    V add(V other);
    V subtract(V other);
    V multiply(V other);
    V divide(V other);
    V scale(float s);
    V negate();
    float length();
    V normalize();
    float dot(V other);
    float distance(V other);
    V center(V other);
    V average(V... other);
    V lerp(V other, float t);
    V min(V other);
    V max(V other);
    V clamp(V min, V max);
    V clamp(float min, float max);
    V abs();
    V project(V other);
    V reflect(V normal);
    float angle(V other);
    String toString();
}