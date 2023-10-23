package src;
import java.util.Scanner;
public class Calculator {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String op;
        double x;
        double y;
        double z;
        //Interactive Mode
        if (args.length < 1) {
            System.out.print("Operation? ");
            op = sc.next();
            System.out.print("First Number: ");
            x = sc.nextDouble();
            System.out.print("Second Number: ");
            y = sc.nextDouble();
        }   else {
            //Non-Interactive Mode
            x = Integer.parseInt(args[0]);
            op = args[1];
            y = Integer.parseInt(args[2]);
        }
        //Checks if Operator is Valid
        switch (op) {
            case "+" -> z = x + y;
            case "-" -> z = x - y;
            case "x" -> z = x * y;
            case "xx" -> z = Math.pow(x, y);
            case "%" -> z = x % y;
            case "/" -> {
                if (y == 0) {
                    throw new Error("Invalid value for the second operand. Divisor cannot be zero.");
                }
                z = x / y;
            }
            default -> throw new Error("Invalid Operand. Valid operands are: +, -, x, /, %, xx ");
        }
        System.out.printf("%f %s %f = %f", x, op, y, z);

    }
}