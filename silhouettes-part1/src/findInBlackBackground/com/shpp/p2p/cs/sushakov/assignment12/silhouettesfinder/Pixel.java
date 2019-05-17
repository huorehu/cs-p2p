package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

/** Simple pixel of image that contains it color in different representations */
public class Pixel implements Comparable {

    /** Integer representation of pixel's color in RGB-model */
    private final int colorRGB;

    /** Red channel of the pixel's color */
    private final int red;
    /** Green channel of the pixel's color */
    private final int green;
    /** Blue channel of the pixel's color */
    private final int blue;
    /** Average of the pixel's color */
    private final int averageColor;

    public Pixel(int colorRGB) {
        this.colorRGB = colorRGB;
        red = colorRGB >> 16 & 0xFF;
        green = colorRGB >> 8 & 0xFF;
        blue = colorRGB & 0xFF;
        averageColor = (red + green + blue) / 3;
    }

    /** @return integer representation of pixel's color in RGB-model */
    public int getColorRGB() {
        return colorRGB;
    }

    /** @return integer representation of the red channel of pixel's color */
    public int getRed() {
        return red;
    }

    /** @return integer representation of the green channel of pixel's color */
    public int getGreen() {
        return green;
    }

    /** @return integer representation of the blue channel of pixel's color */
    public int getBlue() {
        return blue;
    }

    /** @return average of the pixel's color */
    public int getAvaregeColor() {
        return averageColor;
    }

    /**
     * Compares whether the current pixel is equal to the one received as an argument
     * by comparing the average of their three color channels: red, green, blue.
     *
     * @param pixel the pixel compared to the current one
     * @return if the pixels are equal then 0;
     *         if the pixels are not equal then -1.
     */
    @Override
    public int compareTo(Object pixel) {
//        if (!(pixel instanceof Pixel)) {
//            return -1;
//        }
//        if ((Math.abs(this.getAverageColor() - ((Pixel) pixel).getAverageColor())) > 30) {
//            return -1;
//        }
        if (this.getAvaregeColor() == ((Pixel) pixel).getAvaregeColor()) {
            return 0;
        }
        return -1;
    }

}
