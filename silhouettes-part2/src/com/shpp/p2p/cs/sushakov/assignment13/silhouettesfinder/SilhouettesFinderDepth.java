package com.shpp.p2p.cs.sushakov.assignment13.silhouettesfinder;

/**
 * Finder of the silhouettes on the image. It is possible to search for silhouettes
 * having contrasting color relative to the background. It is also possible
 * to recognize silhouettes that are stuck together.
 */
public class SilhouettesFinderDepth extends AbstractFinder {

    /**
     * Filling the area with a silhouette with an untouched color.
     * In the filling of the silhouette used the depth-first algorithm.
     *
     * @param startX starting X point for filling.
     * @param startY starting Y point for filling.
     * @param silhouette the silhouette for which is counted the number of pixels.
     */
    @Override
    protected void filling(int startX, int startY, Silhouette silhouette, int fillerColor) {
        silhouette.addPixel();
        preparedImage[startY][startX] = new Pixel(fillerColor, startX, startY);

        int currentX;
        int currentY;

        for (int i = 0; i < OFFSET_X.length; i++) {
            currentX = startX + OFFSET_X[i];
            currentY = startY + OFFSET_Y[i];

            if (preparedImage[currentY][currentX].getAverageColor() != backgroundAverageColor) {
                filling(currentX, currentY, silhouette, fillerColor);
            }
        }
    }

}
