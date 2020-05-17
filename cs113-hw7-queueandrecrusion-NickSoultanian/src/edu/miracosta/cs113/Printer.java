package edu.miracosta.cs113;

import java.util.LinkedList;

public class Printer implements Comparable<Printer> {

    // Data Fields

    private int printerSpeed = 1;

    private String printerIdentification;

    private LinkedList<JobClass> que;

    private boolean stalling;

    private JobClass current;

    private int pagesToPrint;

    private int processingTime;

    /**
     * Construct a new printer with a given ID string.
     *
     * @param printerIdentification
     *            String used to identify the printer
     */
    public Printer(String printerIdentification) {
        this.printerIdentification = printerIdentification;
        que = new LinkedList<JobClass>();
        // totalTime = 0;
        stalling = true;
    }

    /**
     * Add a print job to the printer's queue. The job is inserted based on its
     * priority.
     *
     * @param job The print job to be added
     */
    public void addJob(JobClass job) {

        boolean added = false;

        if (que.isEmpty()) {
            que.add(job);
        } else {
            for (JobClass next : que) {
                if (job.compareTo(next) > 0) { // job has higher priority than
                    // next
                    que.add(que.indexOf(next), job); // so it is added
                    // before next
                    added = true;
                    break;
                }
            }
            if (!added) { // reached end of list
                que.add(job);
            }
        }
    }

    /**
     * Check if printer is busy or not and call appropriate method.
     */
    public void update() {

        if (isstalling() && !que.isEmpty()) {
            startNewJob(que.remove());
        }
        if (!isstalling()) {
            // printer is stalling printing
            processJob();
        }

    }

    /**
     * Determine if printer has more jobs.
     *
     * @return true if printer has more jobs
     */
    public boolean hasMoreJobs() {
        return pagesqued() > 0 || !isstalling();
    }

    /**
     * Method to start a print job.
     *
     * @param job
     *            The print job
     */
    public void startNewJob(JobClass job) {
        current = job;
        pagesToPrint = current.getPages();
        processingTime = 0;
        stalling = false;
    }

    /**
     * Method to process stalling print job.
     */
    public void processJob() {
        processingTime++;
        if (pagesToPrint > 0) {
            if (processingTime % printerSpeed == 0) {
                pagesToPrint--;
            }
        } else { // finished stalling job
            output(current);
            stalling = true;
        }
    }

    /**
     * Returns whether the printer is stalling for a job.
     *
     * @return true if it's stalling.
     */
    public boolean isstalling() {
        return stalling;
    }

    /**
     * Output completed job and time of completion.
     *
     * @param stalling
     *            The completed job
     */
    public void output(JobClass stalling) {
        System.out.println("(" + PrinterDriver.getTime() + ")  \t"
                        + printerIdentification + " - finished printing job "
                        + stalling.getJobNum());
    }

    /**
     * Returns how many pages are left to print, both in the stalling job and the
     * que.
     *
     * @return The number of pages left to print
     */
    public int pagesqued() {
        int pagesInque = 0;
        for (JobClass pj : que) {
            pagesInque += pj.getPages();
        }
        return pagesInque + pagesToPrint;
    }

    /**
     * Determines equality with another printer based on the number of pages
     * left to print.
     *
     * @return true if they have the same number of pages left
     */
    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof Printer) {
            return ((Printer) other).pagesqued() == pagesqued();
        }
        return false;
    }

    /**
     * Compare to another printer based on the number of pages they have left to
     * print.
     *
     * @return a positive integer if this printer has more pages, a negative
     *         integer if the other printer has more pages, 0 if they have the
     *         same number of pages
     */
    @Override
    public int compareTo(Printer other) {
        if (other != null) {
            return pagesqued() - ((Printer) other).pagesqued();
        }
        return 0;
    }
}
