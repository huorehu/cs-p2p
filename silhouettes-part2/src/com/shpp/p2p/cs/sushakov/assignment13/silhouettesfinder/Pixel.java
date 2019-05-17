package com.shpp.p2p.cs.sushakov.assignment13.silhouettesfinder;

/** Simple pixel of image that contains it color in different representations */
public class Pixel implements Comparable {

    /** Average of the pixel's color */
    private final int averageColor;

    /** X coordinate of pixel */
    private int pixelX;

    /** Y coordinate of pixel */
    private int pixelY;

    public Pixel(int colorRGB, int pixelX, int pixelY) {
        averageColor = Colors.getAverageOfColor(colorRGB);
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    /** @return average of the pixel's color */
    public int getAverageColor() {
        return averageColor;
    }

    /** @return X coordinate of current pixel */
    public int getPixelX() {
        return pixelX;
    }

    /** @return Y coordinate of current pixel */
    public int getPixelY() {
        return pixelY;
    }

    /** Sets pixel's coordinate X */
    public Pixel setPixelX(int pixelX) {
        this.pixelX = pixelX;
        return this;
    }

    /** Sets pixel's coordinate Y */
    public Pixel setPixelY(int pixelY) {
        this.pixelY = pixelY;
        return this;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

}
