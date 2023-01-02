package com.example.algorithimproject_2;

import java.io.*;

public class BitOutputStream {
    private int currentByte;
    BufferedOutputStream outputStream;
    String byteWriting = "";
    private int numBitsInCurrentByte;

    public BitOutputStream(String path) throws FileNotFoundException {
        outputStream = new BufferedOutputStream(new FileOutputStream(path, true));
        this.currentByte = 0;
        this.numBitsInCurrentByte = 0;
    }

    void writeBit(int bit) throws IOException {
        currentByte = (currentByte << 1) | bit;
        numBitsInCurrentByte++;

        if (numBitsInCurrentByte == 8) {
            //System.out.println();
            //System.out.println(Integer.toBinaryString(currentByte));
            outputStream.write((byte)currentByte);
            currentByte = 0;
            numBitsInCurrentByte = 0;
        }

    }

    public void close() throws IOException {
        // If there are any remaining bits, pad the current byte with zeros and write it to the output stream
        while (numBitsInCurrentByte > 0) {
            writeBit(0);
        }


        System.out.println("End");
        // Close the underlying output stream
        outputStream.close();
    }
}

