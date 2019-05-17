package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.histogram;

import findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.PreparedImage;

public class HisogramEqualizationTransform {

    public static int[][] toGrayScale(PreparedImage image) {
        int[][] result = new int[image.getHeight()][image.getWidth()];

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int intensity = (int) (0.3D * (double) image.getImageInArray()[i][j].getRed() + 0.59D * (double) image.getImageInArray()[i][j].getGreen() + 0.11D * (double) image.getImageInArray()[i][j].getBlue() + 0.5D);
                result[i][j] = (intensity << 16 & 0xff) | (intensity << 8 & 0xff) | (intensity & 0xff);
            }
        }
        return result;
    }

    public static int[][] imageToLuminances(int[][] pixels) {
        int[][] luminances = new int[pixels.length][pixels[0].length];

        for (int row = 0; row < pixels.length; ++row) {
            for (int col = 0; col < pixels[row].length; ++col) {
                luminances[row][col] = pixels[row][col] >> 16 & 0xff;
            }
        }

        return luminances;
    }

}
