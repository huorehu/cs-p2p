package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool;

import com.shpp.p2p.cs.sushakov.assignment17.collections.HashMapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;

import java.io.IOException;

import static com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.BitsBufferedWriter.BITS_IN_BYTE;

/** Reads and stores bit sequence-byte association table */
public class Decoder {

    /** Bit sequence-byte association table */
    private MapP2P<String, Byte> decoderTable;
    /** Size of the table in bytes */
    private int tableSize;
    /** Size of the non-compressed data in bytes */
    private long dataSize;
    /** Max length of the bit sequence in the bit sequence-byte association table */
    private int maxCodeLength;

    public Decoder(BitsBufferedReader reader) throws IOException {
        tableSize = readTableSize(reader);
        dataSize = readDataSize(reader);
        createDecoderTable(reader);
    }

    /** Creates decoder table and writes it to the buffer */
    private void createDecoderTable(BitsBufferedReader reader) throws IOException {
        decoderTable = new HashMapP2P<>();
        long tableSizeBit = tableSize * BITS_IN_BYTE;

        int readBits = 0;
        byte byteCode;
        String code;

        while (readBits + BITS_IN_BYTE < tableSizeBit) {
            byteCode = (byte) reader.nextByte();
            code = reader.nextBitsStr(reader.nextByte());
            decoderTable.put(code, byteCode);
            readBits += BITS_IN_BYTE * 2 + code.length();  // 2 includes byte of source data and byte for amount bits in code length

            if (code.length() > maxCodeLength) {
                maxCodeLength = code.length();
            }
        }
        // Skips bits not used in the table (bit-placeholders)
        while (readBits % BITS_IN_BYTE != 0) {
            reader.nextBit();
            readBits++;
        }
    }

    /** Reads from file size of the non-compressed data */
    private long readDataSize(BitsBufferedReader reader) throws IOException {
        return reader.nextLong();
    }

    /** Reads from file size of the table */
    private int readTableSize(BitsBufferedReader reader) throws IOException {
        return reader.nextInt();
    }

    /** @return size of the non-compressed data */
    public long getDataSize() {
        return dataSize;
    }

    /** @return the particular byte that associated with the code */
    public Byte getByte(String code) {
        return decoderTable.get(code);
    }

    /** @return the max code length from the table */
    public int getMaxCodeLength() {
        return maxCodeLength;
    }

}

