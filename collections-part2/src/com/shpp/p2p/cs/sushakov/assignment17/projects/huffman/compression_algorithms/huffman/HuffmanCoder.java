package com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_algorithms.huffman;

import com.shpp.p2p.cs.sushakov.assignment17.collections.HashMapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.PriorityQueueP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.MapP2P;
import com.shpp.p2p.cs.sushakov.assignment17.collections.interfaces.QueueP2P;
import com.shpp.p2p.cs.sushakov.assignment17.projects.huffman.compression_tool.AbstractCoder;

import java.io.IOException;

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
    protected MapP2P<Byte, String> getCodes(String filePath) throws IOException {
        long[] byteFrequency = getByteFrequency(filePath);

        QueueP2P<Node> nodesForBinaryTree = new PriorityQueueP2P<>(Node.class);

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
    private MapP2P<Byte, String> createsCodes(QueueP2P<Node> nodesForBinaryTree) {
        MapP2P<Byte, String> codes = new HashMapP2P<>();
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
    private Node createCodesTree(QueueP2P<Node> nodesForBinaryTree) {
        while (nodesForBinaryTree.size() > 1) {
            Node leftNode = nodesForBinaryTree.poll();
            Node rightNode = nodesForBinaryTree.poll();
            nodesForBinaryTree.offer(new IntermediateNode(leftNode, rightNode));
        }

        return nodesForBinaryTree.poll();
    }

}
