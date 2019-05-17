package com.shpp.p2p.cs.sushakov.assignment14.compressor.code_generators;

/** Bit sequence generator */
public class CodeGenerator {

    /** First generated bit-code */
    private String generatedBitCode = "01";

    /**
     * Generates a bit sequence depending on the transmitted bit sequence in such a way
     * that this sequence has the smallest possible larger numerical value.
     * E.g.  in      out
     *      "01"    "001"
     *      "001"   "011"
     *      "0001"  "0011"
     *
     * @param currentBitCode the bit sequence with which to generate the next bit-code.
     * @return the bit sequence.
     */
    public String getNextBitCode(String currentBitCode) {
        if (currentBitCode == null || !currentBitCode.matches("0+1+")) {
            throw new IllegalArgumentException("Incorrect argument <currentBitCode>");
        }

        char secondChar = currentBitCode.charAt(1);
        if (secondChar == '0') {
            return currentBitCode.substring(1) + "1";
        }

        return currentBitCode.replaceAll("1", "0") + "1";
    }

    /**
     * Generates the next bit-code based on the last requested code
     * of the current code generator object.
     *
     * @return the generated bit-code.
     */
    public String getNextCode() {
        String currentCode = generatedBitCode;
        generatedBitCode = getNextBitCode(generatedBitCode);

        return currentCode;
    }

}
