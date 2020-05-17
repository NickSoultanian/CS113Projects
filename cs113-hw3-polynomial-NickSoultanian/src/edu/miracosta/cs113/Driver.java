package edu.miracosta.cs113;
/**
 *
 * Driver.java: This is a main I made that will take the classes Polynomial.java and Term.java and create polynomials
 * using the LinkedList Class from java. It uses switch statements to go to different menu functions and runs on a
 * while loop to continuously run till the user decides to quit.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
import java.util.Scanner;

public class Driver {
    public static void main(String[] args){
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial();
        Scanner scan = new Scanner(System.in);
        int on = 0;
        while(on == 0) {
            System.out.println("what would you like to do?");
            System.out.println("       1. Edit first polynomial(clear, create, and add terms");
            System.out.println("       2. Edit second polynomial(clear, create, and add terms");
            System.out.println("       3. Display the result of adding the current first and second polynomials(without deleting/ modifying those two polynomials.");
            System.out.println("       4. Exit the program");
            int userInput = scan.nextInt();
            if (userInput > 4 || userInput < 1){
                System.out.println("oops please try to keep to the options in front of you. Try Again");
                userInput = scan.nextInt();
            }
                switch (userInput) {
                //First polynomial
                    case 1:
                        System.out.println("What would you like to do?");
                        System.out.println("         1. Clear");
                        System.out.println("         2. Create");
                        System.out.println("         3. Add terms");
                        userInput = scan.nextInt();
                        switch(userInput) {
                            case 1:
                                System.out.println("Polynomial 1 has been cleared");
                                poly1.clear();
                                break;
                            case 2:
                                poly1.toString();
                                break;
                            case 3:
                                System.out.println("please enter the term you want to enter to the polynomial!");
                                String user = scan.next();
                                Term term = new Term(user);
                                poly1.addTerm(term);
                                break;
                        }
                        break;
                 //second polynomial
                    case 2:
                        System.out.println("What would you like to do?");
                        System.out.println("         1. Clear");
                        System.out.println("         2. Create");
                        System.out.println("         3. Add terms");
                        userInput = scan.nextInt();
                        switch(userInput) {
                            case 1:
                                System.out.println("Polynomial 2 has been cleared");
                                poly2.clear();
                                break;
                            case 2:
                                poly2.toString();
                                break;
                            case 3:
                                System.out.println("please enter the term you want to enter to the polynomial!");
                                String user = scan.next();
                                Term term = new Term(user);
                                poly2.addTerm(term);
                                break;
                        }
                        break;
                    case 3:
                        poly1.add(poly2);
                        //add two polynomials
                        break;
                    case 4:
                        on = 1;
                        break;
                    case 5:
                        poly1.toString();
                }
        }
    }
}
