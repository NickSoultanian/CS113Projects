package edu.miracosta.cs113;
/**
 * GetProfScores.java takes the abbreviated professor names from the class listings and turns them into
 * full names. These names are theb used to search ratemyprofessor for an overall professor's score.
 * This score is entered into the applicable OfferedClass.
 * The scraping is done using jsoup: https://jsoup.org/packages/jsoup-1.11.2.jar
 * @author Ian Ward
 * @version 1.0
 */

import java.io.*;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetProfScore {
    private static HashMap<String, String> alreadyProcessed = new HashMap<String, String>(); //ensures we don't ping Ratemyprof too much
    private Scanner inputFile, scanner, scanner2;
    ArrayList<String> profs = new ArrayList<String>(1385);
    /**Default constructor that drives the other methods and prints out a finished status to the console
     */
    public GetProfScore(){
        readInProfNames();
        correctNames();
        correctRatings();
        System.out.println("Professor ratings entered");
    }
    /**Pulls a list of full prof names from a file and cleans them up formatting wise
     */
    private void readInProfNames() {
        try {
            inputFile = new Scanner(new File("Profs.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        for (int i = 0; i < 1385; i++) {
            String noComma = inputFile.nextLine().replace(",", "");
            String caps = noComma.toUpperCase();
            caps = caps.replaceAll("'", "'");
            profs.add(caps);
        }
    }
    /**Replaces abbreviated professor names with full names
     */
    private void correctNames() {
        String uncut = null;
        for(int i = 0; i< NonConflictingSchedules.testContainer.size(); i++) { //for each schedule in the container
            LinkedList<NonConflictingSchedules.OfferedClass> newList = NonConflictingSchedules.testContainer.get(i); //create a new schedule
            for(int j = 0; j < newList.size();j++){
                NonConflictingSchedules.OfferedClass currentClass = newList.get(j);
                String currentInstructor = currentClass.getInstructor();
                if(currentInstructor.contains("’")){    //replace italics
                    currentInstructor = currentInstructor.replaceAll("’", "'");
                }
                if(profs.toString().contains(currentInstructor)){ //find all classes with the instructor and sets creates the new name
                    int index = profs.toString().indexOf(currentInstructor);
                    uncut = profs.toString().substring(index-2);
                    String refined = uncut.substring(1);
                    uncut = refined.substring(1, refined.indexOf(","));
                    currentClass.setInstructor(uncut);
                    newList.set(j, currentClass);
                }
            }
            NonConflictingSchedules.testContainer.set(i, newList); //set all classes with prof to the ones with a full name
        }
    }
    /**Updates all of the professor ratings for desired classes
     */
    private void correctRatings(){
        for(int i = 0; i < NonConflictingSchedules.testContainer.size(); i++) {
            LinkedList<NonConflictingSchedules.OfferedClass> newList = NonConflictingSchedules.testContainer.get(i); //create a new schedule
            for(int j = 0; j < newList.size();j++){
                NonConflictingSchedules.OfferedClass currentClass = newList.get(j);
                String currentInstructor = currentClass.getInstructor();
                currentInstructor = currentInstructor.replaceAll(" ", "+"); //this name format is needed to the database search
                currentClass.setRating(getRatings(currentInstructor)); //call helper method to retrieve actual score
                newList.set(j, currentClass);
            }
            NonConflictingSchedules.testContainer.set(i, newList); //sets the new score
        }
    }
    /**Gets the ratings from RateMyProf and returns their string value
     * @param professor String: the full name of desired professor to be updated in caps with a + between first, last and any other name
     * @return String: new professor rating
     */
    private String getRatings(String professor){
        Document doc, doc2;
        String desiredLine = null, link;
        String lookingFor = "<li class=\"listing PROFESSOR\"> <a href=\"/ShowRatings.jsp";
        BufferedWriter writer = null, writer2 = null;
        String search = "http://www.ratemyprofessors.com/search.jsp?queryoption=HEADER&queryBy=teacherName&schoolName=MiraCosta+College&schoolID=2470&query=" + professor;
        String profPage = "http://www.ratemyprofessors.com/ShowRatings.jsp?amp;showMyProfs=true&tid=";
        String rating = "<div class=\"grade\" title=\"\">";

        if(alreadyProcessed.containsKey(professor)){ //ensure professors are not run twice
            return alreadyProcessed.get(professor);
        }
        try {
            doc = Jsoup.connect(search).get(); //connect to RMP and pull html for professor search
            writer = new BufferedWriter(new FileWriter("html.txt")); //write html to a file
            writer.write(doc.toString());
            writer.close();
            scanner = new Scanner(new File("html.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (scanner.hasNextLine()){
            String currentLine = scanner.nextLine(); //scan html file
            currentLine = currentLine.trim();
            if(currentLine.startsWith(lookingFor)){ //look for the href that contains the unique prof ID
                desiredLine = currentLine;
                int indexOfProfNum = desiredLine.indexOf("tid=") + 4;
                link = desiredLine.substring(indexOfProfNum); //save the href
                int endIndex = link.indexOf("\"");
                link = link.substring(0, endIndex);
                profPage = profPage + link; //concat the unique ID with page format for a miracosta instructor
            }
        }
        scanner.close();
        if(desiredLine == null){ //if no prof page is found use avg rating of 3.9
            System.out.println(professor + " not found in RateMyProfessor database");
            alreadyProcessed.put(professor, "3.9");
            return "3.9";
        }
        try {
            doc2 = Jsoup.connect(profPage).get(); //scrape RMP for the unique prof page
            writer2 = new BufferedWriter(new FileWriter("html.txt")); //write to a file
            writer2.write(doc2.toString());
            writer2.close();
              scanner2 = new Scanner(new File("html.txt"));
        } catch (IOException e) {
            System.out.println("no recorded ratings for " + professor + " will use 3.9"); //account for non-rated profs
            alreadyProcessed.put(professor, "3.9");
            return "3.9";
        }
        while (scanner2.hasNextLine()){
            lookingFor = "<div class=\"grade\" title=\"\">"; //find line with overall rating
            String currentLine = scanner2.nextLine().trim();
            if(currentLine.startsWith(lookingFor)){
                String numericRating = scanner2.nextLine().trim(); //clean up the line containing the rating
                scanner2.close();
                alreadyProcessed.put(professor, numericRating); // add rating to map so duplicates aren't searched later on
                return numericRating;
            }
        }
        return "3.9"; //if something slips through return the avg rating
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GetProfScore)) return false;
        GetProfScore that = (GetProfScore) o;
        return Objects.equals(inputFile, that.inputFile) &&
                Objects.equals(scanner, that.scanner) &&
                Objects.equals(scanner2, that.scanner2) &&
                Objects.equals(profs, that.profs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(inputFile, scanner, scanner2, profs);
    }

    @Override
    public String toString() {
        return "GetProfScore{" +
                "profs=" + profs +
                '}';
    }
}
