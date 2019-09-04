package ee.ttu.algoritmid.fibonacci;

public class AL01A {

  /**
   * Compute the Fibonacci sequence number.
   * @param n The number of the sequence to compute.
   * @return The n-th number in Fibonacci series.
   */
  public String iterativeF(int n) {
    int a = 0;
    int b = 1;
    int c = 0;
    for (int i = 1; i <= n; i++) {
      c = a + b;
      a = b;
      b = c;
    }
    return String.valueOf(c);
  }
}