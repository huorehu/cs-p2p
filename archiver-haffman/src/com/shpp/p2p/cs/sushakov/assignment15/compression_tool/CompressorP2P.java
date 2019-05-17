package com.shpp.p2p.cs.sushakov.assignment15.compression_tool;

import java.io.*;
import java.util.Properties;

/** Archiver that compresses any binary information. */
public class CompressorP2P {

    /** Path to properties file */
    private static final String PROPERTIES_FILE = "src/com/shpp/p2p/cs/sushakov/assignment15/config.properties";
    /** Code of unarchiving error */
    private static final int UNARCHIVING_ERROR = 1;
    /** Code of success process */
    private static final int PROCESS_SUCCESS = 0;

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
    public int archive(String fileIn, String fileOut) throws Exception {
        startTime = System.currentTimeMillis();

        AbstractCoder coder = getCoder(fileIn);

        BitsBufferedWriter writer = new BitsBufferedWriter(fileOut);
        ArchiveCaptionWriter captionWriter = new ArchiveCaptionWriter(writer, coder);
        captionWriter.writeToFile();

        byte readByte;
        try (FileInputStream fileInputStream = new FileInputStream(fileIn)) {
            while (fileInputStream.available() > 0) {
                readByte = (byte) fileInputStream.read();
                writer.addCode(coder.getCode(readByte));
            }
        }
        writer.flush();

        compressionEfficiency = evaluateCompressionEfficiency(fileIn, fileOut, 1);
        compressionTime = evaluateCompressionTime(startTime);

        return PROCESS_SUCCESS;
    }

    /**
     * Returns object of class which implements class {@link AbstractCoder}
     *
     * @param fileIn the filename.
     * @return the coder which contains table of codes.
     */
    private AbstractCoder getCoder(String fileIn) throws Exception {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)) {
            properties.load(fileInputStream);
        }

        return (AbstractCoder) Class.forName(properties.getProperty("algorithm")).getConstructor(String.class).newInstance(fileIn);
    }

    private long evaluateCompressionTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * Evaluates compression efficiency.
     *
     * @param fileIn non-compressed file.
     * @param fileOut compressed file.
     * @param isArchiving if archiving 1, else -1
     * @return compression efficiency.
     */
    private double evaluateCompressionEfficiency(String fileIn, String fileOut, int isArchiving) {
        File srcFile = new File(fileIn);
        File archivedFile = new File(fileOut);
        srcSize = srcFile.length();
        dstSize = archivedFile.length();
        double compressionEfficiency = (1 - Math.pow(dstSize * 1.0 / srcSize, isArchiving)) * 100;

        return (int) (compressionEfficiency * 100) / 100.0;    // rounding to two decimal places
    }

    /**
     * Unarchive archived file.
     *
     * @param fileIn the file that will be unarchived.
     * @param fileOut the file in which will be saving unarchived information.
     * @return 0 - if the archiving was successful. Otherwise 1.
     */
    public int unarchive(String fileIn, String fileOut) throws IOException {
        startTime = System.currentTimeMillis();
        BitsBufferedReader reader;
        Decoder decoder;

        reader = new BitsBufferedReader(fileIn);
        decoder = new Decoder(reader);

        StringBuilder code = new StringBuilder();
        long dataSize = decoder.getDataSize();
        long maxCodeLength = decoder.getMaxCodeLength();
        Byte currentCode;

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileOut)) {
            for (int i = 0; i < dataSize; i++) {
                while ((currentCode = decoder.getByte(code.toString())) == null && code.length() < maxCodeLength) {
                    code.append(reader.nextBit());
                }

                if (currentCode != null) {
                    fileOutputStream.write(currentCode);
                    code.delete(0, code.length());
                } else {
                    return UNARCHIVING_ERROR;
                }
            }
        }

        compressionEfficiency = evaluateCompressionEfficiency(fileIn, fileOut, -1);
        compressionTime = evaluateCompressionTime(startTime);

        return PROCESS_SUCCESS;
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
