package com.example.algorithimproject_2;

public class HuffmanNode implements Comparable<HuffmanNode> {

    public byte data;
    public int frequency;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode(byte data, int frequency, HuffmanNode left, HuffmanNode right) {
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
    public int compareTo(HuffmanNode o) {
        return Integer.compare(frequency, o.frequency);
    }


    public boolean isLeaf() {
        if (left== null && right == null) {
            return true;
         }
        else
            return false;
    }

}
