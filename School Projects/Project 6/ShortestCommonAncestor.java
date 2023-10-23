import dsa.SeparateChainingHashST;
import dsa.DiGraph;
import dsa.BFSDiPaths;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;


public class ShortestCommonAncestor {
    // Graph to store connections between synsets.
    DiGraph G;  

    // Constructs a ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(DiGraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        this.G = G;
    }

    // Returns length of the shortest ancestral path between vertices v and w.
    public int length(int v, int w) {
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is invalid");
        }

        SeparateChainingHashST<Integer, Integer> vST = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wST = distFrom(w);

        int ancestor = ancestor(v, w);  // Shortest common ancestor between v and w.

        // If there is no ancestor, return 0.
        if (ancestor == -1) {
            return 0;
        }

        // Return the sum of the distances to the shortest ancestor.
        return vST.get(ancestor) + wST.get(ancestor);
    }

    // Returns a shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {
        if (v < 0 || v >= G.V()) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w >= G.V()) {
            throw new IndexOutOfBoundsException("w is invalid");
        }
        SeparateChainingHashST<Integer, Integer> vST = distFrom(v);
        SeparateChainingHashST<Integer, Integer> wST = distFrom(w);

        // The shortest common ancestor.
        int x = -1;

        // The minimum length between 2 nodes.
        int minLength = Integer.MAX_VALUE;  

        // Look through all the keys and find shortest common ancestor.
        for (int i: vST.keys()) {
            if (wST.contains(i)) {
                int currentLength = vST.get(i) + wST.get(i);
                if (currentLength <= minLength) {
                    x = i;
                    minLength = currentLength;
                }
            }
        }

        return x;
    }

    // Returns length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }

        int[] triad = triad(A, B);
        return length(triad[1], triad[2]);
    }

    // Returns a shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }

        int[] triad = triad(A, B);

        return triad[0];
    }

    // Returns a map of vertices reachable from v and their respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> distancesToV = new SeparateChainingHashST<>();
        BFSDiPaths bfs = new BFSDiPaths(G, v);

        // Store the distance of each node that is reachable from v.
         
        for (int i = 0; i < G.V(); i++) {
            if (bfs.hasPathTo(i)) {
                if (distancesToV.contains(i)) {
                    continue;
                }
                distancesToV.put(i, (int) bfs.distTo(i));
            }
        }
         // Return  table that contains all the distances
        return distancesToV;   
    }


    // Returns an array consisting of a shortest common ancestor a of vertex subsets A and B,
    // vertex v from A, and vertex w from B such that the path v-a-w is the shortest ancestral
    // path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        // The vertex in A that is closest to a vertex in B.
        int shortestPathA = -1; 
        // The vertex in B that is closest to a vertex in A.
        int shortestPathB = -1;
        int shortestLength = Integer.MAX_VALUE;
        int ancestor = -1;

        // Look through all combinations of the subsets, and find the 2 nodes with the shortest
        // ancestral path.

        for (int i: A) {
            for (int j: B) {
                if (length(i, j) < shortestLength) {
                    shortestLength = length(i, j);
                    shortestPathA = i;
                    shortestPathB = j;
                    ancestor = ancestor(i, j);
                }
            }
        }
        // Triad to store the ancestor
        int[] triad = {ancestor, shortestPathA, shortestPathB}; 

        return triad;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);
        in.close();
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
