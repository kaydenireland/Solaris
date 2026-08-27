package main.java.net.kallen.solaris.math.curve;

import main.java.net.kallen.solaris.math.Binomial;
import main.java.net.kallen.solaris.math.vector.VectorLike;
import main.java.net.kallen.solaris.util.exception.InvalidListSizeException;

import java.util.List;

/** A Bézier curve that takes a list of points of N length
 *
 */
public class BezierCurve<V extends VectorLike<V>> {
    private final List<V> points;
    private final int degree;
    private final long[] binomials;

    public BezierCurve(List<V> list) throws InvalidListSizeException {
        if (list.size() < 2) {
            throw new InvalidListSizeException(
                    "BezierCurve requires at least 2 control points."
            );
        }
        this.points = List.copyOf(list);
        this.degree = list.size() - 1;
        this.binomials = new long[degree + 1];
        for (int i = 0; i <= degree; i++) {
            binomials[i] = Binomial.compute(degree, i);
        }
    }

    public V getPoint(float t) {
        t = Math.max(0f, Math.min(1f, t));
        V result = null;
        for(int i = 0; i <= degree; i++) {
            float tt = (float) Math.pow(t, i);
            float u = (float) Math.pow(1-t, degree - i);
            float weight = (float) binomials[i] * tt * u;

            V term = points.get(i).scale(weight);
            result = (result == null) ? term : result.add(term);
        }
        return result;
    }
}
