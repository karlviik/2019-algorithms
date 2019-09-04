package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01A {

  /**
   * Compute the Fibonacci sequence number.
   * @param n The number of the sequence to compute.
   * @return The n-th number in Fibonacci series.
   */
  public String iterativeF(int n) {
    if (n < 0) {
      return "";
    }
    BigInteger a = BigInteger.ZERO;
    BigInteger b = BigInteger.ONE;
    BigInteger c = BigInteger.ZERO;
    for (int i = 1; i <= n; i++) {
      c = a.add(b);
      a = b;
      b = c;
    }
    return String.valueOf(c);
  }
}