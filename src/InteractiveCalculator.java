import java.util.Scanner;
import java.io.PrintWriter;

/*InteractiveCalculator is a class that will repeatedly promp a user for input, and will then call
upon the methods in the BFCalculator class for computation. AUTHOR: John Miller*/
public class InteractiveCalculator {

    /*
     * InitialErrorHandling is a method that checks for erroneous syntax in the
     * input string
     * 
     * @pre: Str and Pen are valid, str can be empty
     * 
     * @post: returns a boolean representing whether the string is proper
     */
    public boolean InitialErrorHandling(String str, PrintWriter pen) {
        if (str.compareTo("") == 0 || str.compareTo(" \n") == 0 || str.compareTo("  \n") == 0) {
            pen.printf("please enter proper input: ");
            return false;
        }
        if (str.toCharArray()[0] >= 42 && str.toCharArray()[0] <= 45) {
            pen.printf("please do not start with an operator: ");
            return false;
        }
        if (str.toCharArray()[str.length() - 1] >= 42 && str.toCharArray()[str.length() - 1] <= 45) {
            pen.printf("please do not end with an operator: ");
            return false;
        }

        return true;
    }// InitialErrorHandling method

    /*
     * Calculator takes the objects, String input, and arrays that correspond with
     * the register, and returns the
     * final computed value
     * 
     * @pre: inputs are valid, can be null
     * 
     * @post: returns a valid BigFraction object
     */
    public BigFraction Calculator(BFCalculator calc, String input, BigFraction[] storageArr, char[] registerArr,
            PrintWriter pen) {
        char[] operators = calc.getOperators(input);// getting the operators of the string
        input = calc.ErrorAndFormat(input, pen, registerArr, storageArr);// formatting the input
        BigFraction solution = calc.evaluate(input, operators);// performing the operations
        pen.printf("Solution = %d/%d\n", solution.num.intValue(), solution.denom.intValue());
        return solution;
    }// calculator method

    /*
     * This is the main class that will repeatedly prompt the user for input, until
     * the
     * user enters the command "QUIT". it can store up to 100 individual fractions,
     * I picked this number as it sounded about right
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final int MAX_VALS = 100;
        PrintWriter pen = new PrintWriter(System.out, true);// pen for printing
        BigFraction[] storageArr = new BigFraction[MAX_VALS];// setting aside an array of BigFraction objects for
                                                             // storing
        BFCalculator calc = new BFCalculator();// initializing the BFCalculator
        InteractiveCalculator InterCalc = new InteractiveCalculator();// initializing the InteractiveCalculator
        char[] registerArr = new char[MAX_VALS];
        pen.printf(
                "this is a program that will repeatedly prompt you for calculator input until you write QUIT.\n you may store your prevous answer with the commans STORE reg, where reg is a letter of the alphabet. please allow spaces\n between operators and fractions: ");
        BigFraction solution = null; // starting off with a null BigFraction
        String input = sc.nextLine();// getting the input
        while (input.compareTo("QUIT") != 0) {
            String[] sArr = input.split(" ");
            if (sArr[0].compareTo("STORE") == 0) {
                calc.store(sArr[1].toCharArray()[0], solution, storageArr, registerArr, pen);// passing the input and
                pen.printf("the solution has been stored; what is your next input: "); // solution to the Store
                input = sc.nextLine(); // method
            } else if (input.compareTo("QUIT") != 0)// if the input is not QUIT
            {
                boolean check = InterCalc.InitialErrorHandling(input, pen);// handling errors
                if (check != false) {
                    solution = InterCalc.Calculator(calc, input, storageArr, registerArr, pen);// passing the input to
                    pen.printf("the solution has been found, what is your next command:"); // the
                    // Calculator

                }
                input = sc.nextLine();
            }
        }
        pen.printf("program teminated; have a nice day!");
        sc.close();// closing the scanner
        pen.flush();// flushing the pen
    }// while loop for prompts
}// main method
