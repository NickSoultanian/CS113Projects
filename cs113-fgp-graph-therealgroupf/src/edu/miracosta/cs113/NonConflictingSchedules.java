package edu.miracosta.cs113;
/**
 * NonConflictinSchedules.java sorts through a string that contains the desired classes and builds a graph that holds classes
 * not conducted at conflicting times. Also initiates the class that corrects professor ratings
 * @author Ian Ward
 * @version 1.0
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

public class NonConflictingSchedules {
    private ArrayList<String> individualClasses = new ArrayList<String>(200);
    public static ArrayList<OfferedClass> allClasses = new ArrayList<OfferedClass>(200);
    protected static ArrayList<LinkedList> testContainer = new ArrayList<LinkedList>(200);
    protected LinkedList<OfferedClass> testCombos = new LinkedList<OfferedClass>();
    private String  currentSubj = "";
    private String[] result;
    protected ListGraph graph;
    private double[] scores;
    private ArrayList<LinkedList> finalThree = new ArrayList<LinkedList>();
    /**
     * A nested class that represents a class offered at Miracosta
     */
    public class OfferedClass{
        private String className, section, classTime, instructor, rating = "3.9"; //3.9 is the avg rating at Mircosta
        /**
         * default constructor that sets all instance variables
         * @param classInfo String: contains all instance variables in order classname,section,classtime,instructor
         *                  variables must be seperated by commas
         */
        public OfferedClass(String classInfo){
            StringBuilder currentString = new StringBuilder(classInfo);
            className = currentString.substring(0, currentString.indexOf(","));
            currentString.replace(0, (currentString.indexOf(",") + 1), "");
            currentString.replace(0, (currentString.indexOf(",") + 1), "");
            section =  currentString.substring(0, currentString.indexOf(","));
            currentString.replace(0, (currentString.indexOf(",") + 1), "");
            classTime = currentString.substring(0, currentString.indexOf(","));
            currentString.replace(0, (currentString.indexOf(",") + 1), "");
            instructor = currentString.substring(0, currentString.indexOf(","));
        }
        /**
         * @return String: A getter for the classname ex (BIO 101)
         */
        public String getClassName() {
            return className;
        }
        /**
         * @param className String A setter for the classname ex (BIO 101)
         */
        public void setClassName(String className) {
            this.className = className;
        }
        /**
         * @return String: A getter for the class section ex(2525)
         */
        public String getSection() {
            return section;
        }
        /**
         * @param section String: A setter for the class section ex(2525)
         */
        public void setSection(String section) {
            this.section = section;
        }
        /**
         * @return  String: A getter for class schedule
         */
        public String getClassTime() {
            return classTime;
        }
        public void setClassTime(String classTime) {
            this.classTime = classTime;
        }
        /**
         * @return  String: A getter for the professors name
         */
        public String getInstructor() {
            return instructor;
        }
        /**
         * @param instructor String: A setter for the professors name
         */
        public void setInstructor(String instructor) {
            this.instructor = instructor;
        }
        /**
         * @return String: A getter for the professors rating
         */
        public String getRating() {
            return rating;
        }
        /**
         *@param rating  String: A setter for the professors rating
         */
        public void setRating(String rating) {
            this.rating = rating;
        }
        @Override
        public String toString() {
            return className + "," + section + "," + classTime + "," + instructor + "," + rating;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof OfferedClass)) return false;
            OfferedClass that = (OfferedClass) o;
            return Objects.equals(section, that.section);
        }
    }
    /**
     * Default constructor drives methods to remove conflicting schedules.
     * Also initializes GetProf scores to correct the scores for each class created. Prints
     * to console when finished
     */
    public NonConflictingSchedules(String bulkClasses){
        parseIndividualClasses(bulkClasses);
        getDesiredClasses();
        createCombos();
        createContainers();
        checkSchedules();
        new GetProfScore();
        buildGraph();
        System.out.println("Non-conflicting schedules all created");
    }
    /**
     * For each individual desired classes each offered section is found
     */
    private void getDesiredClasses(){
        for(int j = 0; j < individualClasses.size(); j++) {
            for (int i = 0; i < AvailableClasses.array.size(); i++) {
                String classListing = AvailableClasses.array.get(i);
                if (classListing.startsWith(individualClasses.get(j) + ",")){
                    allClasses.add(new OfferedClass(classListing)); //saves each individual section
                }
            }
        }
        while(allClasses.remove(null)); //remove null classes
    }
    /**
     * Parses a string containing all of the desired classes into individual classes
     * @param bulk String: contains all the desired class names seperated by commas
     */
    private void parseIndividualClasses(String bulk){
        do{
            if(bulk.contains(",")) {
                int index = bulk.indexOf(",");
                individualClasses.add(bulk.substring(0,index));
                bulk = bulk.substring(index + 1);
            }else{
                individualClasses.add(bulk);
                break;
            }
        }while(bulk.length() > 0);
    }
    /**
     * Creates all possible class combinations for classes that do not have the same classname.
     * Those that are have conflicting schedules are included
     */
    private void createCombos(){
        currentSubj = allClasses.get(0).className;
        ArrayList<ArrayList<String>> holder = new ArrayList<ArrayList<String>>();
        holder.add(new ArrayList<String>());

        for(int i = 0, j = 0; i < allClasses.size(); i++){ //go through all classes
            if(allClasses.get(i).getClassName().equals(currentSubj)){ //check for matching classnames
                holder.get(j).add(allClasses.get(i).getSection());
            }else {
                currentSubj = allClasses.get(i).getClassName();
                holder.add(new ArrayList<String>());
                j++;
                holder.get(j).add(allClasses.get(i).getSection());
            }
        }
        result = Arrays.stream(holder.get(0).toArray()).toArray(String[]::new);
        for(int i = 0; i < holder.size() - 1; i++){
            String[] array2 = Arrays.stream(holder.get(i + 1).toArray()).toArray(String[]::new);
            result = sumArrays(result, array2); //helper method creates combos
        }
    }
    /**
     * Creates linked lists of potential schedules
     */
    private void createContainers(){
        String startingString = Arrays.toString(result);
        startingString = startingString.substring(1, startingString.length()-1);
        startingString = startingString.replaceAll(", ",",");
        String currentString = startingString;
        while(currentString.length() > 2) { //current string contains all the class combos
            int index = 0;
            if(currentString.charAt(0) == ',') { //add class to linked list using comma delimiter
                testContainer.add(testCombos);
                testCombos = new LinkedList<>();
                currentString = currentString.substring(1);
            }
            for (int i = 0; i < allClasses.size(); i++) { //repeat for all classes
                if (allClasses.get(i).getSection().equals(currentString.substring(0, 4))) {
                    index = i;
                }
            }
            testCombos.add(allClasses.get(index));
            currentString = currentString.substring(4);
        }
    }
    private void checkSchedules(){
        for(int i = 0; i < testContainer.size(); i++){
            ArrayList<Time> schedules = new ArrayList<Time>(10);
            Iterator it = testContainer.get(i).iterator();
            boolean conflictFlag = false;
            while(it.hasNext()){
                String currentTime = ((OfferedClass) it.next()).getClassTime();
                if(currentTime.contains("&")) {
                    int indexOfAnd = currentTime.indexOf("&") + 2;
                    String secondTime = currentTime.substring(indexOfAnd);
                    currentTime = currentTime.substring(0, indexOfAnd);
                    schedules.add(new Time(secondTime));
                    secondTime = null;
                }
                schedules.add(new Time(currentTime));
            }
            for (int k = 0; k < schedules.size() - 1; k++) {
                for (int j = k + 1; j < schedules.size(); j++) {
                    if (schedules.get(k).compare(schedules.get(j))) {
                        conflictFlag = true;
                    }
                }
            }
            if(conflictFlag){
                testContainer.set(i, null);
            }
        }
        testContainer.removeIf(Objects::isNull);
    }
    /**
     * Creates the graph and sets the 3 highest ratings
     */
    public void buildGraph(){

        graph = new ListGraph(allClasses.size(), true); //needs to be directed
        for(int g = 0; g < testContainer.size(); g++){ //add all the applicable class sections
            for (int k = 0; k < testContainer.get(g).size() - 1; k++) {
                for (int j = k + 1; j < testContainer.get(g).size(); j++) {
                    NonConflictingSchedules.OfferedClass currentClass = (NonConflictingSchedules.OfferedClass) testContainer.get(g).get(k);
                    double firstRating = Double.valueOf(currentClass.getRating());
                    NonConflictingSchedules.OfferedClass secondClass = (NonConflictingSchedules.OfferedClass) testContainer.get(g).get(j);
                    double secondRating = Double.valueOf(secondClass.getRating());
                    //create edges between each applicable edge
                    graph.insert(new Edge((NonConflictingSchedules.OfferedClass)testContainer.get(g).get(k), (NonConflictingSchedules.OfferedClass)testContainer.get(g).get(j),firstRating + secondRating));
                }
            }
        }
/*
        BreadthFirstSearch bfs = new BreadthFirstSearch();
        bfs.BFS(graph.edges,graph.map);
        */
        //get all the edge values
        scores = new double[testContainer.size()];
        for(int i = 0; i < NonConflictingSchedules.testContainer.size();i++){
            for(int j = 0; j < NonConflictingSchedules.testContainer.get(i).size();j++){
                NonConflictingSchedules.OfferedClass temp = (NonConflictingSchedules.OfferedClass)NonConflictingSchedules.testContainer.get(i).get(j);
                scores[i] = scores[i] + Double.valueOf(temp.getRating());
            }
        }
        double[] placeHolder = scores;
        double topScore = 0, secondScore = 0, thirdScore = 0;
        int indexOfTop = 0, indexOf2 = 0, indexOf3 = 0;
        Arrays.sort(placeHolder);
        topScore = placeHolder[placeHolder.length-1];
        secondScore = placeHolder[placeHolder.length-2];
        thirdScore = placeHolder[placeHolder.length-3];
        for(int i = 0; i < scores.length; i++){
            if(scores[i] == topScore){
                indexOfTop = i;
            }
            else if(scores[i] == secondScore){
                indexOf2 = i;
            }else if(scores[i] == thirdScore){
                indexOf3 = i;
            }
        }
        finalThree.add(0, testContainer.get(indexOfTop));
        finalThree.add(1, testContainer.get(indexOf2));
        finalThree.add(2, testContainer.get(indexOf3));
    }

    public ArrayList<LinkedList> getFinalThree() {
        return finalThree;
    }

    /**
     * Cross multiplies two arrays
     * @param array1 String[]
     * @param array2 String[]
     * @return String[] the product of array1*array2
     */
    private String[] sumArrays(String[] array1, String[] array2){
        result = Arrays.stream(array1).flatMap(s1 -> Arrays.stream(array2).map(s2 -> s1 + s2)).toArray(String[]::new);//multiplies
        return result;
    }
    /**
     * Gets the highest rated schedules for the GUI
     * @return double[] : contains the three highest ratings
     */
    public double[] getScores() {
        return scores;
    }

    public ListGraph getGraph() {
        return graph;
    }

    /**
     * Gets a list of all the classes (used for autocomplete)
     * @return ArrayList<OfferedClass> :
     */

    public ArrayList<OfferedClass> getAllClasses() {
        return allClasses;
    }
}
