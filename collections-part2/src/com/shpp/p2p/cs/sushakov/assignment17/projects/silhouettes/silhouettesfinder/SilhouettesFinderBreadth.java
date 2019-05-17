package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.silhouettesfinder;

import com.shpp.p2p.cs.sushakov.assignment17.collections.PriorityQueueP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.QueueP2P;

/**
 * Finder of the silhouettes on the image. It is possible to search for silhouettes
 * having contrasting color relative to the background. It is also possible
 * to recognize silhouettes that are stuck together.
 */
public class SilhouettesFinderBreadth extends AbstractFinder {

    /**
     * Filling the area with a silhouette with an untouched color.
     * In the filling of the silhouette used the breadth-first algorithm.
     *
     * @param startX starting X point for filling.
     * @param startY starting Y point for filling.
     * @param silhouette the silhouette for which is counted the number of pixels.
     */
    protected void filling(int startX, int startY, Silhouette silhouette, int fillerColor) {
        Pixel basePixel = preparedImage[startY][startX];
        QueueP2P<Pixel> pixelQueue = new PriorityQueueP2P<>(Pixel.class);
        pixelQueue.offer(basePixel);
        int currentX;
        int currentY;

        while (!pixelQueue.isEmpty()) {
            silhouette.addPixel();
            basePixel = pixelQueue.poll();

            currentX = basePixel.getPixelX();
            currentY = basePixel.getPixelY();
            preparedImage[currentY][currentX] = new Pixel(fillerColor, currentX, currentY);

            int offsetX;
            int offsetY;
            for (int i = 0; i < OFFSET_X.length; i++) {
                offsetX = currentX + OFFSET_X[i];
                offsetY = currentY + OFFSET_Y[i];

                if (preparedImage[offsetY][offsetX].getAverageColor() != backgroundAverageColor) {
                    pixelQueue.offer(preparedImage[offsetY][offsetX]);
                    preparedImage[offsetY][offsetX] = new Pixel(fillerColor, offsetX, offsetY);
                }
            }
        }
    }

}
