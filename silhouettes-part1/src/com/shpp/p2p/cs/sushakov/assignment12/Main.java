package com.shpp.p2p.cs.sushakov.assignment12;

import com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.ProgramSilhouettesFinder;
import com.shpp.p2p.cs.sushakov.assignment12.tests.ProgramSilhouettesFinderTests;

/** Starts searching silhouettes in the individual thread */
public class Main {

    /** The size of thread in which executing searching silhouettes */
    private static final int STACK_SIZE = 4 << 23;
    /** The name of thread in which executing searching silhouettes */
    private static final String THREAD_NAME = "1";
    /** The test-mode key */
    private static final String TEST_MODE_KEY = "-t";

    public static void main(String[] args) {

        Runnable program;
        String imagePath = null;

        if (args.length > 0) {
            imagePath = args[0];
        }
        program = new ProgramSilhouettesFinder(imagePath);

        if (TEST_MODE_KEY.equals(imagePath)) {
            program = new ProgramSilhouettesFinderTests();
        }

        new Thread(null, program, THREAD_NAME, STACK_SIZE).start();
    }

}
