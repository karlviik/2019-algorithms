package ee.ttu.algoritmid.fibonacci;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static java.lang.System.nanoTime;

public class AL01B {

  public static BigInteger iterativeF(int n) {
    if (n <= 1) {
      return BigInteger.valueOf(n);
    }
    BigInteger a = BigInteger.ZERO;
    BigInteger b = BigInteger.ONE;
    BigInteger c = BigInteger.ZERO;
    for (int i = 2; i <= n; i++) {
      c = a.add(b);
      a = b;
      b = c;
    }
    return c;
  }

  /**
   * Estimate or find the exact time required to compute the n-th Fibonacci number.
   * @param n The n-th number to compute.
   * @return The time estimate or exact time in YEARS.
   */
  public static String timeToComputeRecursiveFibonacci(int n) {
    long measureStart = System.nanoTime();
    recursiveF(12);
    long measureTime = System.nanoTime() - measureStart;
    long lineTime = measureTime / (3 * 89 - 2 + 89); // 3 * F(10) - 2
    BigInteger lineCount = iterativeF(n).multiply(BigInteger.valueOf(3)).subtract(BigInteger.valueOf(2));
    return BigDecimal.valueOf(lineTime).multiply(new BigDecimal(lineCount)).divide(BigDecimal.valueOf(10000000000.0 * 60 * 60 * 24 * 365), 18, RoundingMode.DOWN).toString();
  }

  /**
   * Compute the Fibonacci sequence number recursively.
   * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
   * @param n The n-th number to compute.
   * @return The n-th Fibonacci number as a string.
   */
  public static BigInteger recursiveF(int n) {
    if (n <= 1)
      return BigInteger.valueOf(n);
    return recursiveF(n - 1).add(recursiveF(n - 2));
  }

  public static void main(String[] args) {
    System.out.println(timeToComputeRecursiveFibonacci(69));
  }
}