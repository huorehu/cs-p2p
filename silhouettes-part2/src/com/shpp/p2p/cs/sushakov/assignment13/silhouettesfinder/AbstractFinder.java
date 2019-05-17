package com.shpp.p2p.cs.sushakov.assignment13.silhouettesfinder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract finder of the silhouettes on the image. It is possible to search for silhouettes
 * having contrasting color relative to the background. It is also possible
 * to recognize silhouettes that are stuck together.
 */
public abstract class AbstractFinder implements Finder {

    /** The proportion of the maximum number of pixels in the silhouette.
     *  If the content of pixels in the silhouette is less than this value,
     *  then the silhouette will be considered garbage. */
    private static final double PERCENTAGE_OF_SIZING = 0.1;

    /** Percentage of the deviation of the average number of pixels
     *  of the silhouette at which it will be considered one. */
    private static final double STUCK_PERCENTAGE = 1.4;
    /** The X offset of pixel */
    protected static final int[] OFFSET_X = {0, 1, 0, -1};
    /** The Y offset of pixel */
    protected static final int[] OFFSET_Y = {-1, 0, 1, 0};

    /** The image on which the silhouettes are searched */
    protected Pixel[][] preparedImage;
    /** The list of all silhouettes which are was found */
    private List<Silhouette> silhouetteList;
    /** Image's background color as average of it RGB-color */
    protected int backgroundAverageColor;

    /**
     * Finding silhouettes of the people in the prepared image and returns their amount.
     *
     * @param image the prepared image for finding.
     * @return the amount of people on the image.
     */
    @Override
    public int findSilhouettes(PreparedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image can not be null");
        }

        Pixel[][] imageInPixels = image.getImageInArray();
        defineBackgroundColor(imageInPixels);
        preparedImage = addBorderToImage(imageInPixels);
        silhouetteList = new ArrayList<>();

        for (int y = 1; y < preparedImage.length - 1; y++) {
            for (int x = 1; x < preparedImage[0].length - 1; x++) {
                if (preparedImage[y][x].getAverageColor() != backgroundAverageColor) {
                    addSilhouette(x, y);
                }
            }
        }
        return getSilhouettesAmount(silhouetteList);
    }

    /** Surround image with border which has backgroundAverageColor */
    private Pixel[][] addBorderToImage(Pixel[][] imageInArray) {
        int additionalPixels = 2;
        Pixel[][] result = new Pixel[imageInArray.length + additionalPixels][imageInArray[0].length + additionalPixels];
        int colorBackgroundRGB = Colors.getColorRGB(backgroundAverageColor);

        // All pixels of the picture are filled with the background color
        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[0].length; x++) {
                result[y][x] = new Pixel(colorBackgroundRGB, x, y);
            }
        }
        // Copy a picture on top of the background with an indented margin around one pixel
        for (int y = 1; y < imageInArray.length - 1; y++) {
            for (int x = 1; x < imageInArray[0].length - 1; x++) {
                result[y][x] = imageInArray[y - 1][x - 1].setPixelX(x).setPixelY(y);
            }
        }
        return result;
    }

    /**
     * @param silhouetteList the list of silhouettes
     * @return number of silhouettes that meet the selection criteria
     */
    private int getSilhouettesAmount(List<Silhouette> silhouetteList) {
        int amountOfSilhouettes = 0;
        int maxSilhouetteSize = getMaxSilhouetteSize(silhouetteList);

        // Removal of small objects (in percentage terms) as garbage.
        List<Silhouette> silhouettes = silhouetteList.stream().
                filter(silhouette -> silhouette.getAmountPixels() > maxSilhouetteSize * PERCENTAGE_OF_SIZING).
                collect(Collectors.toList());

        int averageAmountPixels = getAverageAmountPixels(silhouettes);
        // Upper bound of amount pixels in one silhouette
        double upperBound = averageAmountPixels * STUCK_PERCENTAGE;

        // Counting stuck together and no silhouettes.
        for (Silhouette sil : silhouettes) {
            if (sil.getAmountPixels() > upperBound) {
                // If two silhouettes are stuck together, then increases the counter of silhouettes
                amountOfSilhouettes++;
            }
            amountOfSilhouettes++;
        }
        return amountOfSilhouettes;
    }

    /**
     * @param silhouettes the list of silhouettes.
     * @return The average number of pixels among the silhouettes in the list
     */
    private int getAverageAmountPixels(List<Silhouette> silhouettes) {
        int result = 0;

        for (Silhouette silhouette : silhouettes) {
            result += silhouette.getAmountPixels();
        }
        return result / silhouettes.size();
    }

    /**
     * Finding silhouette which contains max amount of a pixels.
     *
     * @param silhouetteList the list of silhouettes those was found.
     * @return max amount of pixels that are contains in one silhouette.
     */
    private int getMaxSilhouetteSize(List<Silhouette> silhouetteList) {
        int maxSize = silhouetteList.get(0).getAmountPixels();

        for (int i = 1; i < silhouetteList.size(); i++) {
            int comparativeSilhouette = silhouetteList.get(i).getAmountPixels();
            if (maxSize < comparativeSilhouette) {
                maxSize = comparativeSilhouette;
            }
        }
        return maxSize;
    }

    /** Define the image's background color */
    private void defineBackgroundColor(Pixel[][] image) {
        int blackCount = 0;
        int whiteCount = 0;

        for (Pixel[] aPreparedImage : image) {
            for (int x = 0; x < image[0].length; x++) {
                if (aPreparedImage[x].getAverageColor() == Colors.AVERAGE_BLACK) {
                    blackCount++;
                    continue;
                }
                whiteCount++;
            }
        }
        if (whiteCount > blackCount) {
            backgroundAverageColor = Colors.AVERAGE_WHITE;
        } else {
            backgroundAverageColor = Colors.AVERAGE_BLACK;
        }
    }

    /**
     * Add new silhouette in the list.
     *
     * @param startX the start X point of the silhouette.
     * @param startY the start Y point of the silhouette.
     */
    private void addSilhouette(int startX, int startY) {
        Silhouette silhouette = new Silhouette();
        silhouetteList.add(silhouette);
        filling(startX, startY, silhouette, Colors.getColorRGB(backgroundAverageColor));
    }

    /**
     * Fills the area with a silhouette with an untouched color.
     *
     * @param startX starting X point for filling.
     * @param startY starting Y point for filling.
     * @param silhouette the silhouette for which is counted the number of pixels.
     */
    protected abstract void filling(int startX, int startY, Silhouette silhouette, int fillerColor);

}
