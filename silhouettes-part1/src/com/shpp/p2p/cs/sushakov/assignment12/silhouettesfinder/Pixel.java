package com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

/** Simple pixel of image that contains it color in different representations */
public class Pixel implements Colors {

    /** Average of the pixel's color */
    private final int averageColor;

    public Pixel(int colorRGB) {
        averageColor = getAverageOfColor(colorRGB);
    }

    /** @return average of the pixel's color */
    public int getAverageColor() {
        return averageColor;
    }

}
