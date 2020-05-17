package edu.miracosta.cs113;
/**
 *
 * DetectiveJill.java: This is a main I made that will find the solution detectiveJill is looking for every time in
 * 20 times or under. It uses the assistant jack class provided as well as the scanner class for user
 * input. after my algorithm the output section displays the result of the brute force and if it was successful for
 * those 20 tries.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
import model.AssistantJack;
import java.util.Scanner;

public class DetectiveJill {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Which theory would you like to test Detective Jill? (1, 2, 3(random))");
        int choice = scan.nextInt();
        AssistantJack jack = new AssistantJack(choice);
        int solution;
        int weapon = 1;
        int person = 1;
        int place = 1;

        /**
         * Algorithm!!!!
         *
         * There is no need to start variables at 0 since that would just be wasting a valuable check
         * so start off with all the variables at one
         *
         * Immediately check to see if 1, 1, 1 works for the first check
         *
         * After it fails go on to the next if statements and if the solution return 1 of the wrong answers
         * add one to the answer that is wrong till all the variables are correct.
         *
         * I printed the variables at the end of the do-while loop to keep track of the progress the program makes
         *
         * When the solution breaks out as being 0 for correct the do-while loop stops and continues on
         * to the output to tell the user how successful it was.
         *
         */
        do {
            solution = jack.checkAnswer(weapon, place, person);

            if (solution == 1) {
                weapon++;
            }
            if (solution == 2) {
                place++;
            }
            if (solution == 3) {
                person++;
            }
            System.out.println(weapon + " " + place + " " + person);
        }
        while(solution != 0);


        // OUTPUT
        System.out.println("Total Checks = " + jack.getTimesAsked() );

        if (jack.getTimesAsked() > 20) {
            System.out.println("KO! You're a horrible Detective...");
        } else {
            System.out.println("WOW! You might as well be called Goku!");
        }



    }

}
