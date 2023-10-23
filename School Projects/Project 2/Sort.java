import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {

        // creates a new deque
        LinkedDeque<String> s =  new LinkedDeque<>();

        // creats a new string w and read first word
        String w = StdIn.readString();

        // add word to deque
        s.addFirst(w);

        // for each word add it to deque sorted
        while (!StdIn.isEmpty()) {
            
            // reads it
            w = StdIn.readString();

            // if word come before word  in dupe add to beginning
            if (less(w, s.peekFirst())) {
                s.addFirst(w);

            // if word come after word in dupe add to end
            } else if (less(s.peekLast(), w)) { 
                s.addLast(w);

            // else keep popping until w is in the correct spot
            } else {
                LinkedStack<String> q = new LinkedStack<>();

                while (!less(w, s.peekFirst())) {
                    q.push(s.removeFirst());
                    if (s.isEmpty()) {
                        break;
                    }
                }
                
                // add w to beginning of dupe
                s.addFirst(w);

                // replace word in queue
                while (!q.isEmpty()) {
                    s.addFirst(q.pop());
                }
            }

        }
        // prints the dupe, that is sorted in alphabetical order
        for (String word: s) {
            StdOut.println(word);
        }

    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
