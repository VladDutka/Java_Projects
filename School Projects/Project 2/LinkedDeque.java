import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
// list as the underlying data structure.
public class LinkedDeque<Item> implements Iterable<Item> {
    private Node first; // front of the deque.
    private Node last;  // back of the deque.
    private int n;      // size of the deque.

    // makes an empty dupe and sets its size to zero
    public LinkedDeque() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    // if nothing in dupe return
    public boolean isEmpty() {
        return n == 0;
    }

    // returns items in dupe
    public int size() {
        return n;
    }

    // puts items in front of dupe
    public void addFirst(Item item) {
        // item = null, throw exception.
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        // Makes a new node that stores the input value
        Node isSecond = this.first;
        first = new Node();
        first.item = item;
        first.next = isSecond;

        // if no other nodes, sets first and last node to be same
        if (isEmpty()) {
            last = first;
        } else {
            isSecond.prev = first;
        }

        n++;   
    }

    // Adds item to the back of this deque.
    public void addLast(Item item) {
        // item = null, throw exception.
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        // New node that stores input value
        Node isThird = last;
        last = new Node();
        last.item = item;
        last.prev = isThird;

       // if no other nodes, sets first and last node to be same
        if (isEmpty()) {
            first = last;
        } else {
            isThird.next = last;
        }

        n++;    
    }

    // Returns item that are in the back
    public Item peekFirst() {
        // no values in the deque, throw an exception.
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        return first.item;
    }

    // Removes and Returns item that are in the back
    public Item removeFirst() {
        // no values in the deque, throw an exception
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item oldFirstItem = first.item; 

        n--;
        // if no items in it, sets both nodes to null
        if (isEmpty()) {
            last = null;
            first = null;
        }   else {
            first = first.next;
        }

        return oldFirstItem;
    }

    // Returns item that are in the back
    public Item peekLast() {
        //  no values in the deque, throw an exception
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        return last.item;
    }

    // deletes item and returns it to the back 
    public Item removeLast() {
        // no values in the deque, throw an exception.
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }

        Item oldFinalItem = last.item;  
        // if no items in it, sets both nodes to null
        n--;
        if (isEmpty()) {
            first = null;
            last = null;
        }   else {
            last = last.prev;
        }

        return oldFinalItem;
    }

    // Returns an iterator to iterate over the items in this deque from front to back.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // returns a string that is the dupe
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : this) {
            stringBuilder.append(item);
            stringBuilder.append(", ");
        }
        return n > 0 ? "[" + stringBuilder.substring(0, stringBuilder.length() - 2) + "]" : "[]";
    }

    // A deque iterator.
    private class DequeIterator implements Iterator<Item> {
        // Makes a Node that is the current postion in the dupe
        private Node current;

        // Constructs an iterator.
        public DequeIterator() {
            this.current = first;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return current != null;
        }

        // Returns the next item.
        public Item next() {
            // no more values to iterate, throw an exception
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty");
            }

            // Return the item and moves to the next
            Item next = current.item;
            current = current.next;
            return next;
        }
    }

    // A data type to represent a doubly-linked list. Each node in the list stores a generic item
    // and references to the next and previous nodes in the list.
    private class Node {
        private Item item;  // item
        private Node next;  // next node
        private Node prev;  // previous node
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}