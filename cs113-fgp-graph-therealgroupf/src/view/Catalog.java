package view;

/**
 * Catalog.java :
 *
 * @author Alex Phan
 * @version 1.0
 */

public class Catalog {
    /* CONSTANTS/STATIC VARIABLE SECTION */

    public static final String DEFAULT_COURSE = "UBW 420";
    public static final String DEFAULT_NUMBER = "1337";
    public static final String DEFAULT_TIME = "TuTh 2:40 - 4:20am";
    public static final String DEFAULT_INSTRUCTOR = "Trump D";
    public static final String DEFAULT_RATING = "1.0";

    /* INSTANCE VARIABLES SECTION */

    private String course;
    private String classNumber;
    private String time;
    private String instructor;
    private String rating;

    /* CONSTRUCTORS SECTION */

    public Catalog() {
        this.course = DEFAULT_COURSE;
        this.classNumber = DEFAULT_NUMBER;
        this.time = DEFAULT_TIME;
        this.instructor = DEFAULT_INSTRUCTOR;
        this.rating = DEFAULT_RATING;
    }

    public Catalog(String course, String classNumber,
                   String time, String instructor, String rating) {
        this.course = course;
        this.classNumber = classNumber;
        this.time = time;
        this.instructor = instructor;
        this.rating = rating;
    }

    /* MUTATOR/SETTER METHODS SECTION*/

    public void setCourse(String course) {
        this.course = course;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    /* ACCESSOR/GETTER METHODS SECTION */

    public String getCourse() {
        return course;
    }

    public String getClassNumber() {
        return this.classNumber;
    }

    public String getTime() {
        return this.time;
    }

    public String getInstructor() {
        return this.instructor;
    }

    public String getRating() {
        return this.rating;
    }
}
