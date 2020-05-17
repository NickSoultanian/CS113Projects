package edu.miracosta.cs113; /**
 * ParsePDFOutput.java takes in a .txt file of unfiltered class listings then parses through the file, creating a refined
 * txt file that has a consistent and usable format for class listings
 * @author Ian Ward
 * @version 1.0
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ParsePDFOutput {
    private Scanner inputFile, multiCampus, elijoScanner, elijoScanner2;
    private String rawLine = "";
    private PrintWriter printWriter = null, elijoWriter, finalWriter; //keep compiler happy;
    /**
     * Default constructor creates a scanner for a valid .txt file. Also initiates
     * the helper methods that will parse/refine the file. Prints to the console when complete.
     * @param inFileName String: the txt file to be parsed
     */
    public ParsePDFOutput(String inFileName) {
        try {
            inputFile = new Scanner(new File(inFileName));
            printWriter = new PrintWriter("allClasses.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        filterClasses();
        inputFile.close();
        printWriter.close();
        removeDuplicateLocations();
        refineForElijo();
        removeElijoTitles();
        System.out.println("Sorted out the PDF");
    }
    /**
     * Advances a scanner if there is a next line or sets the nextline to a default value
     */
    public void advance() {
        if (hasAnotherLine()) {
            rawLine = inputFile.nextLine();
        }else{
            rawLine = "NODATA";
        }
    }
    /**
     * Lets the user know  if the scanner has a next line
     * @return boolean: true if there is a next line
     */
    private boolean hasAnotherLine(){
        if(inputFile.hasNextLine()){
            return true;
        }
        return false;
    }
    /**
     * Goes though txt input and identifies lines that hold information on classes
     */
    public void filterClasses() {
        advance();
        if(rawLine != "NODATA") {
            if (isClassName(rawLine, true)) { //check if the line indicates the start of a class listing and write to file
                filterClasses();
            }
            if (location(rawLine) != -1) { //check to see if the line is an offered class section or just random text
                do {
                    advance();
                    isClassData(rawLine);
                }
                while (((location(rawLine) != 1 | location(rawLine) != 0 | !isClassName(rawLine, true))) && rawLine != "NODATA");
            }
            filterClasses();
        }
    }
    /**
     * Parses a line to see if the line indicates the start of a new class listing
     * @param line String: the line to be parsed
     * @return boolean: if true the line is indicative of a new listing
     */
    private boolean isClassName(String line, boolean write){
        String trimmed = line.trim();
        if(trimmed.length() < 6){ // all class listings are at least 6 chars in length
            return false;
        }
        boolean firstIsLetter = Character.isLetter(trimmed.charAt(0));
        boolean secondIsLetter = Character.isLetter(trimmed.charAt(1));
        boolean thirdIsLetter = Character.isLetter(trimmed.charAt(2));
        boolean forthIsLetter = Character.isLetter(trimmed.charAt(3));
        boolean sixthIsDigit = Character.isDigit(trimmed.charAt(5));
        boolean fifthIsDigit = Character.isDigit(trimmed.charAt(4));
        boolean forthIsDigit = Character.isDigit(trimmed.charAt(3));
        boolean fifthIsSpace = false, fourthIsSpace = false, thirdIsSpace = false;
        //remove false positive classes
        if(trimmed.charAt(trimmed.length()-1) == '.' | trimmed.contains(" See ")){
            trimmed = "NOTACLASS";
        }
        if(trimmed.charAt(4) == ' '){
            fifthIsSpace = true;
        }
        if(trimmed.charAt(3) == ' '){
            fourthIsSpace = true;
        }
        if(trimmed.charAt(2) == ' '){
            thirdIsSpace = true;
        }
        //account for four letter subjects
        if(firstIsLetter & secondIsLetter & thirdIsLetter & forthIsLetter & fifthIsSpace & sixthIsDigit){
            if(write) {
                printWriter.write("*" + trimmed + "\n");
            }
            return true;
        }
        //account for 3 letter subjects
        if(firstIsLetter & secondIsLetter & thirdIsLetter & fourthIsSpace & fifthIsDigit){
            if(write) {
                printWriter.write("*" + trimmed + "\n");
            }
            return true;
        }
        //cs is too cool for 3 letters
        if(firstIsLetter & secondIsLetter & thirdIsSpace & forthIsDigit){
            if(write) {
                printWriter.write("*" + trimmed + "\n");
            }
            return true;
        }
        return false;
    }
    /**
     * Checks a line to see of the class is offered online or at oceanside
     * @param line String: the line thought to contain a class location
     * @return int: 1 means the class is online or in oceanside, 0 means it is in san elijo, -1 means the line is not a class listing
     */
    private int location(String line){
        line = line.trim();
        if((line.contains("ONLINE") && line.length() == 6) | line.contains("OCEANSIDE")){
            printWriter.write("-" + line + "\n");
            return 1;
        }
        if(line.contains("SAN ELIJO")){
            printWriter.write("-" + line + "\n");
            return 0;
        }
        return -1;
    }
    /**
     * Determines if a string contains class data: ex (class time, professor, location...etc)
     * @param line String: the line thought to contain class data
     * @return boolean: true indicates the line contained class data
     */
    private boolean isClassData(String line){
        String trimmed = line.trim();
        if(trimmed.length() < 20){
            return false;
        }
        boolean firstIsDigit = Character.isDigit(trimmed.charAt(0));
        boolean secondIsDigit = Character.isDigit(trimmed.charAt(1));
        boolean thirdIsDigit = Character.isDigit(trimmed.charAt(2));
        boolean forthIsDigit = Character.isDigit(trimmed.charAt(3));
        if(firstIsDigit & secondIsDigit & thirdIsDigit & forthIsDigit){ //all lines containing class data start it 4 digits
            printWriter.write(trimmed + "\n");
            return true;
        }
        if(trimmed.charAt(0) == '&'){
            printWriter.write(trimmed + "\n");
            return true;
        }
        return false;
    }
    /**
     * reads in a partially refined txt file of class listings and removes all classes that have multiple locations
     * as they are offered partially online and partially at o-side. writes out to a new file
     */
    private void removeDuplicateLocations(){
        String currentLine;
        Scanner scanner = null;
        PrintWriter writer = null;
        boolean firstTime = false;
        try {
            scanner = new Scanner(new File("allClasses.txt"));
            writer = new PrintWriter("noDups.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            if(currentLine.charAt(0) == '-'){ //ignores the first location instance
                firstTime = !firstTime;
            }
            if(firstTime | currentLine.charAt(0) != '-') { //only write out one location to new file
                writer.write(currentLine + "\n");
            }
        }
        writer.close();
        scanner.close();
    }
    /**
     * Due to the original pdf formatting a few san elijo classes made it through to the refined .txt file
     * this second pass removes the remaining ones that slipped through the cracks
     */
    private void refineForElijo(){
        try {
            multiCampus = new Scanner(new File("noDups.txt"));
            elijoWriter = new PrintWriter("noElijo.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        while (multiCampus.hasNext()){
            String currentLine = multiCampus.nextLine();
            if(!currentLine.contains("-SAN ELIJO")){
                elijoWriter.write(currentLine + "\n");
            }
            if(currentLine.contains("-SAN ELIJO")){ //don't write any classes that contain san elijo to new file
                do{
                    currentLine = multiCampus.nextLine();
                }while(isClassName(currentLine, false));
            }
        }
        elijoWriter.close();
        multiCampus.close();
    }
    /**
     * This is the first pass through the unrefined .txt that removes the bulk of the classes held at San Elijo
     */
    private void removeElijoTitles(){
        try {
            elijoScanner = new Scanner(new File("noElijo.txt"));
            elijoScanner2 = new Scanner(new File("noElijo.txt"));
            finalWriter = new PrintWriter("final.txt");
        } catch (FileNotFoundException ex) {
            System.out.println("Sorry the input file was not found");
            System.err.println("Run program again, make sure you enter the right file name");
            System.exit(0);
        }
        elijoScanner2.nextLine();
        while(elijoScanner2.hasNextLine()){
            String firstLine = elijoScanner.nextLine();
            String secondLine = elijoScanner2.nextLine();
            if(firstLine.startsWith("*") && secondLine.startsWith("*")){ //skips writing over those identified as elijo
                continue;
            }else{
                finalWriter.write(firstLine + "\n"); //writes out the rest
            }
        }
        elijoScanner.close();
        elijoScanner2.close();
        finalWriter.close();
    }
}