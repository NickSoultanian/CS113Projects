# CS113-HW06-StackPalindrome
## HW #6 for CS113 - Palindrome using Stacks

> **"I will, in fact, claim that the difference between a bad programmer and a good one is whether [they] consider [their] code or [their] data structures more important. Bad programmers worry about the code. Good programmers worry about data structures and their relationships."**
> — Linus Torvalds

> **"Smart data structures and dumb code works a lot better than the other way around."**
> — Eric S. Raymond

**A palindrome is a word, phrase, number, or other sequence of characters which reads the same backward as forward, such as madam or racecar.** One way to programmatically detect palindromes is through iteration (loops), where you check that each character on both ends matches and repeat until the entire string is processed.  A similar approach can be done recursively.

Another approach to checking for palindromes would be to store the characters of the string being checked in a stack and then remove half of the characters, pushing them onto a second stack. When you are finished, if the two stacks are equal, then the string is a palindrome. This works fine if the string has an even number of characters. If the string has an odd number of characters, an additional character should be removed from the original stack before the two stacks are compared. It doesn’t matter what this character is because it doesn’t have to be matched. **Design, code, and test a program that implements this approach.**

You are provided with a `StackInterface` to **implement your stack, make sure to implement the data structure using `java.util.ArrayList`** and naming your implementation `ArrayListStack` (make sure to use generics like you have been for the data structures so far).  The provided `StackTester` assumes you name your implementation that way.  Note that you have also been provided a `PalindromeTester` with the specific `isPalindrome()` method you are required to implement, following the approach above.  The method should take whitespace, case-sensitivity, digits, and symbols into account (see documentation above method).

Make sure to provide a sequence diagram for `isPalindrome()`, it should include more details than just the steps outlined above.  Add the image (PNG, JPEG, etc.) in markdown in the space below:

![Palindrome image](palindromeSequence.png)

## Extra Credit (10pts.)
Create the circular array implementation of the `java.util.Queue` [interface](https://docs.oracle.com/javase/7/docs/api/java/util/Queue.html), and provide a separate JUnit tester file to test the 6 methods thoroughly (see `StackTester` and use it as a guide for the level of detail to test).

----------

### Make sure to commit + push *before* the deadline to have your code be considered for grading.
>Pro-Tips:
>- Read the tests thoroughly, note the situations they are testing, and devise a strategy on how to solve the palindrome checker.  Then create the sequence diagram, ensuring your logic is sound.  Finally, implement in code.
>- Attempting the Extra Credit? Try building the JUnit tests first! Use a `Queue` variable and instantiate it with a `java.util.LinkedList` object to make sure your tests are working/pass. You can follow the `StackTester` as a model (creating the `setup()` method, etc.), that way you focus on how the `Queue` data structure works.  Once you have your JUnit tester fully built, you can focus on your implementation of the data structure and swap the instantiation for your implementations constructor!
