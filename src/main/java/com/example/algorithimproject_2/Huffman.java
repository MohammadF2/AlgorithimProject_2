package com.example.algorithimproject_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigInteger;
import java.nio.channels.FileChannel;
import java.util.*;

public class Huffman {

    private String[] codes = new String[256];

    private int[] charFreqs = new int[256];

    private HuffmanNode root;

    private ArrayList<Byte> specialBytes = new ArrayList<>();

    private PriorityQueue<HuffmanNode> queue = new PriorityQueue<>(256);

    ObservableList<TableViewer> tableViewers = FXCollections.observableArrayList();

    private int sum = 0;

    public void compressFile(String filePath) throws IOException {

        System.out.println("Compressing");

        FileInputStream in = new FileInputStream(filePath);

        BufferedInputStream inp = new BufferedInputStream(new FileInputStream(filePath));

        byte[] data = new byte[in.available()];

        inp.read(data);

        getFileData(data);

        inp.close();

        processTheQueue();

        getHuffmanCodes(root, new StringBuilder());

        compress(filePath);

    } // public method to call (Compress a file)

    private void compress(String filepath) throws IOException {

        String fileName = new File(filepath).getName();
        String fileExtension = "";
        int i = filepath.lastIndexOf('.');
        if (i > 0) {
            fileExtension = filepath.substring(i+1);
        }

        fileName =  fileName.replace("." + fileExtension, "");

        //FileOutputStream out = new FileOutputStream("C:\\Users\\frajm\\Desktop\\testing\\" + fileName + ".MohammadF");

        String newFilePath = "";

        newFilePath = filepath.replace("."+fileExtension, ".MohammadF");

        System.out.println(newFilePath);

        BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream(newFilePath));
        writeHeader(fileExtension, filepath, newFilePath);

        outB.close();

        StringBuilder encoded = new StringBuilder();

