import java.util.Arrays;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    Term[] terms;   // Array of terms.
    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        // If given array is null, throw an exception.
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        this.terms = Arrays.copyOf(terms, terms.length);
        Arrays.sort(this.terms);    // Sort the array in lexicographic order.
    }
    // Returns all terms that start with prefix, in descending order of their weights
    public Term[] allMatches(String prefix) {
        // If given prefix is null, throw an exception.
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        Term prefixTerm = new Term(prefix); // Create the prefix term to find in the array.
        int firstIndex = BinarySearchDeluxe.firstIndexOf(this.terms, prefixTerm, 
            Term.byPrefixOrder(prefix.length()));
        int n = numberOfMatches(prefix);    // Number of matches to prefix in the array
        // If there are no matches in the array, return an empty array.
        if (n == 0) {
            return new Term[0];
        }
        // Create a new list matches that contains all the matches and sort them by reverse weight.
        Term[] matches = Arrays.copyOfRange(this.terms, firstIndex, firstIndex + n);
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches; // Return the sorted matches array.
    }
    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        // If given prefix is null, throw an exception.
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        Term prefixTerm = new Term(prefix); // Create the prefix term to find in the array.
        // Find the indices of the first and last example of given prefix in the array.
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, prefixTerm, 
            Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(this.terms, prefixTerm, 
            Term.byPrefixOrder(prefix.length()));
        // If no examples were found, return 0 (0 matches found).
        if (i == -1) {
            return 0;
        }
        return j - i + 1;   // Return number of matches.
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
