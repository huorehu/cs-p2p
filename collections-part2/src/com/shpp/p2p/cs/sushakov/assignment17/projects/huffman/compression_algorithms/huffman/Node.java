package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_algorithms.huffman;

/** Abstract node of binary H-tree. */
public abstract class Node implements Comparable<Node> {

    /** Sum of occurrences of sub-nodes */
    private final long sumNumberOccurrencesOfSubNode;

    public Node(long sumNumberOccurrencesOfSubNode) {
        this.sumNumberOccurrencesOfSubNode = sumNumberOccurrencesOfSubNode;
    }

    /** Initiates and creates codes for archiving data */
    public abstract void initCode(String code);

    /** Returns sum of frequency of occurrences for sub-nodes
     * @return sum of frequency of occurrences.
     */
    public long getSumNumberOccurrencesOfSubNode() {
        return sumNumberOccurrencesOfSubNode;
    }

    @Override
    public int compareTo(Node o) {
        return (int) (sumNumberOccurrencesOfSubNode - o.sumNumberOccurrencesOfSubNode);
    }

}
