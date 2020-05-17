package edu.miracosta.cs113;
/**
 * AVLDriver.java : Makes use of the BinarySearchTree and the AVLTree Data Structures. Randomly generate 20 or so
 * numbers and add them to avl and bst trees. print them out using the toString method to compare their design side by
 * side. use a height method to figure out the height of both trees to compare the heights.
 *
 * @author Nick Soultanian
 * @version 1.0
 *
 */
import java.util.Random;
import java.util.Scanner;

public class AVLDriver {
    public static void main(String[] args) {
        Integer[] randomNumbers = new Integer[26];
        Random random = new Random();
        for (int i = 0; i < randomNumbers.length; i++) {
            randomNumbers[i] = random.nextInt(50) + 1;
        }

        for (int i = 0; i < randomNumbers.length; i++) {
            System.out.print("   " + randomNumbers[i]);
        }

        System.out.println();
        System.out.println("What I am adding to both the avl and bst trees");
        System.out.println("====================================================================================");
        System.out.println("AVL Tree");


        AVLTree<Integer> avl = new AVLTree<Integer>();

        for (int i = 0; i < randomNumbers.length ; i++) {
            avl.add(randomNumbers[i]);
        }
        String avlString = avl.toString();

        System.out.println(avlString);

        System.out.println("====================================================================================");

        //----------------------------------------------------- BinarySearchTree
        System.out.println("BST Tree");

        BinarySearchTree<Integer> bst = new BinarySearchTree<Integer>();
        for (int i = 0; i < randomNumbers.length; i++) {
            bst.add(randomNumbers[i]);
        }
        String  bstString = bst.toString();
        System.out.println(bstString);
        System.out.println("====================================================================================");
        System.out.println("HEIGHT DIFFERENCES BETWEEN THE TWO TREES");
        System.out.println("AVL height: " + height(avlString));
        System.out.println("BST height: " + height(bstString));

    }

    public static int height(String sb){
        Scanner scan = new Scanner(sb);
        int heightCount = 0;
        while(scan.hasNext()) {
            String a = scan.nextLine();
            int counter = 0;
            for(int i = 0; i < a.length()-1; i++) {
                if (a.charAt(i) == 32){
                    counter++;
                }
                else if(a.charAt(i) != 32){
                    if(counter > heightCount){
                        heightCount = counter;
                    }
                    counter = 0;
                }

            }
        }
        scan.close();
        return heightCount;
    }
}
