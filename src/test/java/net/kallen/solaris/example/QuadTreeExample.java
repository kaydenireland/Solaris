package test.java.net.kallen.solaris.example;

import main.java.net.kallen.solaris.data.QuadTree;
import main.java.net.kallen.solaris.math.box.AABB2;
import main.java.net.kallen.solaris.math.vector.Vector2;

public class QuadTreeExample {

    public static void main(String[] args) {
        QuadTree tree = new QuadTree(new AABB2(new Vector2(0, 0), new Vector2(100, 100)));

        tree.add(new Vector2(10, 10));
        tree.add(new Vector2(20, 20));
        tree.add(new Vector2(30, 30));
        tree.add(new Vector2(40, 40));
        tree.add(new Vector2(10, 40));
        tree.add(new Vector2(14, 10));
        tree.add(new Vector2(20, 21));
        tree.add(new Vector2(8, 30));
        tree.add(new Vector2(40, 90));
        tree.add(new Vector2(67, 69));
        tree.add(new Vector2(67, 69));

        System.out.println(tree);
        System.out.println(tree.query(new AABB2(new Vector2(10, 10), new Vector2(25, 30))));

        AABB2 box = new AABB2(0, 0, 50, 50);
        Vector2 point = new Vector2(25, 75);

        System.out.println(box.getClosestPoint(point));
    }



}
