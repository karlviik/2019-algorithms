import java.io.*;
import java.util.*;

public class Solution {

  /*
   * Complete the runningMedian function below.
   */
  static double[] runningMedian(int[] a) {
    /*
     * Write your code here.
     */
    Queue<Integer> smaller = new PriorityQueue<>(Comparator.reverseOrder());
    Queue<Integer> bigger = new PriorityQueue<>();

    List<Double> medians = new ArrayList<>();


    for (int i = 0; i < a.length; i++) {
      if (smaller.peek() == null || smaller.peek() <= a[i]) {
        bigger.add(a[i]);
      } else {
        smaller.add(a[i]);
      }

      if ((Math.abs(bigger.size() - smaller.size())) > 1) {
        if (bigger.size() > smaller.size()) {
          smaller.add(bigger.remove());
        } else {
          bigger.add(smaller.remove());
        }
      }

      if(bigger.size() == smaller.size()) {
        medians.add(((smaller.peek() + bigger.peek()) * 10.0 / 2) / 10.0);
      } else if (bigger.size() > smaller.size()) {
        medians.add((double) bigger.peek());
      } else {
        medians.add((double) smaller.peek());
      }
    }
    return convertIntegers(medians);
  }

  public static double[] convertIntegers(List<Double> integers)
  {
    double[] ret = new double[integers.size()];
    for (int i=0; i < ret.length; i++)
    {
      ret[i] = integers.get(i);
    }
    return ret;
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    int aCount = Integer.parseInt(scanner.nextLine().trim());

    int[] a = new int[aCount];

    for (int aItr = 0; aItr < aCount; aItr++) {
      int aItem = Integer.parseInt(scanner.nextLine().trim());
      a[aItr] = aItem;
    }

    double[] result = runningMedian(a);

    for (int resultItr = 0; resultItr < result.length; resultItr++) {
      bufferedWriter.write(String.valueOf(result[resultItr]));

      if (resultItr != result.length - 1) {
        bufferedWriter.write("\n");
      }
    }

    bufferedWriter.newLine();

    bufferedWriter.close();
  }
}
