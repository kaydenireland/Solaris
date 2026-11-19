package net.kallen.solaris.data;

public class RingBuffer {
    public static int DEFAULT_SIZE = 128;

    private final Object[] buffer;

    private final int capacity;
    private int head = 0;
    private int tail = 0;

    public RingBuffer() {
        this(DEFAULT_SIZE);
    }

    public RingBuffer(int capacity) {
        this.capacity = capacity + 1;   // Add one since one space will be empty for pointers
        this.buffer = new Object[this.capacity];
    }

    public boolean push(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Cannot push null into the ring buffer.");
        }

        int next = (tail + 1) % capacity;
        if (next == head) return false;

        buffer[tail] = obj;
        tail = next;
        return true;
    }

    public Object pop() {
        if (isEmpty()) return null;  // Buffer is empty

        Object obj = buffer[head];
        buffer[head] = null;    // Allow GC to delete object
        head = (head + 1) % capacity;
        return obj;
    }
    
    public boolean isEmpty() {
        return head == tail;
    }
}
