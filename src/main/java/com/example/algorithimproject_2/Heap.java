package com.example.algorithimproject_2;

import java.util.Comparator;

public class Heap<T extends Comparable<T>> {
    private T[] heap;
    private int size;

    public Heap(int capacity) {
        heap = (T[]) new Comparable[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(T element) {
        if (size == heap.length) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = element;
        int index = size;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap[index].compareTo(heap[parentIndex]) >= 0) {
                break;
            }
            swap(index, parentIndex);
            index = parentIndex;
        }
        size++;
    }

    public T remove() {
        if (size == 0) {
            return null;
        }
        T min = heap[0];
        heap[0] = heap[size - 1];
        heap[size - 1] = null;
        size--;
        int index = 0;
        while (index < size) {
            int leftChildIndex = 2 * index + 1;
            int rightChildIndex = 2 * index + 2;
            if (leftChildIndex >= size) {
                break;
            }
            int minIndex = leftChildIndex;
            if (rightChildIndex < size) {
                if (heap[rightChildIndex].compareTo(heap[leftChildIndex]) < 0) {
                    minIndex = rightChildIndex;
                }
            }
            if (heap[index].compareTo(heap[minIndex]) <= 0) {
                break;
            }
            swap(index, minIndex);
            index = minIndex;
        }
        return min;
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return heap[0];
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

}
