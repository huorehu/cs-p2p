package com.shpp.p2p.cs.sushakov.assignment14.compressor.view;

import com.shpp.p2p.cs.sushakov.assignment14.compressor.AnalyzerCLI;
import com.shpp.p2p.cs.sushakov.assignment14.compressor.CompressorP2P;

/** Abstract class which displays results of program to an abstract output device */
public abstract class ResultPrinter {

    /** Analyzer of command line interpreter */
    private AnalyzerCLI analyzerCLI;
    /** The archiver */
    private CompressorP2P compressor;

    public ResultPrinter(AnalyzerCLI analyzerCLI, CompressorP2P compressor) {
        this.analyzerCLI = analyzerCLI;
        this.compressor = compressor;
    }

    /** Prints result of the program to console */
    public void printResult(int archiveResult) {
        if (archiveResult == 0) {
            String process;

            if (analyzerCLI.isCompression()) {
                process = "Archiving";
            } else {
                process = "Unarchiving";
            }

            printMessage(process + " is OK");
            printMessage(process + " time: " + getNormalyzedTime(compressor.getCompressionTime()));
            printMessage(process + " efficiency: " + compressor.getCompressionEfficiency() + " %");
            printMessage("Source file size: " + compressor.getSrcSize() + " bytes");
            printMessage("Destination file size: " + compressor.getDstSize() + " bytes.");
        } else {
            printMessage("Error in process.");
        }
    }

    /**
     * Normalizes time to string representation and adds time unit.
     *
     * @param compressionTime the time in milliseconds.
     * @return normalized time in string representation.
     */
    protected String getNormalyzedTime(long compressionTime) {
        String compressionTimeStr = compressionTime + "";
        if (compressionTimeStr.length() > 3) {
            compressionTimeStr = compressionTimeStr.substring(0, compressionTimeStr.length() - 3) + " s";
        } else {
            compressionTimeStr += " ms";
        }
        return compressionTimeStr;
    }

    /** Displays a message to an abstract output device.
     *
     *  @param message the text of the message.
     */
    public abstract void printMessage(String message);

}
