
import java.io.PrintWriter; //

/*this provides the methods for my calculator. AUTHOR: John Miller*/
public class BFCalculator {
    /*
     * this is a method that will grab the BigFraction at the specified register key
     * 
     * @pre: the inout fields are valid, and are not null
     * 
     * @post: returns a BigFraction that is either null (0/0), or the found fraction
     * in the register
     */
    public BigFraction getRegisterFraction(PrintWriter pen, char key, char[] register,
            BigFraction[] StoredFractions) {
        for (int i = 0; i < register.length; i++) {
            if (register[i] == key) {
                return StoredFractions[i];// return the BigFraction that corresponds with the key;
            }
        }
        pen.printf("there is no correspoinding register for the key you provided\n");
        return new BigFraction(0, 0);// if we couldn't find the BigFraction corresponding to the key, return a 0/0
                                     // BigFraction
    }// GetRegisteredFraction method

    /*
     * this is a method that I will use to replace characters (which are register
     * jeys) in a calculator problem with their
     * corresponding BigFraction object from the register arrays. so if a = 1/2, a +
     * a will be 1/2 + 1/2
     * 
     * @pre: st is a valid string, can be null or empty
     * 
     * @post: returns an error message if it did not work, or returns the string to
     * the main
     * method to be further evaluated
     */
    public String ErrorAndFormat(String st, PrintWriter pen, char[] register, BigFraction[] StoredFractions) {
        String[] parsedBySpace = st.split(" ");// splitting the array based on the spaces
        for (int i = 0; i < parsedBySpace.length; i++) {
            if (parsedBySpace[i].toCharArray()[0] >= (int) 'A' && parsedBySpace[i].toCharArray()[0] <= (int) 'Z'
                    || parsedBySpace[i].toCharArray()[0] >= (int) 'a'
                            && parsedBySpace[i].toCharArray()[0] <= (int) 'z') {
                char c = parsedBySpace[i].toCharArray()[0];
                BigFraction FoundFrac = getRegisterFraction(pen, c, register, StoredFractions);// call the finding
                                                                                               // method
                if (FoundFrac.denom.intValue() != 0)// if we found the fraction in the register
                {
                    parsedBySpace[i] = FoundFrac.toString();// turning this fraction into a string, and setting it in
                                                            // the array of strings
                }
            }
        }
        String returnSt = "";
        for (int i = 0; i < parsedBySpace.length; i++) {
            returnSt += parsedBySpace[i] + " ";// rebuilding the string with the possibly modified parsedBySpace array
        }
        return returnSt;// returning the string;
    }// ErrorAndFormat method

    /*
     * getOperators takes a string, which represents a mathematical expression, and
     * then gets
     * the mathematical operators which the string contains
     * 
     * @pre: the input operators have one space between each fraction
     * 
     * @post: throws an error if st is an invalid string, or returns a String array
     */
    public char[] getOperators(String str) {
        final int maxOperators = 100;
        char[] returnArr = new char[maxOperators];// maxOperators is an array to store the operators
        int returnIndex = 0;// indexing through the return array
        for (int i = 0; i < str.length(); i++) {
            if (str.toCharArray()[i] == '+' || str.toCharArray()[i] == '-' || str.toCharArray()[i] == '/'
                    || str.toCharArray()[i] == '*') {
                if (str.toCharArray()[i - 1] == ' ' && str.toCharArray()[i + 1] == ' ')// if it is a division operator
                                                                                       // and not a fraction num/denom
                                                                                       // operation
                {
                    returnArr[returnIndex] = str.toCharArray()[i];
                    returnIndex++;
                }
            }
        }
        return returnArr;
    }

    /*
     * Evaluate takes a string, which represents a mathematical expression, and then
     * computes
     * it and returns the bigFraction Value to which it all evaluates
     * 
     * @pre: the input fractions only have one space between each value, and
     * additionally are
     * all fractional values with a numerator and a denominator, giving an even
     * number of numbers
     * separated by spaces, slashes and operators. Operations is an array of
     * characters representing mathematical
     * operations
     * 
     * @post: throws an error if st is an invalid string, or returns a valid
     * BigFraction object
     */
    public BigFraction evaluate(String st, char[] Operations) {
        final int maxFractions = 100;// this is the maximum amount of fractions that the user can pass in
        String[] sArr = st.split("[ +*-]+");// splitting based on these chars
        BigFraction[] fracArray = new BigFraction[maxFractions];// setting aside a fraction array to store the input
                                                                // fractions
        int BigFracIndex = 0;
        for (int i = 0; i < sArr.length; i++) {
            if (sArr[i].contains("/") == false)// if the string is only made up of one value which
            // represents a whole
            {
                int num = Integer.parseInt(sArr[i]);
                fracArray[BigFracIndex] = new BigFraction(num, 1);
                BigFracIndex++;
            } else if (sArr[i].length() > 1) {// we have a string that represents a fraction, such as "1/2", that is
                // longer than 1 that contains '/' but is longer than just '/', so we know '/'
                // is not an operator
                String[] parsedArray = sArr[i].split("/");
                int num = Integer.parseInt(parsedArray[0]);
                int denom = Integer.parseInt(parsedArray[1]);
                fracArray[BigFracIndex] = new BigFraction(num, denom);
                BigFracIndex++;
            }
        } // for loop to get the parsed fractions/values into the array of BigFractions
          // values

        /*
         * at this point, the method has created an array of BigFractions and has found
         * the operators. now,
         * the method will combine the two to compute them, by going left to right
         */

        for (int i = 0; Operations[i] != '\0'; i++) {
            if (Operations[i] == '+')// if operator is equal to +
            {
                fracArray[0] = fracArray[0].add(fracArray[i + 1]);// adding i + 1 as it is the next index over

            } else if (Operations[i] == '-')// if operator is equal to -
            {
                fracArray[0] = fracArray[0].subtract(fracArray[i + 1]);// subtr i + 1 as it is the next index over
            } else if (Operations[i] == '*')// if operator is equal to *
            {
                fracArray[0] = fracArray[0].multiplyfrac(fracArray[i + 1]);// mult i + 1 as it is the next index over
            } else if (Operations[i] == '/')// if operator is equal to /
            {
                fracArray[0] = fracArray[0].divide(fracArray[i + 1]);// dividing i + 1 as it is the next index over
            }
        }

        /*
         * eventually, the BigFraction array will contain the simplified BigFraction at
         * it's index 0
         */
        fracArray[0].simplifyFraction();// calling the simplifyFraction method to simplify the fraction
        return fracArray[0];
    }// evaluate

    public boolean store(char register, BigFraction frac, BigFraction[] fractions, char[] registerArray,
            PrintWriter pen) {
        int index = 0;
        if (frac == null) {
            pen.printf("there is no BigFraction object to store\n");
        }
        while (fractions[index] != null && registerArray[index] != '\0')// while the index isn't taken by another
                                                                        // register
        {
            if (registerArray[index] == register) {
                pen.printf("The register character you gave is already take, so it couldn't be stored");
                return false;
            } else {
                index++;
            }
        } // while loop to trace the storage arrays
        if (register - '0' <= 9 && register - '0' >= 0) {
            pen.printf(
                    "you gave a numeric character as the character key, the key must be alphabetical, the object cannot be stored\n");
            return false;
        } else {
            registerArray[index] = register;// at the free index, set the register equal to the given char
            fractions[index] = frac;// at the free index, set the register equal to the implied BigFraction
            return true;
        }
    }// store
}