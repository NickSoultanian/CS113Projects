package edu.miracosta.cs113;

/**
 *  MorseCodeMain.java: Tester that runs a binary tree of morse code and translates
 *
 *  class invariant: numbers like 0 and negative numbers are checked for, everything else goes through as a fail or done.
 *
 *
 * @author			 Nick Soultanian
 * @date 			 8/30/17
 * @version          1.0 Lab 2
 *

 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class MorseCodeMain {

    private static MorseCodeTree<String> tree = new MorseCodeTree<String>();
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        File input = new File("src/edu/miracosta/cs113/MorseKey.txt");


        try{
            scan = new Scanner(input);
            System.out.println(tree.readBinaryTree(scan));
            scan.close();
        }
        catch(FileNotFoundException e){
            System.out.println("didnt work");
        }


    }

}