import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a random queue, implemented using a resizing array as the underlying
// data structure.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    // Array to store the items in the queue
    Item[] q;
     // Size of the queue
    int n;

    // Constructs an empty random queue.
    public ResizingArrayRandomQueue() {
        q = (Item[]) new Object[2];
        n = 0;
    }

    // Returns true if this queue is empty, and false otherwise.
    public boolean isEmpty() {
        return n == 0;
    }

    // Returns the number of items in this queue.
    public int size() {
        return n;
    }

    // Adds item to the end of this queue.
    public void enqueue(Item item) {
        // If the item is null, throw exception
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        if (q.length == n) {
            resize(2 * q.length);
        }

        q[n++] = item;
    }

    // Returns a random item from this queue.
    public Item sample() {
        // If there are no values in the queue, throw an exception
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        // Return a random value in the queue
        return q[StdRandom.uniform(n)];
    }

    // Removes and returns a random item from this queue.
    public Item dequeue() {
        // If there are no values in the deque, throw an exception.
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        // Randomly select index of value in the queue.
        int index = StdRandom.uniform(n);

        Item randomItem = q[index];
        q[index] = q[n - 1];
        q[n - 1] = null;

        if (q.length <= n / 4) {
            resize(n / 2);
        }

        n--;

        return randomItem;
    }

    // Returns an iterator to iterate over the items in this queue in random order
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // Returns a string representation of this queue.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        // index of current item
        int current;
        // is an array which stores the items that q
        Item[] items;

        // Constructs an iterator.
        public RandomQueueIterator() {
            current = 0;
            // Create a copy of q with length n, store it as items
            items = Arrays.copyOf(q, n);
            StdRandom.shuffle(items);
        }

        // Returns true if there are more items to iterate, and false if not
        public boolean hasNext() {
            return current < n;
        }

        // Returns the next item.
        public Item next() {
            // If no more values to iterate, throw an exception.
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty");
            }

            return items[current++];
        }
    }

    // Resizes the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<>();
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniform(10000);
            q.enqueue(r);
            sum += r;
        }
        int iterSumQ = 0;
        for (int x : q) {
            iterSumQ += x;
        }
        int dequeSumQ = 0;
        while (q.size() > 0) {
            dequeSumQ += q.dequeue();
        }
        StdOut.println("sum       = " + sum);
        StdOut.println("iterSumQ  = " + iterSumQ);
        StdOut.println("dequeSumQ = " + dequeSumQ);
        StdOut.println("iterSumQ + dequeSumQ == 2 * sum? " + (iterSumQ + dequeSumQ == 2 * sum));
    }
}