package com.shpp.p2p.cs.sushakov.assignment14.compressor;

import java.io.*;

/** Archiver that compresses any binary information. */
public class CompressorP2P {

    /** The compression efficiency */
    private double compressionEfficiency;
    /** The compression time */
    private long compressionTime;
    /** The size of source file */
    private long srcSize;
    /** The size of destination file */
    private long dstSize;
    /** Start compression time */
    private long startTime;

    /**
     * Archive any binary information that stored in the file.
     *
     * @param fileIn the file that will be archived.
     * @param fileOut the file in which the archive will be saved
     * @return 0 - if the archiving was successful. Otherwise 1.
     * @throws FileNotFoundException if file not found.
     */
    public int archive(String fileIn, String fileOut) throws FileNotFoundException {
        startTime = System.currentTimeMillis();
        Coder coder;

        try {
            coder = new Coder(fileIn);
        } catch (IOException e) {
            if ("1".equals(e.getMessage())) {
                return 1;
            }
            throw new FileNotFoundException(e.getMessage());
        }

        BitsBufferedWriter writer = new BitsBufferedWriter(fileOut);
        ArchiveCaptionWriter captionWriter = new ArchiveCaptionWriter(writer, coder);
        captionWriter.writeToFile();

        FileInputStream fileInputStream = new FileInputStream(fileIn);
        byte readByte;

        try {
            while (fileInputStream.available() > 0) {
                readByte = (byte) fileInputStream.read();
                writer.addCode(coder.getCode(readByte));
            }
            writer.flush();
        } catch (IOException e) {
            return 1;
        }

        compressionEfficiency = evaluateCompressionEfficiency(fileIn, fileOut, true);
        compressionTime = evaluateCompressionTime(startTime);

        return 0;   // success
    }

    private long evaluateCompressionTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Evaluates compression efficiency.
     *
     * @param fileIn non-compressed file.
     * @param fileOut compressed file.
     * @return compression efficiency.
     */
    private double evaluateCompressionEfficiency(String fileIn, String fileOut, boolean isArchiving) {
        File srcFile = new File(fileIn);
        File archivedFile = new File(fileOut);
        srcSize = srcFile.length();
        dstSize = archivedFile.length();
        double compressionEfficiency;

        if (isArchiving) {
            compressionEfficiency = (1 - dstSize * 1.0 / srcSize) * 100;
        } else {
            compressionEfficiency = (1 - srcSize * 1.0 / dstSize) * 100;
        }

        return (int) (compressionEfficiency * 100) / 100.0;    // rounding to two decimal places
    }

    /**
     * Unarchive archived file.
     *
     * @param fileIn the file that will be unarchived.
     * @param fileOut the file in which will be saving unarchived information.
     * @return 0 - if the archiving was successful. Otherwise 1.
     */
    public int unarchive(String fileIn, String fileOut) throws FileNotFoundException {
        startTime = System.currentTimeMillis();
        FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
        BitsBufferedReader reader;
        Decoder decoder;

        try {
            reader = new BitsBufferedReader(fileIn);
            decoder = new Decoder(reader);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        } catch (IOException e) {
            return 1;
        }

        StringBuilder code = new StringBuilder();
        long dataSize = decoder.getDataSize();
        long maxCodeLength = decoder.getMaxCodeLength();
        Byte currentCode;

        for (int i = 0; i < dataSize; i++) {
            try {
                while ((currentCode = decoder.getByte(code.toString())) == null && code.length() < maxCodeLength) {
                    code.append(reader.nextBit());
                }

                if (currentCode != null) {
                    fileOutputStream.write(currentCode);
                    code.delete(0, code.length());
                } else {
                    break;
                }
            } catch (IOException e) {
                return 1;
            }
        }

        compressionEfficiency = evaluateCompressionEfficiency(fileIn, fileOut, false);
        compressionTime = evaluateCompressionTime(startTime);

        return 0;   // success
    }

    /** @return the compression efficiency */
    public double getCompressionEfficiency() {
        return compressionEfficiency;
    }

    /** @return the compression time */
    public long getCompressionTime() {
        return compressionTime;
    }

    /** @return size of the source file */
    public long getSrcSize() {
        return srcSize;
    }

    /** @return size of the destination file */
    public long getDstSize() {
        return dstSize;
    }

}
