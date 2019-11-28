import java.io.*;
import java.util.*;

public class Solution {

  // Complete the activityNotifications function below.
  static int activityNotifications(int[] expenditure, int d) {
    PriorityQueue<Integer> smaller = new PriorityQueue<>(Comparator.reverseOrder());
    PriorityQueue<Integer> bigger = new PriorityQueue<>();
    ArrayDeque<Integer> normalQueue = new ArrayDeque<>();
    int counter = 0;
    int median2;
    int thing;
    for (int i = 0; i < expenditure.length; i++) {
      thing = expenditure[i];
      if (i >= d) {
        if (bigger.size() == smaller.size()) {
          median2 = smaller.peek() + bigger.peek();
        }
        else if (bigger.size() > smaller.size()) {
          median2 = bigger.peek() * 2;
        }
        else {
          median2 = smaller.peek() * 2;
        }
        if (median2 <= thing) {
          counter++;
        }
        Integer toBeRemoved = normalQueue.remove();
        if (!bigger.remove(toBeRemoved)) {
          smaller.remove(toBeRemoved);
        }
      }

      if (smaller.peek() == null || smaller.peek() <= thing) {
        bigger.add(thing);
      } else {
        smaller.add(thing);
      }

      if ((Math.abs(bigger.size() - smaller.size())) > 1) {
        if (bigger.size() > smaller.size()) {
          smaller.add(bigger.remove());
        } else {
          bigger.add(smaller.remove());
        }
      }
      normalQueue.add(thing);
    }
    return counter;
  }

  private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) throws IOException {
    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

    String[] nd = scanner.nextLine().split(" ");

    int n = Integer.parseInt(nd[0]);

    int d = Integer.parseInt(nd[1]);

    int[] expenditure = new int[n];

    String[] expenditureItems = scanner.nextLine().split(" ");
    scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");

    for (int i = 0; i < n; i++) {
      int expenditureItem = Integer.parseInt(expenditureItems[i]);
      expenditure[i] = expenditureItem;
    }

    int result = activityNotifications(expenditure, d);

    bufferedWriter.write(String.valueOf(result));
    bufferedWriter.newLine();

    bufferedWriter.close();

    scanner.close();
  }
}