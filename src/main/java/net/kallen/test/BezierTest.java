package main.java.net.kallen.test;

import main.java.net.kallen.solaris.math.curve.BezierCurve;
import main.java.net.kallen.solaris.math.vector.Vector2;
import main.java.net.kallen.solaris.math.vector.Vector3;
import java.util.List;

public class BezierTest {
    public static void main(String[] args) {
        // 2D cubic curve
        List<Vector2> points2D = List.of(
                new Vector2(0, 0),
                new Vector2(1, 3),
                new Vector2(2.5f, 3.8f),
                new Vector2(5, 1)
        );
        BezierCurve<Vector2> curve2D = new BezierCurve<>(points2D);

        System.out.println("2D curve:");
        for (int step = 0; step <= 10; step++) {
            float t = step * 0.1f;
            Vector2 p = curve2D.getPoint(t);
            System.out.println("t=" + t + " -> " + p);
        }

        // 3D quadratic curve (arcs up and over in z)
        List<Vector3> points3D = List.of(
                new Vector3(0, 0, 0),
                new Vector3(2, 4, 3),
                new Vector3(4, 0, 0)
        );
        BezierCurve<Vector3> curve3D = new BezierCurve<>(points3D);

        System.out.println("\n3D curve:");
        for (int step = 0; step <= 10; step++) {
            float t = step * 0.1f;
            Vector3 p = curve3D.getPoint(t);
            System.out.println("t=" + t + " -> " + p);
        }
    }
}