package com.example.algorithimproject_2;

class Node<T> implements Comparable<Node> {
    public byte data;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    public Node(byte data, int frequency, HuffmanNode left, HuffmanNode right) {
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return frequency + " " + data;
    }

    @Override
    public int compareTo(Node o) {
        return 0;
    }
}