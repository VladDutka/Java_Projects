import stdlib.StdOut;

public class Ramanujan1 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int a = 1; a * a * a <= n; a++) {
            for (int b = a + 1; b * b * b + a * a * a <= n; b++) {
                for (int c = a + 1; c * c * c <= n; c++) {
                    for (int d = a + 1; d * d * d + c * c * c <= n; d++) {
                        if (a * a * a + b * b * b == c * c * c + d * d * d && c < d) {
                            int total = a * a * a + b * b * b;
                            StdOut.printf("%d = %d^3 + %d^3 = %d^3 + %d^3\n", total, a, b, c, d);
                        }
                    }
                }
            }
        }
    }
}
