package com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

/** Contains integer values of the particular color */
public interface Colors {

    /** Integer representation of a black color */
    int BLACK = 0;
    /** Integer representation of a white color */
    int WHITE = -1;
    /** Integer of average of a black color */
    int AVERAGE_BLACK = 0;
    /** Integer of average of a white color */
    int AVERAGE_WHITE = 255;

    /**
     * Calculating average of the particular RGB-color.
     *
     * @param colorRGB integer representation of a color in RGB-model.
     * @return the average of color.
     */
    default int getAverageOfColor(int colorRGB) {
        // Bit-shifts of 16 and 8 bits to obtain the color value of the corresponding channel
        int tempRed = colorRGB >> 16 & 0xff;
        int tempGreen = colorRGB >> 8 & 0xff;
        int tempBlue = colorRGB & 0xff;

        return  (tempRed + tempGreen + tempBlue) / 3; // 3 is an amount of channels of a color
    }

    /**
     * Forms an integral representation of color in RGB-model from average color.
     *
     * @param backgroundColor average of color.
     * @return integer representation of color.
     */
    default int getColorRGB(int backgroundColor) {
        // Bit-shifts 16 and 8 bits to get the color value of the color formed from the color of one of the channels.
        return backgroundColor << 16 | backgroundColor << 8 | backgroundColor;
    }

}
