package com.shpp.p2p.cs.sushakov.assignment14;

import com.shpp.p2p.cs.sushakov.assignment14.compressor.AnalyzerCLI;
import com.shpp.p2p.cs.sushakov.assignment14.compressor.CompressorP2P;
import com.shpp.p2p.cs.sushakov.assignment14.compressor.view.ConsoleResultPrinter;
import com.shpp.p2p.cs.sushakov.assignment14.compressor.view.ResultPrinter;

import java.io.FileNotFoundException;

/** Archive and unarchive files. */
public class Main {

    public static void main(String[] args) {

        AnalyzerCLI analyzerCLI = new AnalyzerCLI(args);
        CompressorP2P compressor = new CompressorP2P();
        ResultPrinter printer = new ConsoleResultPrinter(analyzerCLI, compressor);

        if (!analyzerCLI.isCorrectArgs()) {
            printer.printMessage("Arguments is not correct.");
            return;
        }

        String fileIn = analyzerCLI.getSrcFile();   // source file
        String fileOut = analyzerCLI.getDstFile();  // destination file
        int archiveResult;

        try {
            if (analyzerCLI.isCompression()) {
                printer.printMessage("Archiving...");
                archiveResult = compressor.archive(fileIn, fileOut);
            } else {
                printer.printMessage("Unarchiving...");
                archiveResult = compressor.unarchive(fileIn, fileOut);
            }

            printer.printResult(archiveResult);

        } catch (FileNotFoundException e) {
            printer.printMessage("File not found");
        }
    }

}
