package edu.miracosta.cs113;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a value");
        int user = scan.nextInt();
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();
        list.add(user);
        user = scan.nextInt();
        list.add(user);
        user = scan.nextInt();
        list.add(user);
        user = scan.nextInt();
        list.add(user);

        System.out.println(list.toString());


    }
}
