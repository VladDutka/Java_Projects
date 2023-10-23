import stdlib.StdOut;


public class GreatCircle {
    // Entry point.
    public static void main(String[] args) {
        // Accept x1 (double), y1 (double), x2 (double),
        // And y2 (double) as command-line arguments.
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);
        // Convert the angles to radians.
        double X1 = Math.toRadians(x1);
        double Y1 = Math.toRadians(y1);
        double X2 = Math.toRadians(x2);
        double Y2 = Math.toRadians(y2);


        // Calculate great-circle distance d.
        double miny = Y1 - Y2;
        double cosy = Math.cos(miny);
        double res1 = Math.sin(X1) * Math.sin(X2) + Math.cos(X1) * Math.cos(X2) * cosy;
        double res2 = Math.acos(res1);
        double d = 6359.83 * res2;

        // Write d to standard output.
        StdOut.println(d);

    }
}