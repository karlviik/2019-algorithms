package ee.ttu.algoritmid.interestingstamps;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class InterestingStamps {

	public static List<Integer> findStamps(int sum, List<Integer> stampOptions) throws IllegalArgumentException {
		if (sum < 0 || stampOptions == null) {
			throw new IllegalArgumentException();
		}
		int[] subtaskis = new int[sum+1];
		int[] amountofmarks = new int[sum+1];
		int[] amountofsimplemarks = new int[sum+1];
		ArrayList<int[]> options;
		for (int i = 1; i <= sum; i++) {
			options = new ArrayList<>();
			for (int mark : stampOptions) {
				if (mark < i) {
					int[] option = new int[3];
					option[0] = mark; // value of the mark chosen
					int sub = i - mark;
					option[1] = amountofmarks[sub] + 1; // amount of marks in new thing
					int simple = amountofsimplemarks[sub];
					if (mark == 1 || mark % 10 == 0) {
						simple++;
					}
					option[2] = simple; // amount of simple marks in the new thing
					options.add(option);
				}
				else if (mark == i) {
					if (mark == 1 || mark % 10 == 0) {
						amountofsimplemarks[i] = 1;
					}
					options = new ArrayList<>();
					amountofmarks[i] = 1;
					subtaskis[i] = -1;
					break;
				}
			}
			if (options.size() > 0) {
				Optional<int[]> best = options.stream().min(Comparator.comparingInt((int[] x) -> x[1]).thenComparingInt(x -> x[2]));
				if (best.isPresent()) {
					int[] thing = best.get();
					amountofmarks[i] = thing[1];
					amountofsimplemarks[i] = thing[2];
					subtaskis[i] = i - thing[1];
				}
			}
		}
		if (subtaskis[sum] != 0) {
			ArrayList<Integer> answer = new ArrayList<>();
			int index = sum;
			while (true) {
				int subtask = sum - subtaskis[index];
				if (subtask == 0) {
					return answer;
				}
				answer.add(subtask);
				index = subtask;
			}
		}
		return null;
	}
}