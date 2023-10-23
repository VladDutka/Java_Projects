import stdlib.StdOut;
import stdlib.StdRandom;

public class Die implements Comparable<Die> {
    private int value; // the face value

    // Constructs a die.
    public Die() {
        roll();
    }
    // Rolls this die.
    public void roll() {
        value = (StdRandom.uniform(6) + 1);
    }

    // Returns the face value of this die.
    public int value() {
        return value;
    }

    // Returns true if this die is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (this == other) {
            return true; 
        }
            
        if (other == null) {
            return false;
        }
           
        if (getClass() != other.getClass()) {
            return false;
        }
           
        Die die = (Die) other;
        if (value != die.value) {
            return false;
        }
            
        return true;
    }

    // Returns a comparison of this die with other, by their face values.
    public int compareTo(Die that) {
        return this.value() - that.value();
    }

    // Returns a string representation of this die.
    public String toString() {
        if (value == 1) {
            return "     \n  *  \n     ";
        } else if (value == 2) {
            return "*    \n     \n    *";
        } else if (value == 3) {
            return "*    \n  *  \n    *";
        } else if (value == 4) {
            return "*   *\n     \n*   *";
        } else if (value == 5) {
            return "*   *\n  *  \n*   *";
        } else {
            return "*   *\n*   *\n*   *";
        }
            
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Die a = new Die();
        a.roll();
        while (a.value() != x) {
            a.roll();
        }
        Die b = new Die();        
        b.roll();
        while (b.value() != y) {
            b.roll();
        }
        Die c = new Die();        
        c.roll();
        while (c.value() != z) {
            c.roll();
        }
        StdOut.println("Dice a, b, and c:");
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(c);
        StdOut.println("a.equals(b)    = " + a.equals(b));
        StdOut.println("b.equals(c)    = " + b.equals(c));
        StdOut.println("a.compareTo(b) = " + a.compareTo(b));
        StdOut.println("b.compareTo(c) = " + b.compareTo(c));
    }
}
