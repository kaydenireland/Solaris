package net.kallen.solaris.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import net.kallen.solaris.test.math.*;
import net.kallen.solaris.test.data.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        Vector2Test.class,
        Vector3Test.class,
        Vector4Test.class,
        Matrix4Test.class,
        AABB2Test.class,
        AABB3Test.class,
        QuadTreeTest.class,
        OctreeTest.class
})
public class TestRunner {
}