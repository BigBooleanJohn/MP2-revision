
import java.math.BigInteger;

/*this provides the constructors and object declarations for my calculator. AUTHOR: John Miller*/
public class BigFraction {

  // +--------+-------------------------------------------------------
  // | Fields |
  // +--------+

  /** The numerator of the BigFraction. Can be positive, zero or negative. */
  BigInteger num;

  /** The denominator of the BigFraction. Must be non-negative. */
  BigInteger denom;

  // +--------+-------------------------------------------------------
  // | Constructors |
  // +--------+

  public BigFraction(BigInteger num, BigInteger denom) {
    this.num = num;
    this.denom = denom;
  } // BigFraction(BigInteger, BigInteger)

  /**
   * Build a new Bigfraction with numerator num and denominator denom.
   * 
   * Warning! Not yet stable.
   */
  public BigFraction(int num, int denom) {
    this.num = BigInteger.valueOf(num);
    this.denom = BigInteger.valueOf(denom);
  } // Fraction(int, int)

  /**
   * Build a new Bigfraction by parsing a string.
   *
   * Warning! Not yet implemented.
   */
  public BigFraction(String str) {
    String[] numbers = str.split("/");
    int numerator = Integer.parseInt(numbers[0]);
    int denominator = Integer.parseInt(numbers[1]);
    this.num = BigInteger.valueOf(numerator);
    this.denom = BigInteger.valueOf(denominator);
  } // BigFraction

  // +---------+------------------------------------------------------
  // | Methods |
  // +---------+

  /**
   * Express this Bigfraction as a double.
   */
  public double doubleValue() {
    return this.num.doubleValue() / this.denom.doubleValue();
  } // doubleValue()

  /**
   * Add the Bigfraction `addMe` to this Bigfraction.
   */
  public BigFraction add(BigFraction addMe) {
    BigInteger resultNumerator;
    BigInteger resultDenominator;

    // The denominator of the result is the
    // product of this object's denominator
    // and addMe's denominator
    resultDenominator = this.denom.multiply(addMe.denom);
    // The numerator is more complicated
    resultNumerator = (this.num.multiply(addMe.denom)).add(addMe.num.multiply(this.denom));

    // Return the computed value
    return new BigFraction(resultNumerator, resultDenominator);
  }// add(Fraction)

  /**
   * Get the denominator of this fraction.
   */
  public BigInteger denominator() {
    return this.denom;
  } // denominator()

  /**
   * Get the numerator of this fraction.
   */
  public BigInteger numerator() {
    return this.num;
  } // numerator()

  /**
   * Convert this Bigfraction to a string for ease of printing.
   */
  public String toString() {
    // Special case: It's zero
    if (this.num.equals(BigInteger.ZERO)) {
      return "0";
    } // if it's zero
    else {
      String[] StringArr = new String[3];
      StringArr[0] = String.valueOf(this.num.intValue());// converting Integer to string and assigning
      StringArr[1] = "/";
      StringArr[2] = String.valueOf(this.denom.intValue());// converting Integer to string and assigning
      String ReturnString = StringArr[0] + StringArr[1] + StringArr[2];
      return ReturnString;// converting into one string
    }
  }// toString

  public BigFraction multiplyfrac(BigFraction f1) {
    BigInteger ResultNumerator;
    BigInteger ResultDenominator;
    ResultNumerator = this.num.multiply(f1.num);
    ResultDenominator = this.denom.multiply(f1.denom);
    return new BigFraction(ResultNumerator, ResultDenominator);
  }// multiplying frations

  public BigFraction fractional() {
    BigInteger newNum = this.num.mod(this.denom);
    return new BigFraction(newNum, this.denom);
  }// fractional

  /*
   * this is a function that takes a Fraction and uses it to
   * divide the object on which it is called.
   * 
   * @pre: divider is a valid Fraction object, and it is called on
   * a Fraction object
   * 
   * @post: valid Fraction is returned
   */
  public BigFraction divide(BigFraction divider) {
    int num = this.num.intValue() * divider.denom.intValue();
    int denom = this.denom.intValue() * divider.num.intValue();
    return new BigFraction(num, denom);// returning a new Fraction from the int,int constructor
  }

  public BigFraction subtract(BigFraction subtracter) {
    BigInteger resultNum;
    BigInteger resultDen;

    resultDen = this.denom.multiply(subtracter.denom);// getting the denominator by multiplying both denoms
    resultNum = (this.num.multiply(subtracter.denom)).subtract(subtracter.num.multiply(this.denom));
    // getting num by multiplying both by each other's denominator then subtracting
    // each other
    return new BigFraction(resultNum, resultDen);
  }

  /*
   * simplifyFraction is a method that simplifies Bigfractions in java.
   * 
   * @pre: there is a valid Bigfraction object upon which simplifyFraction is
   * called
   * 
   * @post: the BigFraction is either left alone and simplifyFraction returned
   * false,
   * or was modified in place and is now it's simplest form.
   */
  public boolean simplifyFraction() {
    int Divider = 2;/*
                     * the divider will begin at 2, as anything divided by 1 is itself,
                     * and anything divided by 0 is undefined
                     */
    boolean modified = false;
    int maxDivider = 0;// this will save the greatest number that both the numerator and denom can be
                       // divided by
    for (int i = Divider; i <= this.denom.intValue(); i++) {
      if (this.num.intValue() % i == 0 && this.denom.intValue() % i == 0) {
        maxDivider = i;
      }
    }
    if (maxDivider != 0) {
      this.num = BigInteger.valueOf(this.num.intValue() / maxDivider);// simplifying the fraction
      this.denom = BigInteger.valueOf(this.denom.intValue() / maxDivider);// simplifying the fraction
      modified = true;
    } else {
      modified = false;// fraction cannot be simplified
    }
    return modified;
  }// simplifyFraction

  public static void main(String[] args) {
    BigFraction f1 = new BigFraction(5, 11);
    BigFraction f2 = new BigFraction(8, 13);
    BigFraction quot = f1.divide(f2);
    System.out.printf("%d/%d\n", quot.num.intValue(), quot.denom.intValue());
  }
}
