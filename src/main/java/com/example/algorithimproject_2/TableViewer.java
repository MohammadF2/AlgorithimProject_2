package com.example.algorithimproject_2;

public class TableViewer {

    private byte data;
    private int freq;
    private String code;
    private String ascii;

    public TableViewer(byte data, int freq, String code) {

        this.data = data;
        this.freq = freq;
        this.code = code;

        this.ascii = Integer.toBinaryString(data & 0xFF);

        while (ascii.length() < 8) {
            ascii = "0" + ascii;
        }
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAscii() {
        return ascii;
    }

    public void setAscii(String ascii) {
        this.ascii = ascii;
    }

    @Override
    public String toString() {
        return "TableViewer{" +
                "data=" + data +
                ", freq=" + freq +
                ", code='" + code + '\'' +
                ", ascii='" + ascii + '\'' +
                '}';
    }
}
