package edu.miracosta.cs113;
/**
 *
 * DoubleLinkedList.java: This is a class that implements the List<E> interface. there are two private classes.
 * Node<E> class which is static and holds nodes for linkedlists nodes. the second is a DoubleListIterator which implements
 * ListIterator<E>. inside the doublelistiterator is methods head taken from ListIterator<E>. then for the main class is
 * method heads overridden from list.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Collection;
import java.util.NoSuchElementException;

public class DoubleLinkedList<E> implements List<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size = 0;

    private static class Node<E>{
            private E data;
            private Node<E> next;
            private Node<E> prev;

            private Node(E data){
                this.data = data;
                this.next = null;
                this.prev = null;
            }

            private Node(E data, Node<E> nodeRefNext, Node<E> nodeRefPrev){
                this.data = data;
                this.next = nodeRefNext;
                this.prev = nodeRefPrev;
            }
        }
    private class DoubleListIterator implements ListIterator<E>{
        private Node<E> nextItem;
        private Node<E> lastItemReturned;
        private int index = 0;


        public DoubleListIterator(int i) {
            if (i < 0 || i > size) {
                throw new IndexOutOfBoundsException("Invalid index " + i);
            }
            lastItemReturned = null;
            if (i == size) {
                index = size;
                nextItem = null;
            } else {
                nextItem = head;
                for (index = 0; index < i; index++) {
                    nextItem = nextItem.next;
                }
                lastItemReturned = nextItem;
            }
        }

        @Override
        public boolean hasNext() {
            return nextItem != null;
        }

        @Override
        public E next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            lastItemReturned = nextItem;
            nextItem = nextItem.next;
            index++;
            return lastItemReturned.data;
        }

        @Override
        public boolean hasPrevious() {
            return (nextItem == null && size != 0)
                    || nextItem.prev != null;
        }
        @Override
        public E previous() {
            if(!hasPrevious()){
                throw new NoSuchElementException();
            }
            if(nextItem == null){
                nextItem = tail;
            }
            else{
                nextItem = nextItem.prev;
            }
            lastItemReturned = nextItem;
            index--;
            return lastItemReturned.data;
        }
        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() throws IllegalStateException {
            if (lastItemReturned != null) {
                if (lastItemReturned.next != null) {
                    lastItemReturned.next.prev = lastItemReturned.prev;
                } else {
                    tail = lastItemReturned.prev;
                    if (tail == null) {
                        head = null;
                    } else {
                        tail.next = null;
                    }
                }
                if (lastItemReturned.prev != null) {
                    lastItemReturned.prev.next = lastItemReturned.next;
                } else {
                    head = lastItemReturned.next;
                    if (head == null) {
                        tail = null;
                    } else {
                        head.prev = null;
                    }
                }
                lastItemReturned = null;
                size--;
                index--;
            } else {
                throw new IllegalStateException();
            }
        }


        @Override
        public void set(E obj) throws IllegalStateException {
            if (lastItemReturned != null) {
                lastItemReturned.data = obj;
            } else {
                throw new IllegalStateException();
            }
        }
        @Override
        public void add(E obj) {
            if (head == null) {
                head = new Node<E>(obj);
                tail = head;
            }
            else if (nextItem == head) {
                Node<E> temp = new Node<E>(obj);
                temp.next = nextItem;
                nextItem.prev = temp;
                head = temp;
            }
            else if (nextItem == null) {
                Node<E> temp = new Node<E>(obj);
                tail.next = temp;
                temp.prev = tail;
                tail = temp;
            } else {
                Node<E> temp = new Node<E>(obj);
                temp.prev = nextItem.prev;
                nextItem.prev.next = temp;
                temp.next = nextItem;
                nextItem.prev = temp;
            }
            size++;
            index++;
            lastItemReturned = null;
        }
    }

    public DoubleLinkedList(){

    }
    //-----------------------------------------
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (head == null) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean contains(Object o) {

        DoubleListIterator iterator = new DoubleListIterator(0);

        while(iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                return true;
            }

        }
        return false;

    }

    @Override
    public Iterator<E> iterator() {
        DoubleListIterator iterator = new DoubleListIterator(0);
        return iterator;
    }

    //Do Not Do
    @Override
    public Object[] toArray() {
        return new Object[0];
    }
    //Do Not Do
    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        add(size(), e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        DoubleListIterator iterator = new DoubleListIterator(0);

        while(iterator.hasNext()){
            if(o.equals(iterator.next())){
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    //Do Not Do
    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    //Do Not Do
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    //Do Not Do
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    //Do Not Do
    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    //Do Not Do
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }


    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        DoubleListIterator iterator = new DoubleListIterator(index);
        if( iterator.lastItemReturned == null){
            throw new IndexOutOfBoundsException();
        }
        return iterator.lastItemReturned.data;
    }

    @Override
    public E set(int index, E element) {
        DoubleListIterator iterator = new DoubleListIterator(index);
        if( iterator.lastItemReturned == null){
            throw new IndexOutOfBoundsException();
        }
        Node<E> temp = iterator.lastItemReturned;
        iterator.set(element);
        return temp.data;
    }

    @Override
    public void add(int index, E element) {
        DoubleListIterator iterator  = new DoubleListIterator(index);
        iterator.add(element);
    }

    @Override
    public E remove(int index) {
        DoubleListIterator iterator = new DoubleListIterator(index);
        if( iterator.lastItemReturned == null){
            throw new IndexOutOfBoundsException();
        }
        E temp = iterator.lastItemReturned.data;
        iterator.remove();
        return temp;
    }

    @Override
    public int indexOf(Object o) {
        DoubleListIterator iterator = new DoubleListIterator(0);
        while (iterator.hasNext()) {
            if (o.equals(iterator.next())) {
                return iterator.nextIndex() - 1;
            }
        }
           return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        DoubleListIterator iterator = new DoubleListIterator(size());
        while (iterator.hasPrevious()) {
            if (o.equals(iterator.previous())) {
                return iterator.previousIndex() +1;
            }
        }
        return - 1;
    }

    @Override
    public java.util.ListIterator<E> listIterator() {
        DoubleListIterator iterator  = new DoubleListIterator(0);
        return iterator;
    }

    @Override
    public java.util.ListIterator<E> listIterator(int index) {
        DoubleListIterator iterator  = new DoubleListIterator(index);
        return iterator;
    }

    //Do Not Do
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }
    //------------------------------------------------------


    @Override
    public String toString() {
        DoubleListIterator iterator  = new DoubleListIterator(0);
        String result = "[";

        while(iterator.hasNext()){
            result += iterator.nextItem.data + ", ";
            iterator.next();
        }
        if(result =="["){
            return "[]";
        }
        String result2 = result.substring(0, result.length()-2);
        return result2 + "]";

    }
}


