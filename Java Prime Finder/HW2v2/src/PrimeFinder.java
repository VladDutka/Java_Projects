import java.util.ArrayList;
import java.util.List;


public class PrimeFinder {

    public static void main(String[] args) {
        double start = Double.parseDouble(args[0]);
        double end = Double.parseDouble(args[1]);
        //Converts the start and end to int.
        start = (int) start;
        end = (int) end;
        List<Integer> primeNumbers = new ArrayList<>();
        //Checks for different possible errors that could happen
        if (start > end){
            throw new Error("Error: max must be greater than or equal to min");
        } else if (start < 0) {
            throw new Error("Error: min must be between 0 and 1,000,000.");
        }
        //Iterates through numbers from the start to the end
        for (int i = (int) start; i <= end; i++) {
            //Assume the number is prime first
            boolean isPrime = true;
            //Checks if it is
            for (int x = 2; x <= i / 2; x++) {
                if (i % x == 0) {
                    isPrime = false;
                    break;
                }
            }
            //If the number is still assumed to be prime, it adds to list
            if (isPrime){
                primeNumbers.add(i);
            }
        }
        //Prints out the List of Numbers
        for(int y : primeNumbers)
        {
            System.out.print(y + ", ");
        }
    }
}
