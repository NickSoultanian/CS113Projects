package edu.miracosta.cs113;
import java.util.Scanner;

public class MorseCodeTree<E> extends BinaryTree<E> {


    public MorseCodeTree(Node<E> first){
        super(first);
    }

    public MorseCodeTree(E data, BinaryTree<E> leftTree, BinaryTree<E> rightTree){
        super(data, leftTree, rightTree);
    }

    public MorseCodeTree(){
        super();
    }

    public static BinaryTree<String> readBinaryTree(Scanner scan){
        //String data = scan.next();
        if (scan.equals("null")) {
            return null;
        }
        scan.useDelimiter("\\s");
        String letter = scan.next();
        String code = scan.next();

        BinaryTree<String> leftTree = null;
        BinaryTree<String> rightTree = null;

        //System.out.println(letter + " " + code );
        if( code.charAt(0) == '*'){
            leftTree = readBinaryTree(scan);
        }
        else if(code.charAt(0) == '-'){
            rightTree = readBinaryTree(scan);
        }

        return new BinaryTree<String>(letter, leftTree, rightTree);





    }



}

