package net.kallen.solaris.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class LimitedList<T> implements Iterable<T> {
    private final int maxSize;
    private ArrayList<T> list;

    public LimitedList(int maxSize) {
        this.maxSize = maxSize;
        this.list = new ArrayList<T>();
    }


    public boolean add(T item) {
        if (list.size() < maxSize) {
            return list.add(item);
        }

        return false;
    }

    public T remove(int index) {
        return list.remove(index);
    }

    public int getMaxSize() {
        return maxSize;
    }

    public T get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public List<T> asList() {
        return Collections.unmodifiableList(list);
    }

    public boolean contains(T item) {
        return list.contains(item);
    }

    public void clear() {
        list.clear();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int indexOf(T item) {
        return list.indexOf(item);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}
