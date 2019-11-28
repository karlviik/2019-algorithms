import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Solution {

	// Complete the gridlandMetro function below.
	static long gridlandMetro(int n, int m, int k, int[][] track) {
		long cells = (long) n * (long) m;
		HashMap<Integer, ArrayList<int[]>> lines = new HashMap<>();
		Set<Integer> rows = new TreeSet<>();
		for (int[] tracky : track) {
			rows.add(tracky[0]);
			int start = tracky[1];
			int end = tracky[2];
			if (!lines.containsKey(tracky[0])) {
				lines.put(tracky[0], new ArrayList<>(Collections.singletonList(new int[]{start, end})));
			}
			else {
				int[] containTrackyStart = null;
				int[] containTrackyEnd = null;
				boolean isTrackyInsideSomethingElseEntirely = false;
				ArrayList<int[]> containedByTracky = new ArrayList<>();
				for (int[] trackyInRow : lines.get(tracky[0])) {
					if (trackyInRow[0] <= start && end <= trackyInRow[1]) {
						isTrackyInsideSomethingElseEntirely = true;
						break;
					}
					if (start <= trackyInRow[0] && trackyInRow[1] <= end) {
						containedByTracky.add(trackyInRow);
					}
					else if (trackyInRow[0] <= start && start <= trackyInRow[1]) {
						containTrackyStart = trackyInRow;
					}
					else if (trackyInRow[0] <= end && end <= trackyInRow[1]) {
						containTrackyEnd = trackyInRow;
					}
				}
				if (isTrackyInsideSomethingElseEntirely) {
					continue;
				}
				ArrayList<int[]> deleteFromThis = lines.get(tracky[0]);
				for (int[] deleteThis : containedByTracky) {
					deleteFromThis.remove(deleteThis);
				}
				if (containTrackyEnd != null && containTrackyStart == null) {
					deleteFromThis.remove(containTrackyEnd);
					deleteFromThis.add(new int[]{tracky[1], containTrackyEnd[1]});
				}
				else if (containTrackyEnd == null && containTrackyStart != null) {
					deleteFromThis.remove(containTrackyStart);
					deleteFromThis.add(new int[]{containTrackyStart[0], tracky[2]});
				}
				else if (containTrackyEnd != null && containTrackyStart != null) {
					deleteFromThis.remove(containTrackyEnd);
					deleteFromThis.remove(containTrackyStart);
					deleteFromThis.add(new int[]{containTrackyStart[0], containTrackyEnd[1]});
				}
				else {
					deleteFromThis.add(new int[]{tracky[1], tracky[2]});
				}
			}

		}
		for (int row : rows) {
			for (int[] tracky : lines.get(row)) {
				cells -= (tracky[1] - tracky[0] + 1);
			}
		}
		return cells;
	}

	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
		String[] nmk = scanner.nextLine().split(" ");
		int n = Integer.parseInt(nmk[0]);
		int m = Integer.parseInt(nmk[1]);
		int k = Integer.parseInt(nmk[2]);
		int[][] track = new int[k][3];
		for (int i = 0; i < k; i++) {
			String[] trackRowItems = scanner.nextLine().split(" ");
			scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
			for (int j = 0; j < 3; j++) {
				int trackItem = Integer.parseInt(trackRowItems[j]);
				track[i][j] = trackItem;
			}
		}
		long result = gridlandMetro(n, m, k, track);
		bufferedWriter.write(String.valueOf(result));
		bufferedWriter.newLine();
		bufferedWriter.close();
		scanner.close();
	}
}
