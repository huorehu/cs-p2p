package findInBlackBackground.com.shpp.p2p.cs.sushakov.assignment12.silhouettesfinder;

public class Silhouette {

    private int amountPixels;

    public void addPixel() {
        amountPixels++;
    }

    public int getAmountPixels() {
        return amountPixels;
    }

    @Override
    public String toString() {
        return Integer.toString(amountPixels);
    }

}
