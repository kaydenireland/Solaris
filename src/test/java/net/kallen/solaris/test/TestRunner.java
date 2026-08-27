package test.java.net.kallen.solaris.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.java.net.kallen.solaris.test.math.*;
import test.java.net.kallen.solaris.test.data.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AABB2Test.class,
        AABB3Test.class,
        QuadTreeTest.class,
        OctreeTest.class
})
public class TestRunner {
}