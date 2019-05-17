package com.shpp.p2p.cs.sushakov.assignment17.projects.silhouettes.silhouettesfinder;

/** Silhouette of object that has monophonic color. */
public class Silhouette {

    /** Amount of a silhouette's pixels */
    private int amountPixels;

    /** Increases the number of pixels of the silhouette by one. */
    public void addPixel() {
        amountPixels++;
    }

    /** @return amount pixels of the silhouette. */
    public int getAmountPixels() {
        return amountPixels;
    }

    /** String representation of the current object. */
    @Override
    public String toString() {
        return Integer.toString(amountPixels);
    }

}
