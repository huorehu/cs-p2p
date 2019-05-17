package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman;

import com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.AnalyzerCLI;
import com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.CompressorP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.view.ConsoleResultPrinter;
import com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.view.ResultPrinter;

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
        } catch (Exception e) {
            printer.printMessage("UNEXPECTED ERROR");
        }
    }

}
