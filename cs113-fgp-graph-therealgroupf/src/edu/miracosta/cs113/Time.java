package edu.miracosta.cs113;
/**
 * Time.java compares strings that contain class listing times. It will decide if the classes can be added to a schedule
 * together based on whether or not the class times overlap
 * @author Ian Ward
 * @version 1.0
 */
public class Time {
    int dayOfWeek = -2;
    double startTime, endTime;
    String abbrvDayOfWeek = null, currentString;
    /**
     * Constructor initializes the parsing of the string that contains the class time
     * @param initial String: contains a single class date and time ex(" TTh       9 am–10:50 am ")
     */
    public Time(String initial){
        parseDays(initial);
        if(dayOfWeek != 0) {
            formatTimes();
        }else{
            startTime = 0;
            endTime = 0;
        }
    }
    /**
     * Parses the day(s) of the week a class is offered on and converts it to a digit
     * @param fullString String: contains a day of the week ex the TTH in " TTh       9 am–10:50 am "
     */
    private void parseDays(String fullString){
        int indexOfSpace = fullString.indexOf(" ");//locates the day of week value in the string
        if(indexOfSpace == -1){
            dayOfWeek = 0;
        }else {
            abbrvDayOfWeek = fullString.substring(0, indexOfSpace);
            currentString = fullString.substring((indexOfSpace + 1));
        }
        if(dayOfWeek != 0) {
            setDayOfWeek();
        }
    }
    //converts day of week to string that contains integer value
    private void setDayOfWeek() { //1 = mon 2= tues, 3=wed so on and so forth 12 = monday and tuesday
        switch(abbrvDayOfWeek){
            case "M": dayOfWeek = 1;
                break;
            case "MW": dayOfWeek = 13;
                break;
            case "TTh": dayOfWeek = 24;
                break;
            case "F": dayOfWeek = 5;
                break;
            case "W": dayOfWeek = 3;
                break;
            case "T": dayOfWeek = 2;
                break;
            case "Th": dayOfWeek = 4;
                break;
            case "S": dayOfWeek = 6;
                break;
            case "Daily": dayOfWeek = 12345;
                break;
            case "MT": dayOfWeek = 12;
                break;
            case "MTWTh": dayOfWeek = 1234;
                break;
            case "MTh": dayOfWeek = 14;
                break;
            case "MWF": dayOfWeek = 135;
                break;
            case "FS": dayOfWeek = 56;
                break;
            case "ARRANGED": dayOfWeek = 0;
                break;
            case "ANYTIME": dayOfWeek = 0;
                break;
            default: dayOfWeek = 0;
                break;
        }
    }
    /**
     * Splits the class times into start and end times and then calls a helper method to convert those times to military time
     * so they can be evaluated using simple math
     */
    private void formatTimes(){
        currentString = currentString.trim();
        if(currentString.contains("&")){    //remove & sticking around from classes with two times
            currentString = currentString.substring(0, currentString.length()-2);
        }
        int divider = currentString.indexOf("–");
        StringBuilder unfilteredStart = new StringBuilder(currentString.substring(0, divider));//split start and end times
        StringBuilder unfilteredEnd = new StringBuilder(currentString.substring(divider + 1));
        parseTime(unfilteredStart, true); //helper method to convert to military time
        parseTime(unfilteredEnd, false);
    }
    /**
     * Converts a time to military time and then sets the class variables startTime and endTime
     * @param unfilteredStart StringBuilder: contains only the start or end time in its original form
     * @param startTime boolean: true indicates that the stringbuilder contains a start time not an endtime
     */
    private void parseTime(StringBuilder unfilteredStart, boolean startTime){
        if(unfilteredStart.indexOf(":") == -1){
            unfilteredStart.insert(unfilteredStart.indexOf(" "), ":00");//pad for :00 that werent added
        }

        if(unfilteredStart.substring(unfilteredStart.indexOf(" ") + 1).equals("pm")){ //convert pms to military time
            int colon = unfilteredStart.indexOf(":");
            if(!unfilteredStart.substring(0,colon).equals("12")){
                int militaryTime = Integer.valueOf(unfilteredStart.substring(0,colon));
                String militaryString = (militaryTime + 12) + "";
                unfilteredStart.replace(0, colon, militaryString);
            }
            String fraction = unfilteredStart.substring(colon + 2);
        }
        if(unfilteredStart.indexOf(":") == 1){ //append 0 at start for classes that start/end in a single digit value
            unfilteredStart.insert(0,"0");
        }
        unfilteredStart = new StringBuilder(unfilteredStart.substring(0, unfilteredStart.indexOf(" ")));
        unfilteredStart.replace(unfilteredStart.indexOf(":"),unfilteredStart.indexOf(":") + 1, ".");
        if(startTime){
            this.startTime = Double.valueOf(unfilteredStart.toString()); //sets the instance variables
        }else{
            this.endTime = Double.valueOf(unfilteredStart.toString());
        }
    }
    @Override
    public String toString() {
        return "{" + dayOfWeek + ": " + startTime + "-" + endTime + "}";
    }
    /**
     *  Compares two Time objects to see if they overlap
     *  @param other Time: the class this will be compared to
     *  @return boolean: true means the Times overlap
    */
    public boolean compare(Time other){
        double a,b,c,d;
        String thisString = (this.getDayOfWeek() + ""); //converts day of week integer value to a string
        String otherString = (other.getDayOfWeek() + "");
        a = this.getStartTime();
        b = this.getEndTime();
        c = other.getStartTime();
        d = other.getEndTime();
        for (int i = 0; i < thisString.length(); i++){
            if(otherString.contains((thisString.charAt(i)) + "")){
                if((a <= d)&&(b >= c)){ //overlap
                    return true;
                }else{
                    return false; //no overlap
                }
            }
        }
        return false;
    }
    /**
     * Getter returns the integer value of the day(s) of the week the class is held
     * @return int
     */
    public int getDayOfWeek() {
        return dayOfWeek;
    }
    /**
     * Getter returns value of the classes start time in decimal form
     * @return double
     */
    public double getStartTime() {
        return startTime;
    }
    /**
     * Getter returns value of the classes end time in decimal form
     * @return double
     */
    public double getEndTime() {
        return endTime;
    }
}
