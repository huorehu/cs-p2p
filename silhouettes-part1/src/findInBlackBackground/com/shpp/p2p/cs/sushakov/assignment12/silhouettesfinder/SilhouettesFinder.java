package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

import java.util.ArrayList;
import java.util.List;

public class SilhouettesFinder {

    private PreparedImage preparedImage;

    public List<Silhouette> getSilhouetteList() {
        return silhouetteList;
    }

    private List<Silhouette> silhouetteList;

    private int count;

    /**
     * Finding silhouettes of the people in the prepared image and returns their amount.
     *
     * @param image the prepared image for finding.
     * @return the amount of people on the image.
     */
    public int findSilhouettes(PreparedImage image) {
        preparedImage = image;
        silhouetteList = new ArrayList<>();

        for (int i = 0; i < preparedImage.getHeight(); i++) {
            for (int j = 0; j < preparedImage.getWidth(); j++) {
                if (j == 0) {
                    continue;
                }
                if (!preparedImage.isPixelsEquals(j, i, j - 1, i)) {
                    System.out.println("i = " + i + " j = " + j);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    addSilhouette(j, i);
                }
            }
        }
        int amountOfSilhouettes = 0;
        for (Silhouette sil : silhouetteList) {
            if (sil.getAmountPixels() > 320) {
                amountOfSilhouettes++;
            }
        }
        return amountOfSilhouettes;
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
        filling(startX, startY, silhouette, preparedImage.getImageInArray()[startY][startX - 1].getColorRGB());
    }

    /**
     * Filling the area with a silhouette with an untouched color.
     * In the filling of the silhouette used the depth-first algorithm.
     *
     *  @param startX starting X point for filling.
     * @param startY starting Y point for filling.
     * @param silhouette
     */
    private void filling(int startX, int startY, Silhouette silhouette, int fillerColor) {
        System.out.println("count " + count++ + " x = " + startX + " y = " + startY + " currentColor = " + preparedImage.getImageInArray()[startY][startX].getAvaregeColor() + " fillColor = " + (fillerColor >> 16 & 0xff));
        System.out.println("previews color = " + preparedImage.getImageInArray()[startY][startX - 1].getAvaregeColor());
        silhouette.addPixel();
        Pixel currentPixel = preparedImage.getImageInArray()[startY][startX];
        preparedImage.drawPixel(startX, startY, fillerColor);

        if (startY - 1 > 0 && preparedImage.isPixelsEquals(currentPixel, startX, startY - 1)) {
            filling(startX, startY - 1, silhouette, fillerColor);
        }
        if (startX + 1 < preparedImage.getWidth() && preparedImage.isPixelsEquals(currentPixel, startX + 1, startY)) {
            filling(startX + 1, startY, silhouette, fillerColor);
        }
        if (startY + 1 < preparedImage.getHeight() && preparedImage.isPixelsEquals(currentPixel, startX, startY + 1)) {
            filling(startX, startY + 1, silhouette, fillerColor);
        }
        if (startX - 1 > 0 && preparedImage.isPixelsEquals(currentPixel, startX - 1, startY)) {
            filling(startX - 1, startY, silhouette, fillerColor);
        }
    }

}
