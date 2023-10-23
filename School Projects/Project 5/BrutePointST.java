import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

public class BrutePointST<Value> implements PointST<Value> {
    // a treed that stores 2d points
    RedBlackBinarySearchTreeST<Point2D, Value> bst; 

    // Constructs an empty symbol table.
    public BrutePointST() {
        bst = new RedBlackBinarySearchTreeST<Point2D, Value>();
    }

    // Returns true if this symbol table is empty, and false otherwise.
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size() {
        return bst.size();
    }

    // Inserts the given point and value into this symbol table.
    public void put(Point2D p, Value value) {
        // If p is null throw error
        if (p == null) {
            throw new NullPointerException("p is null");
        }
         // If value is null throw error
        if (value == null) {
            throw new NullPointerException("value is null");
        }
        bst.put(p, value);
    }

    // Returns the value associated with the given point in this symbol table, or null.
    public Value get(Point2D p) {
         // If p is null throw error
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.get(p);
    }

    // Returns true if this symbol table contains the given point, and false otherwise.
    public boolean contains(Point2D p) {
         // If p is null throw error
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return bst.contains(p);
    }

    // Returns all the points in this symbol table.
    public Iterable<Point2D> points() {
        return bst.keys();
    }

    // Returns all the points in this symbol table that are inside the given rectangle.
    public Iterable<Point2D> range(RectHV rect) {
         // If rect is null throw error
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        // Linked queue called queue is made
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        // Loop to go throw the qeuue and if p is rect and if yes add it to the queue
        for (Point2D p : points()) {
            if (rect.contains(p)) {
                queue.enqueue(p);
            }
        }
        return queue;
    }

    // Returns the point in this symbol table that is different from and closest to the given point,
    // or null.
    public Point2D nearest(Point2D p) {
        // Throws error if P is null
        if (p == null) {
            throw new NullPointerException("p is null");
        }

        Point2D ans = null;
        double runningMinDist = Double.POSITIVE_INFINITY;
        // Loops through the Tree 
        for (Point2D p2 : points()) {
            // Distance is set to be distance squared from p to p2
            double distance = p.distanceSquaredTo(p2);
            if (!p2.equals(p) && distance < runningMinDist) {
                runningMinDist = distance;
                ans = p2;
            }
        }
        return ans;
    }

    // Returns up to k points from this symbol table that are different from and closest to the
    // given point.
    public Iterable<Point2D> nearest(Point2D p, int k) {
         // If p is null throw error
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        // MinPQ is made called pq and size and distancetooeder is passed
        MinPQ<Point2D> pq = new MinPQ<Point2D>(size(), p.distanceToOrder());
        // Loop to go through the tree and if p2 doesn't equal p insert p2 into pq
        for (Point2D p2 : points()) {
            if (!p2.equals(p)) {
                pq.insert(p2);
            }
        }
        // Linkedqueue is made called queue
        LinkedQueue<Point2D> queue = new LinkedQueue<Point2D>();
        // Loops till empty to find the min values and add them to queue thus making 
        // Smallest to greatest.
        for (int i = 0; i < k && !pq.isEmpty(); i++) {
            Point2D temp = pq.delMin();
            queue.enqueue(temp);
        }
        return queue;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}