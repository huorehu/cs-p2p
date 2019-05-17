package com.shpp.p2p.cs.sushakov.assignment15.compression_algorithms.huffman;

/**
 * Class contains encoded byte of original alphabet.
 * This node is a leaf of a H-tree.
 */
public class ByteNode extends Node {

    /** Encoded byte of original alphabet */
    private final Byte encodedByte;
    /** The code for original byte */
    private String code;

    public ByteNode(Byte encodedByte, long numberOccurrencesOfByte) {
        super(numberOccurrencesOfByte);
        this.encodedByte = encodedByte;
    }

    /**
     * Initiates and creates codes for archiving data.
     *
     * @param code the code of particular encoded original byte.
     */
    @Override
    public void initCode(String code) {
        this.code = code;
    }

    /**
     * Returns original byte (value of current byte).
     *
     * @return the original byte.
     */
    public Byte getEncodedByte() {
        return encodedByte;
    }

    /**
     * Returns the code for original byte, which formed as a result of traversing H-tree.
     *
     * @return the code for original byte.
     */
    public String getCode() {
        return code;
    }

}
