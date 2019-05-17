package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_algorithms.huffman;

/** This class is an intermediate node of H-tree. */
public class IntermediateNode extends Node {

    /** Left node of sub-tree of H-tree */
    private Node leftNode;
    /** Right node of sub-tree of H-tree */
    private Node rightNode;

    public IntermediateNode(Node leftNode, Node rightNode) {
        super(rightNode.getSumNumberOccurrencesOfSubNode() + leftNode.getSumNumberOccurrencesOfSubNode());
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    /**
     * Initiates and creates codes for archiving data.
     * @param code the cumulative number of bits from the root of H-tree.
     */
    public void initCode(String code) {
        leftNode.initCode(code + "0");
        rightNode.initCode(code + "1");
    }

}
