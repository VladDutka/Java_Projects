import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Return the index of the first key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        // throws error if A, key or Comparator is null
    	if (a == null) {
            throw new NullPointerException("a, key, or c is null");
        } 
        if (key == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        if (comparator == null) {
    		throw new NullPointerException("a, key, or c is null");
    	}
        if (a.length <= 0) {
            return -1;
        }
    	int low = 0;
    	int high = a.length - 1;
    	if (comparator.compare(a[0], key) == 0) {
    		return 0;	//  base case.
    	}
    	while (low <= high) {
    		int mid = low + (high - low) / 2;
    		//  compares the key is being sorted with.
    		if (comparator.compare(key, a[mid]) < 0) {
                high = mid - 1;
            } else if (comparator.compare(key, a[mid]) > 0) {
                low = mid + 1;
            } else if (comparator.compare(a[mid - 1], a[mid]) == 0) {
                high = mid - 1;
    		
            } else {
                return mid;
            } 
    	}
		return -1;		// Index of the first occurrence of an element matching key
    }

    // Return the index of the last key in a[] that equals the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        // throws error if A, key or Comparator is null
    	if (a == null) {
            throw new NullPointerException("a, key, or c is null");
        } 
        if (key == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        if (comparator == null) {
    		throw new NullPointerException("a, key, or c is null");
    	}
        if (a.length <= 0) {
            return -1;
        }
    	int low = 0;
    	int high = a.length - 1;
    	if (comparator.compare(a[high], key) == 0) {
    		return high;	// base case.
    	}
    	while (low <= high) {
    		int mid = low + (high - low) / 2;
    		// Searches for the last occurrence: key.
    		if (comparator.compare(key, a[mid]) < 0) {
                high = mid - 1;
            } else if (comparator.compare(key, a[mid]) > 0) {
                low = mid + 1;
            } else if (comparator.compare(a[mid + 1], a[mid]) == 0) { 
                low = mid + 1;
            } else {
                return mid;
            } 
    	}
		return -1;			// Index of the last occurrence of an element matching key
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
