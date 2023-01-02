package com.example.algorithimproject_2;


public class PriorityQueue<T extends Comparable<T>> {
    private Heap<T> heap;

    public PriorityQueue(int capacity) {

        heap = new Heap<>(capacity);
    }

    public void add(T value) {
        heap.add(value);
    }

    public T peek() {
        return heap.peek();
    }

    public T remove() {
        return heap.remove();
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public int size() {
        return heap.size();
    }
}