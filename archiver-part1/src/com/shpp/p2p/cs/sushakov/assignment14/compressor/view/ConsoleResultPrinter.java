package com.shpp.p2p.cs.sushakov.assignment14.compressor.view;

import com.shpp.p2p.cs.sushakov.assignment14.compressor.AnalyzerCLI;
import com.shpp.p2p.cs.sushakov.assignment14.compressor.CompressorP2P;

/** Displays the results of the program to console */
public class ConsoleResultPrinter extends ResultPrinter {

    public ConsoleResultPrinter(AnalyzerCLI analyzerCLI, CompressorP2P compressor) {
        super(analyzerCLI, compressor);
    }

    /** Print message to console.
     *
     *  @param message the text of the message.
     */
    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

}
