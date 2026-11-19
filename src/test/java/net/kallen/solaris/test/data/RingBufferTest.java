package net.kallen.solaris.test.data;

import net.kallen.solaris.data.RingBuffer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RingBufferTest {

    private RingBuffer buffer;

    @Before
    public void setUp() {
        buffer = new RingBuffer(3);
    }

    @Test
    public void newBufferShouldBeEmpty() {
        assertTrue(buffer.isEmpty());
        assertNull(buffer.pop());
    }

    @Test
    public void shouldPushAndPopSingleElement() {
        assertTrue(buffer.push("Hello"));

        assertFalse(buffer.isEmpty());
        assertEquals("Hello", buffer.pop());
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void shouldMaintainFifoOrder() {
        buffer.push("First");
        buffer.push("Second");
        buffer.push("Third");

        assertEquals("First", buffer.pop());
        assertEquals("Second", buffer.pop());
        assertEquals("Third", buffer.pop());
    }

    @Test
    public void shouldRejectPushWhenFull() {
        assertTrue(buffer.push(1));
        assertTrue(buffer.push(2));
        assertTrue(buffer.push(3));

        assertFalse(buffer.push(4));

        assertEquals(1, buffer.pop());
        assertEquals(2, buffer.pop());
        assertEquals(3, buffer.pop());
    }

    @Test
    public void shouldAllowPushAfterPop() {
        buffer.push(1);
        buffer.push(2);
        buffer.push(3);

        assertEquals(1, buffer.pop());

        assertTrue(buffer.push(4));

        assertEquals(2, buffer.pop());
        assertEquals(3, buffer.pop());
        assertEquals(4, buffer.pop());

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void shouldWrapAroundWhenCapacityIsExceeded() {
        buffer.push("A");
        buffer.push("B");
        buffer.push("C");

        assertEquals("A", buffer.pop());
        assertEquals("B", buffer.pop());

        buffer.push("D");
        buffer.push("E");

        assertEquals("C", buffer.pop());
        assertEquals("D", buffer.pop());
        assertEquals("E", buffer.pop());

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void shouldSupportRepeatedWrapAround() {
        for (int i = 0; i < 100; i++) {
            assertTrue(buffer.push(i));
            assertEquals(i, buffer.pop());
        }

        assertTrue(buffer.isEmpty());
    }

    @Test
    public void shouldReturnNullWhenPoppingEmptyBuffer() {
        assertNull(buffer.pop());

        buffer.push("Value");

        assertEquals("Value", buffer.pop());
        assertNull(buffer.pop());
    }

    @Test
    public void shouldRejectNullValues() {
        assertThrows(
                IllegalArgumentException.class,
                () -> buffer.push(null)
        );
    }

    @Test
    public void shouldSupportDefaultConstructor() {
        RingBuffer defaultBuffer = new RingBuffer();

        assertTrue(defaultBuffer.isEmpty());
        assertTrue(defaultBuffer.push("Value"));
        assertEquals("Value", defaultBuffer.pop());
    }

    @Test
    public void shouldStoreDifferentObjectTypes() {
        assertTrue(buffer.push("String"));
        assertTrue(buffer.push(42));
        assertTrue(buffer.push(3.14));

        assertEquals("String", buffer.pop());
        assertEquals(42, buffer.pop());
        assertEquals(3.14, (Double) buffer.pop(), 0.0001);
    }
}
