package edu.miracosta.cs113;
/**
 *
 * Recursion.java: This is a class that uses recursion so solve a change problem.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

public class RecursionMain {
    public static ArrayList<int[]> savedTheGame = new ArrayList<int[]>(10);
    public static int index;
    public static void main(String[] args) {

        // figure how much change is needed
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the change that you want. (ex. 0.20)");
        double userChange = scan.nextDouble() * 100;
        int userInt = (int)userChange;
        System.out.println(userInt);
        scan.close();

        int[] savedChange = new int[3];
        String[] coin = new String[]{" Quarters: ", " Dimes: ", " Nickles: ", " Pennies: "};



        recursion(userInt, 0,0,0,userInt);

        //need to delete duplicates

        if(savedTheGame.isEmpty()){
            System.out.println("whytho");;
        }

        for(int i = 0; i < savedTheGame.size(); i++){
            savedChange = savedTheGame.get(i);
            for(int j = 0; j <= 3; j++){
                System.out.print(coin[j] + savedChange[j]);
            }
            System.out.println();
        }
        System.out.println(index);




    }

    public static void recursion(int change, int q, int d, int n, int p){
        if(q * 25 + d * 10 + n * 5 + p == change){
            //System.out.println("bruh");
            save(q,d,n,p);
        }

        index++;

        //System.out.println("hey");
        if(p >= 5){
            recursion(change, q, d, n + 1 , p - 5);
        }
        if(p >= 10){
            recursion(change, q, d + 1, n , p - 10);

        }
        if(p >= 25){
            recursion(change, q + 1, d, n , p - 25);
        }



    }

    public static void save(int q, int d, int n, int p){
        int[] savedChange = new int[]{q, d, n, p};

        //System.out.println("yo");
        savedTheGame.add(savedChange);
    }


}