        BitOutputStream bitOutputStream = new BitOutputStream(newFilePath);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(filepath));


        byte[] data = new byte[in.available()];

        in.read(data);

        for (byte b : data) {
            encoded.append(codes[b & 0xFF]);

            String code = codes[b & 0xFF];

            for (int j = 0; j < code.length(); j++) {
                char c = code.charAt(j);
                if (c == '0') {
                    bitOutputStream.writeBit(0);
                } else {
                    bitOutputStream.writeBit(1);
                }
            }
        }

        System.out.println("Writing on file");

        System.out.println("Started to convert the bit's.");

        System.out.println("Ended converting the bits\nStarted to write on the file");

        System.out.println("Started writing the data");

        //outB.write(bytes);
        System.out.println("Done");

        bitOutputStream.close();

    } // private method to compress a file

    private void writeHeader(String fileExtension,String filePath, String newFilePath) throws IOException {

        System.out.println("Started writing header.");

        FileInputStream in = new FileInputStream(filePath);

        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(newFilePath));

        FileChannel channel = in.getChannel();

        out.write((calculateHeaderSize(fileExtension, channel.size()) + "").getBytes());

        String write ="E" + fileExtension + "\n";

        write += channel.size() +"\n";

        System.out.println(write);

        out.write(write.getBytes());

        for (int i = 0; i < specialBytes.size(); i++) {

            write = specialBytes.get(i) + " " + charFreqs[specialBytes.get(i) & 0xFF] + "\n";
            out.write(write.getBytes());
            System.out.println(write);

        }

        out.close();
        System.out.println("Ended writing the header");

    } // write the header on the file

    private int calculateHeaderSize(String fileEX, long size) throws UnsupportedEncodingException {
        String write = "";


        for (int i = 0; i < specialBytes.size(); i++) {
            write += specialBytes.get(i) + " " + charFreqs[specialBytes.get(i) & 0xFF] + "\n";
        }

        byte[] asciiBytes = write.getBytes("UTF-8");
        int asciiSize = asciiBytes.length;
        return asciiSize ;
    }

    private void getFileData(byte[] data) {


        for (byte b : data) {
            if(( b & 0xFF) == 0) {
                System.out.println("NULL");
            }
            if(charFreqs[b & 0xFF] == 0) {
                specialBytes.add(b);
            }
            charFreqs[b & 0xFF]++;
        }

    } // build the queue and get the char freq

    private void processTheQueue() {

        for (int i = 0; i < specialBytes.size(); i++) {
            if(charFreqs[specialBytes.get(i) & 0xFF] > 0) {
                sum += charFreqs[specialBytes.get(i) & 0xFF]; //
                queue.add(new HuffmanNode (specialBytes.get(i), charFreqs[specialBytes.get(i) & 0xFF], null, null));
                //tableViewers.add(new TableViewer(specialBytes.get(i), charFreqs[specialBytes.get(i) & 0xFF], codes[specialBytes.get(i) & 0xFF]));
            }
        }


        HuffmanNode left;
        HuffmanNode right;

        int queueFreq = sum;
        do {
            if (!queue.isEmpty()) {
                left = queue.remove();
                try {
                    right = queue.remove();
                    queue.add(new HuffmanNode(left.data, (left.frequency + right.frequency), left, right));

                } catch (Exception e) {
                    queue.add(new HuffmanNode(left.data, (left.frequency + 0), left, null));
                }
            }

            try {
                queueFreq = queue.peek().frequency;
            } catch (Exception err) {}

        } while (queueFreq != sum);


        root = queue.peek();
    }

    private void processTheDeCompressQueue() {
        HuffmanNode left;
        HuffmanNode right;



        tableViewers = FXCollections.observableArrayList();


        int queueFreq = sum;
        do {
            if (!queue.isEmpty()) {
                left = queue.remove();
                try {
                    right = queue.remove();
                    tableViewers.add(new TableViewer(left.data, left.frequency, codes[left.data & 0xFF]));
                    tableViewers.add(new TableViewer(right.data, right.frequency, codes[right.data & 0xFF]));
                    queue.add(new HuffmanNode(left.data, (left.frequency + right.frequency), left, right));
                    System.out.println("Has right and left");
                } catch (Exception e) {
                    tableViewers.add(new TableViewer(left.data, left.frequency, codes[left.data & 0xFF]));
                    queue.add(new HuffmanNode(left.data, (left.frequency + 0), left, null));
                    System.out.println("Has left");
                }
            }

            try {
                queueFreq = queue.peek().frequency;
            } catch (Exception err) {}

        } while (queueFreq != sum);

        codes = new String[256];
        getHuffmanCodes(root, new StringBuilder());

        root = queue.peek();
    }

    public void getHuffmanCodes(HuffmanNode root, StringBuilder prefix) {

        if( root != null) {

            if(root.left == null & root.right == null) {
                this.codes[root.data &0xFF] = prefix.toString();
                System.out.println("byte: " + root.data + " code: " + prefix.toString());
                System.out.println("Here1");
                //this.tableViewers.get(root.data &0xFF).setCode(prefix.toString());
                System.out.println("Here2");
                return;
            }

            if(root.left != null) {
                prefix.append('0');
                getHuffmanCodes(root.left, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }


            if(root.right != null) {
                prefix.append('1');
                getHuffmanCodes(root.right, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

        // Recursively generate the codes for the left and right subtrees


    } // calculate Huffman code

    public void deCompressFile(String filePath) throws IOException {
        System.out.println("DeCompressing");

        this.sum = 0;

        String fileName = new File(filePath).getName();
        String fileExtension = "";
        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            fileExtension = filePath.substring(i+1);
        }

        if(fileExtension.equals("MohammadF")) {

            //FileInputStream in = new FileInputStream(filePath);

            FileInputStream inputStream = new FileInputStream(filePath);

            FileChannel channel = inputStream.getChannel();

            long fileSize = channel.size();

            boolean readingSize = true;
            boolean readingExtension = false;
            boolean readingOriginalFileSize = false;
            boolean readingHead = false;
            boolean readingData = false;

            boolean readingChar = true;
            boolean readingFreq = false;

            int counter = 0;

            String headSize = "";

            int readSize = 1;

            String originalExt = "";

            String originalSize = "";

            long originalFileSize = 0;

            HuffmanNode curr = root;

            byte[] buffer = new byte[readSize];

            long numBytesRead  = inputStream.read(buffer);

            System.out.println(inputStream.available());

            String newFilePath;

            while (numBytesRead  != -1) {
                //System.out.println("bits " +Integer.toBinaryString(buffer[0]));

                if( readingSize ) {
                    counter++;
                    if((char) buffer[0] == 'E' ){
                        readingSize = false;
                        readingExtension = true;
                        System.out.println(headSize);
                    } else {
                        headSize += (char) buffer[0];
                    }
                } else if(readingExtension) {
                    counter++;
                    if('\n' == (char) buffer[0]){
                        readingExtension = false;
                        readingOriginalFileSize = true;

                        System.out.println(originalExt);
                    } else {
                        originalExt += (char) buffer[0];
                    }

                } else if(readingOriginalFileSize) {
                    counter++;
                    if('\n' == (char) buffer[0]) {
                        readingOriginalFileSize = false;
                        readingHead = true;
                        originalFileSize = Long.parseLong(originalSize);
                        this.originalFileSize = originalFileSize;
                        readSize = Integer.parseInt(headSize) + 1;
                        counter += readSize;
                        System.out.println("reading size " +readSize);
                        System.out.println(originalFileSize);
                    } else {
                        originalSize += (char) buffer[0];
                    }
                } else if(readingHead) {


                    String byteData = "";
                    String byteFreq = "";

                    this.sum = 0;

                    for (int j = 0; j < buffer.length; j++) {

                        if(j == buffer.length - 1) {
                            readingHead = false;
                            readingData = true;
                            readSize = 1024 * 1024;
                            processTheDeCompressQueue();
                            curr = root;
                            System.out.println("switching " + buffer.length);
                            newFilePath = filePath.replace(".MohammadF", "." + originalExt);
                            printOnfile(buffer[buffer.length - 1], newFilePath, curr);

                        } else {
                            if(readingChar) {
                                if(' ' == buffer[j]){
                                    readingFreq = true;
                                    readingChar = false;
                                } else {
                                    byteData += (char) (buffer[j]);
                                }
                            } else if(readingFreq) {
                                if ('\n' == buffer[j]) {
                                    queue.add(new HuffmanNode(((byte) Integer.parseInt(byteData)), Integer.parseInt(byteFreq), null, null));
                                    System.out.println(new HuffmanNode(((byte) Integer.parseInt(byteData)), Integer.parseInt(byteFreq), null, null).toString());
                                    sum += Integer.parseInt(byteFreq);
                                    byteData = "";
                                    byteFreq = "";
                                    readingChar = true;
                                    readingFreq = false;
                                } else {
                                    byteFreq += (char) (buffer[j]);
                                }
                            }
                        }
                    }

                } else if(readingData) {
                    //System.out.println("here");
                    newFilePath = filePath.replace(".MohammadF", "." + originalExt);
                    curr = printChunks(buffer, newFilePath, curr);
                }

                buffer = new byte[readSize];
                numBytesRead = inputStream.read(buffer);
            }

            System.out.println("Done");

        } else {
            return;
        }

    } // deCompressFile

    long originalFileSize = 0;
    long alreadyDone = 0;

    private HuffmanNode printChunks(byte[] data, String filePath, HuffmanNode curr) throws IOException {
        BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream(filePath, true));

        String res;

        for (int i = 0; i < data.length; i++) {
            res = Integer.toBinaryString(data[i] & 0xff);
            while (res.length() < 8) {
                res = "0" + res;
            }
            //System.out.println(Integer.toBinaryString(data & 0xff));
            for (int j = 0; j < res.length(); j++) {

                if(originalFileSize > alreadyDone) {

                    if (res.charAt(j) == '0') {
                        curr = curr.left;
                    } else if (res.charAt(j) == '1') {
                        curr = curr.right;
                    }

                    if (curr.isLeaf()) {
                        alreadyDone++;
                        outB.write(curr.data);
                        System.out.println("Done: " + alreadyDone + "/" + originalFileSize);
                        curr = root;

                    }
                }
            }
        }
        outB.close();
        return curr;
    }

    private HuffmanNode printOnfile(byte data, String filePath, HuffmanNode curr) throws IOException {

        BufferedOutputStream outB = new BufferedOutputStream(new FileOutputStream(filePath, true));

        String res;


        res = Integer.toBinaryString(data & 0xff);

        while (res.length() < 8) {
            res = "0" + res;
        }

        //System.out.println(Integer.toBinaryString(data & 0xff));

        for (int j = 0; j < res.length(); j++) {

            if(originalFileSize > alreadyDone) {

                if (res.charAt(j) == '0') {
                    curr = curr.left;
                } else if (res.charAt(j) == '1') {
                    curr = curr.right;
                }

                if (curr.isLeaf()) {
                    alreadyDone++;
                    outB.write(curr.data);
                    System.out.println("Done: " + alreadyDone + "/" + originalFileSize);
                    curr = root;

                }
            }
        }


        outB.close();
        return curr;
    }

    public ObservableList<TableViewer> getTableViewers() {
        return tableViewers;
    }
}