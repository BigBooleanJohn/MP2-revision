import java.util.Scanner;
import java.io.PrintWriter;

/*QuickCalculator is a program written by John Miller. It utilizes some of the methods in Interactive
 * Calculator and BFCalculator to take one input, compute it, and return the result.AUTHOR: John Miller
 */
public class QuickCalculator {

    /*
     * containsInt is a method that checks an input, in this case a parsed string,
     * to see if it contains an integer that represents part of an operation. it
     * also
     * checks for an empty string and returns false if it is empty.
     * 
     * @pre: st is a valid strings
     * 
     * @post: returns a boolean
     * 
     * 
     */
    public static boolean containsInt(String st) {
        if (st.compareTo("") == 0 || st.compareTo(" ") == 0) {
            return false;
        }//if
        for (int i = 0; i < st.length(); i++) {
            if (st.toCharArray()[i] - '0' > 0 || st.toCharArray()[i] - '0' < 9)
            {
                return true;
            }
        }//for
        return false;
    }// containsInt

    public static void main(String[] args) {
        PrintWriter pen = new PrintWriter(System.out, true);
        Scanner sc = new Scanner(System.in);
        pen.println(
                "this is a program that will prompt you for input once, and then print it out. please put each individual command in quotes, such as \"5 + 12\" and a space between each command:");
        String input = sc.nextLine();
        sc.close();
        String[] commands = input.split("\"");
        int MAX_VALS = 100;
        BigFraction result = null;
        BigFraction[] fractions = new BigFraction[MAX_VALS];
        char[] registerArray = new char[MAX_VALS];
        BFCalculator calc = new BFCalculator();
        InteractiveCalculator calc2 = new InteractiveCalculator();
        for (int i = 0; i < commands.length; i++) {
            if (commands[i].contains("STORE") == true) {
                String[] StoreArr = commands[i].split(" ");
                calc.store(StoreArr[1].toCharArray()[0], result, fractions, registerArray, pen);
            } else if (containsInt(commands[i]) == true) {
                boolean check = calc2.InitialErrorHandling(commands[i], pen);
                if (check != false) {
                    result = calc2.Calculator(calc, commands[i], fractions, registerArray, pen);
                }
            }
        }//for
    }// main
}
