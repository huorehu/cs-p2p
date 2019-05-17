package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.silhouettesfinder;

import java.io.IOException;

/** Finding silhouettes on the image. */
public class ProgramSilhouettesFinder implements Runnable {

    /** Path to image for finding silhouettes */
    private String imagePath;

    /** Searching algorithm */
    private String algorithm = "depth";

    public ProgramSilhouettesFinder(String imagePath, String algorithm) {
        this.imagePath = imagePath;
        this.algorithm = algorithm;
    }

    @Override
    public void run() {
        if (imagePath == null) {
            imagePath = "resources/shpp_standard.jpg";          // 4
        }

        PreparedImage image = null;

        try {
            image = new PreparedImage(imagePath);
        } catch (IOException e) {
            System.out.println("Image not found. " + e.getMessage());
        }

        Finder finder;

        if (algorithm.equals("breadth")) {
            System.out.println("Breadth-first search:");
            finder = new SilhouettesFinderBreadth();
        } else {
            System.out.println("Depth-first search:");
            finder = new SilhouettesFinderDepth();
        }

        if (image != null) {
            int amountSilhouettes = finder.findSilhouettes(image);
            System.out.println("Amount of silhouettes: " + amountSilhouettes);
        }
    }

}
