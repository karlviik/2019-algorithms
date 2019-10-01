package ee.ttu.algoritmid.popularity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Popularity {

  private int maxPlace = 0;
  private Map<ArrayList<Integer>, Integer> pointMap = new HashMap<>();

  public Popularity(int maxCoordinates) {
  }

  /**
   * @param x, y - coordinates
   */

  void addPoint(Integer x, Integer y) {
    ArrayList<Integer> thing = new ArrayList<>(Arrays.asList(x, y));
    Integer newValue = pointMap.getOrDefault(thing, 0) + 1;
    if (newValue > maxPlace) {
      maxPlace = newValue;
    }
    pointMap.put(thing, newValue);
  }

  /**
   * @param x, y - coordinates
   * @return the number of occurrennces of the point
   */

  int pointPopularity(Integer x,Integer y) {
    return pointMap.getOrDefault(new ArrayList<>(Arrays.asList(x, y)), 0);
  }



  /**
   * @return the number of occurrennces of the most popular point
   */

  int maxPopularity() {
    return maxPlace;
  }

}