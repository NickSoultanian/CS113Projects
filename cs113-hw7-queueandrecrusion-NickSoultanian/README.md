# CS113-HW07-QueueAndRecursion
## HW #7 for CS113 - PrinterQueue and Recursion problems

The homework consists of two projects.  Make sure to make a package for each, `edu.miracosta.cs113.printerQueue` and `edu.miracosta.cs113.change`, respectively.


## edu.miracosta.cs113.Printer Queues (Programming Projects 5, pg. 240):
An operating system assigns jobs to print queues based on the number of pages to be printed (less than 10 pages, less than 20 pages, or more than 20 pages).  You may assume that the system printers are able to print 10 pages per minute.  Smaller print jobs are printed before larger print jobs, and print jobs of the same priority are queued up in the order in which they are received.  The system administrator would like to compare the time required to process a set of print jobs using one, two, or three system printers.

Write a program to simulate processing 100 print jobs of varying lengths using one, two, or three printers.  Assume that a print request is made every minute and that the number of pages to print varies from 1 to 50 pages.

The output from your program should indicate the order in which the jobs were received, the order in which they were printed, and the time required to process the set of print jobs.  If more than one printer is being used, indicate which printer each job was printed on.

**_NOTE:_** _You may use any Queue you’ve implemented, or the built in Queue interface with a LinkedList instantiation to accomplish the above project. Make sure to provide JUnit tests for all classes (except your driver)_

## [Recursive] Change Calculator (Programming Project 7, pg. 291):

Write a recursive method that will dispense change for a given amount of money.  The method will **display** the total number of combinations of quarters, dimes, nickels, and pennies that equal the desired amount and all of the combinations as well.  Avoid duplication, if you choose to use a data structure it must be one that we've covered and you must thoroughly justify why it was the best choice: **[here]**

**_NOTE:_** _Your program should dispense the highest coin first (quarters, then dimes, then nickels, then pennies).  Provide tests for 5 cent increments between 5 cents and 30 cents, and a larger test for 75 cents (hint: 75 cents has 121 unique number of combinations)_
