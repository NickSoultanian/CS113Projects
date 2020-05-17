package edu.miracosta.cs113;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class HuffmanTree implements Serializable{

    public static class HuffData implements Serializable{
        private double weight;
        private Character symbol;

        public HuffData(double weight, Character symbol) {
            this.weight = weight;
            this.symbol = symbol;
        }
        public String getData(){
            return " weight: " + weight + "       Symbol: " + symbol;
        }
    }

    private BinaryTree<HuffData> huffTree;

    private static class CompareHuffmanTrees implements Comparator<BinaryTree<HuffData>> {
        public int compare(BinaryTree<HuffData> treeLeft, BinaryTree<HuffData> treeRight){
            double wLeft = treeLeft.getData().weight;
            double wRight = treeRight.getData().weight;
            return Double.compare(wLeft, wRight);
        }
    }

    public void buildTree(HuffData[] symbols) {
        Queue<BinaryTree<HuffData>> theQueue = new PriorityQueue<BinaryTree<HuffData>>(symbols.length, new CompareHuffmanTrees());
        for (HuffData nextSymbol : symbols) {
            BinaryTree<HuffData> aBinaryTree = new BinaryTree<HuffData>(nextSymbol, null, null);
            theQueue.offer(aBinaryTree);
        }

        while(theQueue.size() > 1) {
            BinaryTree<HuffData> left = theQueue.poll();
            BinaryTree<HuffData> right = theQueue.poll();
            double wl = left.getData().weight;
            double wr = right.getData().weight;
            HuffData sum = new HuffData(wl + wr, null);
            BinaryTree<HuffData> newTree = new BinaryTree<HuffData>(sum, left, right);
            theQueue.offer(newTree);
        }

        huffTree = theQueue.poll();
    }

    /**
     * Wrapper class to help the print code recursion method. just sets the tree and default string code.  works really well
     * @param out which is the print steram used
     */
    public void printCode(PrintStream out){
        printCode(out, "", huffTree );
    }
    private void printCode(PrintStream out, String code, BinaryTree<HuffData> tree){
        HuffData theData = tree.getData();
        if (theData.symbol != null) {
            if (theData.symbol.equals(' ')) {
                out.println("space: " + code);
            } else {
                out.println(theData.symbol + ": " + code);
            }
        } else {
            printCode(out, code + "0", tree.getLeftSubtree());
            printCode(out, code + "1", tree.getRightSubtree());
        }
    }

    public String decode(String codedMessage) {
        StringBuilder result = new StringBuilder();
        BinaryTree<HuffData> currentTree = huffTree;
        for (int i = 0; i < codedMessage.length(); i++) {
            if (codedMessage.charAt(i) == '1') {
                currentTree = currentTree.getRightSubtree();
            } else {
                currentTree = currentTree.getLeftSubtree();
            }
            if (currentTree.isLeaf()) {
                HuffData theData = currentTree.getData();
                result.append(theData.symbol);
                currentTree = huffTree;
            }
        }
        return result.toString();
    }
}
