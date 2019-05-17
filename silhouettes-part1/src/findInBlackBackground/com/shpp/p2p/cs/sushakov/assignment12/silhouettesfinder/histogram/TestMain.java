package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.histogram;

import findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.PreparedImage;
import findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.SilhouettesFinder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestMain {

    public static void main(String[] args) {

        String imagePath = "resources/shpp_standard.jpg";
        if (args.length != 0) {
            imagePath = args[0];
        }

        PreparedImage image2 = null;
        BufferedImage bufferedImage = null;

        try {
            image2 = new PreparedImage(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
            bufferedImage = new BufferedImage(image2.getWidth(), image2.getHeight(), BufferedImage.TYPE_INT_RGB);

            int[][] pixels = HisogramEqualizationTransform.toGrayScale(image2);
            int[][] imageLuminance = HisogramEqualizationTransform.imageToLuminances(pixels);
            int[][] imageG = HistogramEqualizationLogic.equalize(imageLuminance);
//        int[][] imageG = new int[image2.getHeight()][image2.getWidth()];

        for (int i = 0; i < image2.getHeight(); i++) {
            for (int j = 0; j < image2.getWidth(); j++) {
                imageG[i][j] = image2.getImageInArray()[i][j].getColorRGB();
                System.out.println("average = " + imageG[i][j]);
            }
        }
            JFrame frame = new JFrame("SilhouettesFinder");
            frame.setSize(image2.getImageInArray()[0].length, image2.getImageInArray().length);
            frame.setVisible(true);
            System.out.println(image2.getImageInArray()[0].length);
            System.out.println(image2.getImageInArray().length);
            Graphics graphics = frame.getContentPane().getGraphics();
            for (int i = 0; i < image2.getImageInArray().length; i++) {
                for (int j = 0; j < image2.getImageInArray()[0].length; j++) {
                    bufferedImage.setRGB(j, i, imageG[i][j]);

                }
            }
//            bufferedImage.setRGB(0, 0, image2.getWidth(), image2.getHeight(), image, 0, image2.getWidth());
        try {
            ImageIO.write(bufferedImage, "jpg", new File("resources/newTest1.jpg"));
        } catch (IOException e) {
            System.out.println("5665765761");
            e.printStackTrace();
        }
        System.out.println("Write in file: Ok");

        SilhouettesFinder finder = new SilhouettesFinder();

        int amountSilhouettes = finder.findSilhouettes(image2);
        System.out.println("Amount of silhouettes: " + amountSilhouettes);
        System.out.println("Analyze: Ok");

        System.out.println("Sizes of sihouettes:");
        finder.getSilhouetteList().forEach(System.out::println);
    }

}
