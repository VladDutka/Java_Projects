import stdlib.StdOut;

public class Harmonic {
    // Entry point.
    public static void main(String[] args) {
        // Accept n (int) as command-line argument.
        int n = Integer.parseInt(args[0]);

        // Set total to the rational number 0.
        double total = 0;

        // For each 1 <= i <= n, add the rational term 1 / i to total.
        for (int i = 1; i <= n; i++) {
            total = total + 1.0 / i;


        }

        // Write total to standard output.
        StdOut.println(total);
    }
}
