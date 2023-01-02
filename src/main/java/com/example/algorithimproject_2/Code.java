package com.example.algorithimproject_2;

public class Code {
    byte data;
    String code;

    public Code(byte data, String code) {
        this.data = data;
        this.code = code;
    }


    public static Code[] generateCodes(HuffmanNode tree, String prefix) {
        if (tree.left == null && tree.right == null) {
            // Leaf node
            System.out.println(prefix);
            return new Code[] {new Code(tree.data, prefix)};
        }

        // Non-leaf node
        Code[] leftCodes = generateCodes(tree.left, prefix + "0");
        Code[] rightCodes = generateCodes(tree.right, prefix + "1");

        Code[] codes = new Code[leftCodes.length + rightCodes.length];
        System.arraycopy(leftCodes, 0, codes, 0, leftCodes.length);
        System.arraycopy(rightCodes, 0, codes, leftCodes.length, rightCodes.length);

        return codes;
    }
}
