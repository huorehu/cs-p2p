package com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

import java.io.IOException;

/** Finding silhouettes on the image. */
public class ProgramSilhouettesFinder implements Runnable {

    /** Path to image for finding silhouettes */
    private String imagePath;

    public ProgramSilhouettesFinder(String imagePath) {
        this.imagePath = imagePath;
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

        SilhouettesFinder finder = new SilhouettesFinder();

        if (image != null) {
            int amountSilhouettes = finder.findSilhouettes(image);
            System.out.println("Amount of silhouettes: " + amountSilhouettes);
        }
    }

}
