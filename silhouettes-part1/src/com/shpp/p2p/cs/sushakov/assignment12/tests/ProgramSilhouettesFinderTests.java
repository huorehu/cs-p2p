package com.shpp.p2p.cs.sushakov.assignment12.tests;

import com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.PreparedImage;
import com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.SilhouettesFinder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/** Using for testing main program on different images */
public class ProgramSilhouettesFinderTests implements Runnable {

    /** Path to images for testing app */
    private static final String TESTS_IMAGES_PATH = "src/com/shpp/p2p/cs/sushakov/assignment12/tests/test_images.txt";
    /** Contains the method for finding silhouettes */
    private SilhouettesFinder finder = new SilhouettesFinder();

    @Override
    public void run() {
        boolean isDataLoaded = false;
        Map<String, Integer> imagesList = null;

        try {
            imagesList = loadImageInfo();
            isDataLoaded = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("Incorrect amount of silhouettes in file. " + nfe.getMessage());
        }

        if (isDataLoaded) {
            Set<Map.Entry<String, Integer>> entries = imagesList.entrySet();
            int testResult;

            for (Map.Entry<String, Integer> entry : entries) {
                testResult = findSilhouettes(entry.getKey());
                if (testResult == entry.getValue()) {
                    System.out.print("[ OK ] ");
                } else {
                    System.out.print("[ FAILED ] ");
                }
                System.out.println("Amount of silhouettes: " + testResult + " (" + entry.getKey() + "), expected amount: " + entry.getValue());
            }
        }
    }

    /** Finding silhouettes on the image */
    private int findSilhouettes(String imagePath) {
        PreparedImage image = null;
        int result = 0;

        try {
            image = new PreparedImage(imagePath);
        } catch (IOException e) {
            System.out.println("\nImage not found. " + e.getMessage());
        }
        if (image != null) {
            result = finder.findSilhouettes(image);
        }

        return result;
    }

    /** Loading image paths for testing app */
    private Map<String, Integer> loadImageInfo() throws IOException {
        Map<String, Integer> result = new HashMap<>();
        Stream<String> images = Files.lines(Paths.get(TESTS_IMAGES_PATH), StandardCharsets.UTF_8);
        images.filter(line -> line.indexOf(" ") > 0).forEach(line -> result.put(line.substring(0, line.indexOf(" ")), Integer.parseInt(line.substring(line.indexOf(" ") + 1))));

        return result;
    }

}
