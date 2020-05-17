package edu.miracosta.cs113;
/**
 * AvailableClasses.java takes all of the available classes and removes those that are not offered at Oceanside or Online.
 * The class listings are also formatted so that their string representations are all formatted the same
 * @author Ian Ward
 * @version 1.0
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class AvailableClasses {
    private Scanner inputFile;
    private String rawLine = "", className, location = "ANYWHERE", classDetails, classNumber;
    public static ArrayList<String> array = new ArrayList<String>(1000);
    /**
     * Default constructor drives methods to non-desired schedules. Prints
     * to console when finished
     */
    public AvailableClasses(){
        try {
            inputFile = new Scanner(new File("final.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        generatelistings();
        correctForRecursion();
        fixScheduleConflicts();
        correctForTwoDays();
        cleanUpArrayFormatting();
        System.out.println("Created an array of all offered classes");
    }
    /**
     * Pulls and formats classes that are only offered at Oceanside or online
     */
    private void generatelistings(){
            if(inputFile.hasNextLine()) {
                rawLine = inputFile.nextLine();
            }else{
                return;
            }
            if(rawLine.startsWith("*")){ //finds class names
                parseClassName(rawLine);
                generatelistings();
            }
            if(rawLine.startsWith("-")){ //Pulls only those classes from oceanside or online
                if(rawLine.charAt(2) == 'N'){
                    location = "ONLINE";
                }else if(rawLine.charAt(2) == 'C'){
                    location = "OCEANSIDE";
                }
                generatelistings(); //helper method further refines each class
            }
        if (Character.isDigit(rawLine.charAt(0)) ){ //stores all valid refined classes
            classDetails = className + "," + location + "," + rawLine;
            classNumber = rawLine.substring(0,4) + " ";
            array.add(classDetails);
            generatelistings();
        }
        if (rawLine.charAt(0) == '&'){ //accounts for class listings that are more than 1 line in length
            classDetails = className + "," + location + "," + classNumber + rawLine;
            array.add(classDetails);
            generatelistings();
        }
    }
    /**
     * Pulls the classname (ex: CS 111) from the class listing
     * @param line String: a single class listing line that contains a classname
     */
    private void parseClassName(String line){
        int index, secondIndex;
        line = line.substring(1);
        index = line.indexOf(" ");
        String number = line.substring(index + 1);
        secondIndex = number.indexOf(" ");
        className = line.substring(0, (index + secondIndex + 2));
    }
    /**
     * Removes a certain set of classes that were necessarily duplicated by the recursive
     * method that parsed each class
     */
    private void correctForRecursion() {
        String temp = null;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).contains("2087")) { //2087 was the class number that was read in several times
                temp = array.get(i);
                array.set(i, null); //remove any additional class 2087
            }
        }
        array.add(array.indexOf(null), temp);
        while(array.remove(null));
    }
    /**
     * A few formatting errors allowed San Elijo classes to be processed.
     * This method removes those classes
     */
    private void fixScheduleConflicts(){
        for(int i = 0; i < array.size(); i++){
            String temp = array.get(i);
            String temp2 = temp.substring(temp.length()-10);
            if(temp2.contains("SAN")){ //remove classes in san elijo
                array.set(i, null);
            }
            if(temp.contains("& ONL")){ //remove classes that are split between san elijo and online
                array.set(i, null);
            }
        }
        while(array.remove(null));
    }
    /**
     * Correct schedule entries that are offered on multiple different days at different times
     * (ex MW 3-4 & T 4-5)
     */
    private void correctForTwoDays(){
        for(int i = 1; i < array.size() - 1; i++){
            String temp = array.get(i);
            String classNeedAppend = array.get(i-1);
            if(temp.contains("&")) { //find all entries with multiple days
                int index = temp.indexOf("&");
                int indexOfLoc = temp.indexOf("OC ");
                String partial = temp.substring(index + 1, indexOfLoc) + " & ";
                int commaIndex = classNeedAppend.lastIndexOf(",") + 8; //8 is for the 4 digit class #
                String firstHalf = classNeedAppend.substring(0, commaIndex);//find the time offerings for the seperate days
                String secondHalf = classNeedAppend.substring(commaIndex, classNeedAppend.length());
                classNeedAppend = firstHalf + partial + secondHalf; //create new schedule to include both class times in a single line
                array.set(i - 1, classNeedAppend);
                array.set(i, null);
            }
        }
        while(array.remove(null));
    }
    /**
     * Ensures that each class that had been refined follows the same format
     */
    private void cleanUpArrayFormatting(){

        for(int i = 0; i < array.size(); i++){
            StringBuilder lineBuilder = new StringBuilder();
            String regex = "\\d{4}";
            String timeRegex = "\\d\\s.*\\d\\s\\w";
            String line = array.get(i);
            line = line.replaceAll("\\s{2,}", " ").trim(); //remove long spaces
            int indexOfCredits = line.indexOf(".");
            if(indexOfCredits != -1){
                line = line.substring(0, indexOfCredits - 1); //remove credits
            }
            if(indexOfCredits == -1){
                line = line.substring(0, line.length()-1); //removed poorly entered credits
            }
            if(line.contains(" ONL")){
                line = line.replace(" ONL", ""); //remove extra online locations
            }
            if(line.contains(" ,")){ //remove space after class name
                line = line.replaceAll(" ,", ",");
            }
            if(line.contains("ARRANGED")){ //append comma for Arranged times
                line = line.replaceAll(" ARRANGED", " ARRANGED,");
            }
            if(line.contains(" Daily")){ //append comma for Daily times
                line = line.replaceAll(" Daily", "Daily");
            }
            Pattern classNum = Pattern.compile(regex);
            Pattern classTime = Pattern.compile(timeRegex);
            Matcher classTimeMatcher = classTime.matcher(line);
            Matcher classNumMatcher = classNum.matcher(line); // matcher method looks for all occurances of classNum in line
            if(classNumMatcher.find()){ //if match is found
                lineBuilder.append(line);
                lineBuilder.insert(classNumMatcher.end(), ',');
            }
            if(classTimeMatcher.find()){
                lineBuilder.insert((classTimeMatcher.end() +  2) ,",");
            }
            if(line.contains("ONLINE,")){
                int where = lineBuilder.lastIndexOf(",");
                lineBuilder.insert(where, ",ANYTIME");
            }
            lineBuilder.deleteCharAt(lineBuilder.length()-1); //remove trailing space
            line = lineBuilder.append(',').toString(); //add comma for future rating to be added
            if(line.contains(", ")){ //remove extra spaces
                line = line.replaceAll(", ", ",");
            }
            array.set(i, line); //write new string to the array
        }
    }
}
