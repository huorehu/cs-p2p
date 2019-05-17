package com.shpp.p2p.cs.sushakov.assignment15.compression_algorithms.huffman;

import com.shpp.p2p.cs.sushakov.assignment15.compression_tool.AbstractCoder;

import java.io.IOException;
import java.util.*;

/** Contains codes of alphabet for compressing data. */
public class HuffmanCoder extends AbstractCoder {

    public HuffmanCoder(String filePath) throws IOException {
        super(filePath);
    }

    /**
     * Creates coding alphabet.
     *
     * @param filePath the path to file based on the contents of which an alphabet will be built
     *                to encode bytes based on the frequency of occurrences of each of the bytes.
     */
    @Override
    protected Map<Byte, String> getCodes(String filePath) throws IOException {
        long[] byteFrequency = getByteFrequency(filePath);

        Queue<Node> nodesForBinaryTree = new PriorityQueue<>();

        for (int i = 0; i < byteFrequency.length; i++) {
            if (byteFrequency[i] > 0) {
                nodesForBinaryTree.offer(new ByteNode((byte) i, byteFrequency[i]));
            }
        }

        return createsCodes(nodesForBinaryTree);
    }

    /**
     * Creates disjoint bit sequences for a finite set of non-repeating bytes.
     *
     * @param nodesForBinaryTree the finite queue of non-repeating bytes.
     */
    private Map<Byte, String> createsCodes(Queue<Node> nodesForBinaryTree) {
        Map<Byte, String> codes = new HashMap<>();
        /* Stores list of leafs nodes for the subsequent
           extraction of codes from them. */
        ByteNode[] leafs = new ByteNode[nodesForBinaryTree.size()];

        int i = 0;
        for (Node node : nodesForBinaryTree) {
            leafs[i] = (ByteNode) node;
            i++;
        }

        Node treeOfCodes = createCodesTree(nodesForBinaryTree);
        treeOfCodes.initCode("");
        ByteNode currentNode;
        for (ByteNode node : leafs) {
            currentNode = node;
            codes.put(currentNode.getEncodedByte(), currentNode.getCode());
        }

        return codes;
    }

    /**
     * Creates binary tree for init codes for bytes.
     * @param nodesForBinaryTree queue of occurrence of original bytes.
     * @return the root of the tree storing references to subtrees of codes.
     */
    private Node createCodesTree(Queue<Node> nodesForBinaryTree) {
        while (nodesForBinaryTree.size() > 1) {
            Node leftNode = nodesForBinaryTree.poll();
            Node rightNode = nodesForBinaryTree.poll();
            nodesForBinaryTree.offer(new IntermediateNode(leftNode, rightNode));
        }

        return nodesForBinaryTree.poll();
    }

}
