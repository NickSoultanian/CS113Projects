package models;
/**
 *
 * ArrayListStack.java: This is a class that implements the StackInterface<E>. This class is meant to be run as a
 * single thread stack data structure. It stores data through java.util.ArrayList<E>. this class will throw an
 * emptystackexcetpion when a pop or peek is used on an empty stack. it is designed to follow the exact stack
 * data structure. I have added a getSize method to help with palindrome testing.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */

import java.util.ArrayList;
import java.util.EmptyStackException;

public class ArrayListStack<E>  implements StackInterface<E> {

    private ArrayList<E> theData;
    private int topOfStack = 0;
    /**
     * When the class is initialized, arraylist is initialized.
     *
     * @return nothing
     */
    public ArrayListStack(){
        theData = new ArrayList<E>();
    }
    /**
     * Gives the user the current size of the stack, the stack is empty at 0 and full if topOfStack > 0
     *
     * @return returns topOfStack
     */
    public int getSize(){
        return topOfStack;
    }
    @Override
    public boolean empty(){
        return theData.isEmpty();
    }
    @Override
    public E peek(){
        if (empty()){
            throw new EmptyStackException();
        }
        return theData.get(topOfStack - 1);

    }

    @Override
    public E pop(){
        if(empty()) {
            throw new EmptyStackException();
        }
        /*topOfStack --;
        E data = theData.get(topOfStack);
        return data;*/

        E toReturn = theData.get(topOfStack - 1);
        theData.remove(topOfStack - 1);
        topOfStack --;
        return toReturn;
    }

    @Override
    public E push(E obj){
        theData.add(topOfStack, obj);
        topOfStack++;
        return obj;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayListStack)) return false;
        return theData.equals(o);
    }

    @Override
    public String toString() {
        return "ArrayListStack{" +
                "theData=" + theData +
                ", topOfStack=" + topOfStack +
                '}';
    }
}

