package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12;

import findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.PreparedImage;
import findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder.SilhouettesFinder;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        PreparedImage image = null;
        try {
            image = new PreparedImage("resources/silhouette16.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SilhouettesFinder finder = new SilhouettesFinder();

        int amountSilhouettes = finder.findSilhouettes(image);
        System.out.println("Amount of silhouettes: " + amountSilhouettes);

        JFrame frame = new JFrame("SilhouettesFinder");
        frame.setSize(image.getImageInArray()[0].length, image.getImageInArray().length);
        frame.setVisible(true);
        System.out.println(image.getImageInArray()[0].length);
        System.out.println(image.getImageInArray().length);
        Graphics graphics = frame.getContentPane().getGraphics();

        for (int i = 0; i < image.getImageInArray().length; i++) {
            for (int j = 0; j < image.getImageInArray()[0].length; j++) {
                if (image.getImageInArray()[i][j].getRed() < 50 &&
                        image.getImageInArray()[i][j].getGreen() < 50 &&
                        image.getImageInArray()[i][j].getBlue() < 50) {
                    graphics.drawRect(j, i, 0, 0);
                }
//                if (image.getImageInArray()[i][j].getAvaregeColor() < 100) {
//                    graphics.drawRect(j, i, 0, 0);
//                }
            }
        }
    }

}
