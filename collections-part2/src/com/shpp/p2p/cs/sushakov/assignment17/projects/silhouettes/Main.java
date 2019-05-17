package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes;

/** Starts searching silhouettes in the individual thread */
public class Main {

    /** The size of thread in which executing searching silhouettes */
    private static final int STACK_SIZE = 16_000_000; // 16 MB
    /** The name of thread in which executing searching silhouettes */
    private static final String THREAD_NAME = "1";

    public static void main(String[] args) {

        AnalyzerCLI analyzerCLI = new AnalyzerCLI(args);

        new Thread(null, analyzerCLI.getProgram(), THREAD_NAME, STACK_SIZE).start();
    }

}
