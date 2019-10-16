package ee.ttu.algoritmid.scoreboard;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ScoreBoard {

    private TreeSet<Participant> ts = new TreeSet<>(Comparator.comparing(Participant::getTime).thenComparing(Participant::getId));
    /**
     * Adds a participant'ssss time to the checkpoint scoreboard
     */
    public void add(Participant participant) {
        ts.add(participant);
    }

    /**
     * Returns top n number of participants in the checkpoint to be displayed on the scoreboard
     * This method will be queried by the tests every time a new participant is added
     */
    public List<Participant> get(int n) {
        return ts.stream().limit(n).collect(Collectors.toList());
    }
}