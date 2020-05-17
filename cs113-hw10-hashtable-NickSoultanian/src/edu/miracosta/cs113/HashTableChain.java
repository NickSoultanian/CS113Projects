package edu.miracosta.cs113;
/**
 *
 * HashTableChain.java: This is a class that implements the List<E> interface. there are two private classes.
 * Node<E> class which is static and holds nodes for linkedlists nodes. the second is a DoubleListIterator which implements
 * ListIterator<E>. inside the doublelistiterator is methods head taken from ListIterator<E>. then for the main class is
 * method heads overridden from list.
 *
 * @author Nick Soultanian
 *
 * @version 1.0
 *
 */
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public class HashTableChain<K, V> implements Map<K, V> {


    private LinkedList<Entry<K, V>>[] table;

    private int numKeys;

    private static final int CAPACITY = 101;

    private static final double LOAD_THRESHOLD = 3.0;

    private class SetIterator implements Iterator<Map.Entry<K,V>> {

        int index = 0;
        Entry<K,V> lastItemReturned = null;
        Iterator<Entry<K,V>> itr = null;

        @Override
        public boolean hasNext() {
            if(itr != null && itr.hasNext()) {
                return true;
            }
            do {
                index++;
                if (index>= table.length) {
                    return false;
                }
            } while (table[index] == null);
            itr = table[index].iterator();
            return itr.hasNext();
        }

        @Override
        public Map.Entry<K, V> next() {
            if (itr.hasNext()) {

                lastItemReturned = itr.next();
                return lastItemReturned;
            } else {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() throws IllegalStateException {
            if (lastItemReturned == null) {
                throw new IllegalStateException();
            } else {
                itr.remove();
                lastItemReturned = null;
            }
        }
    }
    // Inner class EntrySet
    private class EntrySet extends AbstractSet<Map.Entry<K,V>> {

        /** Return the size of the set. */
        @Override
        public int size() {
            return numKeys;
        }

        /** Return an iterator over the set. */
        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new SetIterator();
        }
    }

    /** Contains key-value pairs for a hash table. */
    private static class Entry<K, V> implements Map.Entry<K, V> {

        /** The key */
        private K key;
        /** The value */
        private V value;

        /**
         * Creates a new key-value pair.
         * @param key The key
         * @param value The value
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * Retrieves the key.
         * @return The key
         */
        @Override
        public K getKey() {
            return key;
        }

        /**
         * Retrieves the value.
         * @return The value
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * Sets the value.
         * @param val The new value
         * @return The old value
         */
        @Override
        public V setValue(V val) {
            V oldVal = value;
            value = val;
            return oldVal;
        }

        public String toString() {
            return key + "=" + value;
        }
    }


    @SuppressWarnings("unchecked")
    public HashTableChain() {
        table = new LinkedList[CAPACITY];
    }

    /*<listing chapter="7" number="9">*/
    /**
     * Method get for class HashtableChain.
     * @param key The key being sought
     * @return The value associated with this key if found;
     *         otherwise, null
     */
    @Override
    public V get(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null; // key is not in the table.
        }
        // Search the list at table[index] to find the key.
        for (Entry<K, V> nextItem : table[index]) {
            if (nextItem.key.equals(key)) {
                return nextItem.value;
            }
        }

        // assert: key is not in the table.
        return null;
    }
    /*</listing>*/

    /*<listing chapter="7" number="10">*/
    /**
     * Method put for class HashtableChain.
     * @post This key-value pair is inserted in the
     *       table and numKeys is incremented. If the key is already
     *       in the table, its value is changed to the argument
     *       value and numKeys is not changed.
     * @param key The key of item being inserted
     * @param value The value for this key
     * @return The old value associated with this key if
     *         found; otherwise, null
     */
    @Override
    public V put(K key, V value) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            // Create a new linked list a
            table[index] = new LinkedList<Entry<K, V>>();
        }

        // Search the list
        for (Entry<K, V> nextItem : table[index]) {

            if (nextItem.key.equals(key)) {
                // Replace value for this key.
                V oldVal = nextItem.value;
                nextItem.setValue(value);
                return oldVal;
            }
        }


        table[index].addFirst(new Entry<K, V>(key, value));
        numKeys++;
        if (numKeys > (LOAD_THRESHOLD * table.length)) {
            rehash();
        }
        return null;
    }


    /** Returns true if empty */
    public boolean isEmpty() {
        return numKeys == 0;
    }

    /**
     * Method to rehash table.
     * Allocates a new hash table that is double the size and has an odd length.
     * Reinsert each table entry in the new hash table.
     */
    private void rehash() {
        LinkedList<Entry<K,V>>[] oldTable = table;
        table = new LinkedList[oldTable.length * 2 + 1];
        numKeys = 0;
        for (LinkedList<Entry<K, V>> list : oldTable) {
            if (list != null) {
                for (Entry<K,V> entry : list) {
                    put(entry.getKey(), entry.getValue());
                    numKeys++;
                }
            }
        }
    }


    @Override
    public void clear() {
        for (LinkedList<Entry<K,V>> list : table) {
            list = null;
        }
    }


    @Override
    public boolean containsKey(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return false;
        }
        for (Entry<K,V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean containsValue(Object value) {
        for (LinkedList<Entry<K,V>> list : table) {
            for (Entry<K,V> entry : list) {
                if (entry.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }



    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>(size());
        for (LinkedList<Entry<K,V>> list : table) {
            for (Entry<K,V> entry : list) {
                if (entry != null) {
                    keySet.add(entry.getKey());
                }
            }
        }
        return keySet;
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }


    @Override
    public V remove(Object key) {
        int index = key.hashCode() % table.length;
        if (index < 0) {
            index += table.length;
        }
        if (table[index] == null) {
            return null; // key is not in table
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                V value = entry.getValue();
                table[index].remove(entry);
                numKeys--;
                if (table[index].isEmpty()) {
                    table[index] = null;
                }
                return value;
            }
        }
        return null;
    }


    @Override
    public int size() {
        return numKeys;
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a String representation of the map.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append("[" + i + "] ");
            for (Entry<K,V> entry : table[i]) {
                sb.append("-> " + entry + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTableChain)) return false;
        HashTableChain<?, ?> that = (HashTableChain<?, ?>) o;
        return numKeys == that.numKeys &&
                Arrays.equals(table, that.table);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(numKeys);
        result = 31 * result + Arrays.hashCode(table);
        return result;
    }
}
