package ee.ttu.algoritmid.fibonacci;

import java.math.BigInteger;

public class AL01B {

  /**
   * Estimate or find the exact time required to compute the n-th Fibonacci number.
   * @param n The n-th number to compute.
   * @return The time estimate or exact time in YEARS.
   */
  public String timeToComputeRecursiveFibonacci(int n) {
    // TODO
    return "";
  }

  /**
   * Compute the Fibonacci sequence number recursively.
   * (You need this in the timeToComputeRecursiveFibonacci(int n) function!)
   * @param n The n-th number to compute.
   * @return The n-th Fibonacci number as a string.
   */
  public BigInteger recursiveF(int n) {
    if (n <= 1)
      return BigInteger.valueOf(n);
    return recursiveF(n - 1).add(recursiveF(n - 2));
  }
}