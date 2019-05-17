package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes;

import com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.silhouettesfinder.ProgramSilhouettesFinder;
import com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.tests.ProgramSilhouettesFinderTests;

/** Analyzes data from command line and creates the program-searcher
 *  depending on key from CLI */
public class AnalyzerCLI {

    /** The test-mode key */
    private static final String TEST_MODE_KEY = "-t";
    /** The breadth-first key */
    private static final String BREADTH_FIRST_KEY = "-b";
    /** Algorithm used */
    private String algorithm = "depth";
    /** Path to image */
    private String imagePath;
    /** Program that searches silhouettes on the image */
    private Runnable program;

    public AnalyzerCLI(String[] args) {
        if (args.length > 0) {
            for (String arg : args) {
                if (BREADTH_FIRST_KEY.equals(arg)) {
                    algorithm = "breadth";
                } else if (TEST_MODE_KEY.equals(arg) && program == null) {
                    program = new ProgramSilhouettesFinderTests(algorithm);
                } else {
                    imagePath = arg;
                }
            }
        }
    }

    /**
     * @return object of program for searching silhouettes.
     */
    public Runnable getProgram() {
        if (program == null) {
            program = new ProgramSilhouettesFinder(imagePath, algorithm);
        }
        return program;
    }

}
