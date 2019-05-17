package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.silhouettesfinder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Prepared image to search for silhouettes. The preparation consists
 * in finding the average of the three color channels for each pixel
 * with further reduction to black (0) or white (255) color.
 */
public class PreparedImage {

    /** Semitone between absolutely black and absolutely white color */
    private static final int SEMITONE = 127;

    /** The image presented in the format of an array of pixels */
    private Pixel[][] imageInArray;

    /** Width of the image */
    private int width;
    /** Height of the image */
    private int height;

    public PreparedImage(String imagePath) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        prepareImage(bufferedImage);
    }

    /**
     * Image transformation for processing in the appropriate format.
     *
     * @param bufferedImage the image that represented as {@link BufferedImage}.
     */
    private void prepareImage(BufferedImage bufferedImage) {
        imageInArray = new Pixel[height][width];
        int[] image = bufferedImage.getRGB(0, 0, width, height, null, 0, width);

        int color;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (Colors.getAverageOfColor(image[y * width + x]) > SEMITONE) {
                    color = Colors.WHITE;
                } else {
                    color = Colors.BLACK;
                }
                imageInArray[y][x] = new Pixel(color, x, y);
            }
        }
    }

    /** @return the array representation of processed image. */
    public Pixel[][] getImageInArray() {
        return imageInArray;
    }

}
