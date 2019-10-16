package ee.ttu.algoritmid.scoreboard;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ScoreBoard {

    private TreeSet<Participant> pq = new TreeSet<>(Comparator.comparing(Participant::getTime, Comparator.reverseOrder()).thenComparing(Participant::getId));
    /**
     * Adds a participant'ssss time to the checkpoint scoreboard
     */
    public void add(Participant participant) {
        pq.add(participant);
    }

    /**
     * Returns top n number of participants in the checkpoint to be displayed on the scoreboard
     * This method will be queried by the tests every time a new participant is added
     */
    public List<Participant> get(int n) {
        return StreamSupport.stream(
            Spliterators.spliteratorUnknownSize(pq.iterator(), Spliterator.ORDERED),
            false).limit(n).collect(Collectors.toList());
    }

//    public static void main(String[] args) {
//        ScoreBoard sb = new ScoreBoard();
//        sb.add(new Participant(1, "", 0));
//        sb.add(new Participant(3, "aefa", 2));
//        sb.add(new Participant(2, "aefa", 1));
//        System.out.println(sb.get(3));
//        System.out.println(sb.get(2));
//        sb.pq.iterator().forEachRemaining(System.out::println);
//        System.out.println(sb.pq.stream().limit(3).collect(Collectors.toList()));
//
//
//    }
}