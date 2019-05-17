package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PreparedImage {

    private BufferedImage bufferedImage;
    private Pixel[][] imageInArray;

    private int width;
    private int height;

    public PreparedImage(String imagePath) throws IOException {
        this.bufferedImage = ImageIO.read(new File(imagePath));
        prepareImage();
    }

    /**  */
    private void prepareImage() {
        imageInArray = new Pixel[bufferedImage.getHeight()][bufferedImage.getWidth()];
        int[] image = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(),
                null, 0, bufferedImage.getWidth());

        this.width = imageInArray[0].length;
        this.height = imageInArray.length;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int color = 0;
                int tempRed = (image[i * width + j] >> 16) & 0xff;
                int tempGreen = (image[i * width + j] >> 8) & 0xff;
                int tempBlue = image[i * width + j] & 0xff;

                int average = (tempRed + tempGreen + tempBlue) / 3;
                System.out.println("red = " + tempRed + " green = " + tempGreen + " blue = " + tempBlue + " average = " + average);
                if (average > 125) {
                    color = -1;
                }
                imageInArray[i][j] = new Pixel(color);
            }
        }
    }

    public boolean isPixelsEquals(int firstPixelX, int firstPixelY, int secondPixelX, int secondPixelY) {
        return imageInArray[firstPixelY][firstPixelX].compareTo(imageInArray[secondPixelY][secondPixelX]) == 0;
    }

    public boolean isPixelsEquals(Pixel pixel, int secondPixelX, int secondPixelY) {
        return pixel.compareTo(imageInArray[secondPixelY][secondPixelX]) == 0;
    }

    public void drawPixel(int pixelX, int pixelY, int colorRGB) {
        imageInArray[pixelY][pixelX] = new Pixel(colorRGB);
    }

    public Pixel[][] getImageInArray() {
        return imageInArray;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
