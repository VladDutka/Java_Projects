import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {

        // lowest boundry of sample
        int low = Integer.parseInt(args[0]);

        // upper boundry of sample
        int high = Integer.parseInt(args[1]);

        // number of samples that are needed to be collected
        int samples = Integer.parseInt(args[2]);

        // the method that is used
        String method = args[3];

        // itiliazes a queue 
        ResizingArrayRandomQueue<Integer> queue = new ResizingArrayRandomQueue<>();
        
        // makes a queue from low to high
        for (int i = low; i <= high; i++) {
            queue.enqueue(i);
        }

        // samples the shuffled queue n times
        // based of method makes use of a replacement
        if (method.equals("+")) {
            while (samples > 0) {
                StdOut.println(queue.sample());
                samples--;
            }

        } else if (method.equals("-")) {
            while (samples > 0) {
                StdOut.println(queue.dequeue());
                samples--;
            }
        } else {
            throw new IllegalArgumentException("Illegal mode");
        }
    }
}
