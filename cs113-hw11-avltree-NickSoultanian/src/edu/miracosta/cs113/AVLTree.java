package edu.miracosta.cs113;


public class AVLTree<E extends Comparable<E>> extends BinarySearchTreeWithRotate<E> {

    private boolean increase;

    private static class AVLNode<E> extends Node<E>{
        public static final int LEFT_HEAVY = -1;
        public static final int BALANCED = 0;
        public static final int RIGHT_HEAVY = 1;
        private int balance;

        //methods
        public AVLNode(E item){
            super(item);
            balance = BALANCED;
        }

        @Override
        public String toString(){
            return super.toString();
        }
    }

    public boolean add(E item){
        increase = false;
        root = add((AVLNode<E>) root, item);
        return addReturn;
    }

    private AVLNode<E> add(AVLNode<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            return new AVLNode<E>(item);
        }
        if (item.compareTo(localRoot.data) == 0) {
            increase = false;
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add((AVLNode<E>) localRoot.left, item);
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < AVLNode.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            return localRoot;
        }
        //what i added
        else {
            // item > data
            localRoot.right = add((AVLNode<E>) localRoot.right, item);
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
                    increase = false;
                    return rebalanceRight(localRoot);
                }

            }
            return localRoot;
        }
    }

    private AVLNode<E> rebalanceLeft(AVLNode<E> localRoot){

        AVLNode<E> leftChild = (AVLNode<E>) localRoot.left;

        if(leftChild.balance > AVLNode.BALANCED) {

            AVLNode leftRightChild = (AVLNode<E>) leftChild.right;

            if (leftRightChild.balance < AVLNode.BALANCED) {
                leftChild.balance = AVLNode.BALANCED;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.RIGHT_HEAVY;
            } else {
                leftChild.balance = AVLNode.LEFT_HEAVY;
                leftRightChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            localRoot.left = rotateLeft(leftChild);
        } else {
            leftChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }

        return (AVLNode<E>) rotateRight(localRoot);
    }

    private AVLNode<E> rebalanceRight(AVLNode<E> localRoot){
        AVLNode<E> rightChild = (AVLNode<E>) localRoot.right;

        if(rightChild.balance < AVLNode.BALANCED) {

            AVLNode rightLeftChild = (AVLNode<E>) rightChild.left;

            if (rightLeftChild.balance > AVLNode.BALANCED) {
                rightChild.balance = AVLNode.BALANCED;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.LEFT_HEAVY;
            }
            else {
                rightChild.balance = AVLNode.RIGHT_HEAVY;
                rightLeftChild.balance = AVLNode.BALANCED;
                localRoot.balance = AVLNode.BALANCED;
            }

            localRoot.right = rotateRight(rightChild);
        } else {
            rightChild.balance = AVLNode.BALANCED;
            localRoot.balance = AVLNode.BALANCED;
        }

        return (AVLNode<E>) rotateLeft(localRoot);
    }

    private void decrementBalance(AVLNode<E> node){
        node.balance--;
        if(node.balance == AVLNode.BALANCED){
            increase = false;
        }
    }
    private void incrementBalance(AVLNode<E> node){
        node.balance++;
        if(node.balance == AVLNode.BALANCED){
            increase = true;
        }
    }









}
