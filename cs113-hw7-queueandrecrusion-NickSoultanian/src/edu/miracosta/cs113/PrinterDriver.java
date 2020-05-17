package edu.miracosta.cs113;
/**
 * PrinterDriver.java: This is a class that uses the JobClass and Printer classes. This class is meant to be run as a
 * main to run all three different types of printer rooms.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
public class PrinterDriver {

    private static int numberOfJobs = 100;

    private static JobClass[] lineOfJobs;

    private static int time;

    public static void main(String[] args) {

        PrinterDriver driver = new PrinterDriver(numberOfJobs);

        // One printer simulation
        System.out.println("\nDriver using One System Printer:\n");
        driver.OnePrinter();

        // Two printer simulation
        System.out.println("\nDriver using Two System Printers (1 , 2):\n");
        driver.TwoPrinters();

        // Three printer simulation
        System.out.println("\nDriver using Three System Printers (1 , 2 , 3):\n");
        driver.ThreePrinters();

    }
    /**
     * Construct a new driver with a given number of jobs. Initialize jobs
     * and add them to the array.
     *
     * @param numberOfJobs Number of jobs in the driver
     */
    public PrinterDriver(int numberOfJobs) {
        lineOfJobs = new JobClass[numberOfJobs];
        for (int i = 0; i < numberOfJobs; i++) {
            lineOfJobs[i] = new JobClass(i + 1, (int) (Math.random() * 50 + 1));
        }
    }

    /**
     * Run driver with one printer.
     */
    public void OnePrinter() {

        Printer printer1 = new Printer("Printer 1");

        time = 0;
        int nextJob = 0;

        while (nextJob < numberOfJobs) {
            if (time % 60 == 0) {
                printer1.addJob(lineOfJobs[nextJob]);
                System.out.println("(" + getTime() + ")  \t"
                        + lineOfJobs[nextJob] + " sent to Printer.");
                nextJob++;
            }
            time++;
            printer1.update();
        }
        while (printer1.hasMoreJobs()) {
            time++;
            printer1.update();

        }
        System.out.println("\nTotal Driver Time (One Printer): "
                + getTime() + "\n");
    }

    /**
     * Run driver with two printers.
     */
    public void TwoPrinters() {

        Printer printer1 = new Printer("Printer 1");
        Printer printer2 = new Printer("Printer 2");

        time = 0;
        int nextJob = 0;

        while (nextJob < numberOfJobs) {
            if (time % 60 == 0) {
                if (printer1.compareTo(printer2) <= 0) {
                    printer1.addJob(lineOfJobs[nextJob]);
                    System.out.println("(" + getTime() + ")  \t"
                            + lineOfJobs[nextJob] + " sent to Printer 1.");
                } else {
                    printer2.addJob(lineOfJobs[nextJob]);
                    System.out.println("(" + getTime() + ")  \t"
                            + lineOfJobs[nextJob] + " sent to Printer 2.");
                }
                nextJob++;
            }
            time++;
            printer1.update();
            printer2.update();
        }
        while (printer1.hasMoreJobs() || printer2.hasMoreJobs()) {
            time++;
            printer1.update();
            printer2.update();
        }

        System.out.println("\nTotal Driver Time (Two Printers): "
                + getTime() + "\n");
    }

    /**
     * Run driver with three printers.
     */
    public void ThreePrinters() {

        Printer printer1 = new Printer("Printer 1");
        Printer printer2 = new Printer("Printer 2");
        Printer printer3 = new Printer("Printer 3");

        time = 0;
        int nextJob = 0;

        while (nextJob < numberOfJobs) {
            if (time % 60 == 0) {
                if (printer1.compareTo(printer2) <= 0
                        && printer1.compareTo(printer3) <= 0) {
                    printer1.addJob(lineOfJobs[nextJob]);
                    System.out.println("(" + getTime() + ")  \t"
                            + lineOfJobs[nextJob] + " sent to Printer 1.");
                } else if (printer2.compareTo(printer3) <= 0
                        && printer2.compareTo(printer1) <= 0) {
                    printer2.addJob(lineOfJobs[nextJob]);
                    System.out.println("(" + getTime() + ")  \t"
                            + lineOfJobs[nextJob] + " sent to Printer 2.");
                } else {
                    printer3.addJob(lineOfJobs[nextJob]);
                    System.out.println("(" + getTime() + ")  \t"
                            + lineOfJobs[nextJob] + " sent to Printer 3.");
                }
                nextJob++;
            }
            time++;
            printer1.update();
            printer2.update();
            printer3.update();
        }
        while (printer1.hasMoreJobs() || printer2.hasMoreJobs()
                || printer3.hasMoreJobs()) {
            time++;
            printer1.update();
            printer2.update();
            printer3.update();
        }

        System.out.println("\nTotal Driver Time (Three Printers): "
                + getTime() + "\n");
    }

    /**
     * Method to format seconds into a string (hh:mm:ss)
     *
     * @param minutes
     *            The time to format
     * @return the formatted time
     */
    public static String formatTime(int minutes) {
        int h = minutes / 3600; // hours
        minutes %= 3600;
        int m = minutes / 60; // minutes
        int s = minutes % 60; // seconds

       if (h > 0) {
            return String.format("%d:%02d:00", h , m); // time as hh:mm:ss
        }else {
            return String.format("%d:00", m ); // time as mm:ss
       }
    }

    /**
     * Returns the formatted time of the stalling simulation.
     *
     * @return the time in hh:mm:ss format
     */
    public static String getTime() {
        return formatTime(time);
    }

}