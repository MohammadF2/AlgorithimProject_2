package com.example.algorithimproject_2;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutionException;

public class DriverTesting {

    public static void main(String[] args) throws IOException {

        FileOutputStream out = new FileOutputStream("C:\\Users\\frajm\\Desktop\\testing\\test1.2\\oo.txt");
        out.write((byte) 0);
        out.close();

        Huffman huf = new Huffman();
        //huf.deCompressFile("C:\\Users\\frajm\\Desktop\\testing\\0.5G.MohammadF");

    }

}

