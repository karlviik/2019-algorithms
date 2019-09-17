package ee.ttu.algoritmid.guessinggame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuessingGame {

  Oracle oracle;

  public GuessingGame(Oracle oracle) {
    this.oracle = oracle;
  }

  /**
   * @param cityArray - All the possible cities.
   * @return the name of the city.
   */
  public String play(City[] cityArray) {
    ArrayList<City> sortedCities = List.of(cityArray).stream().sorted(Comparator.comparing(City::getPopulation)).collect(Collectors.toCollection(ArrayList::new));
    int questionThis = sortedCities.size() / 2;
    int diff = questionThis;
    while (true) {
      String answer = oracle.isIt(sortedCities.get(questionThis));
      diff /= 2;
      if (diff == 0) {
        diff = 1;
      }
      if (answer.equals("higher population")) {
        questionThis += diff;
      } else if (answer.equals("lower population")) {
        questionThis -= diff;
      } else {
        return sortedCities.get(questionThis).getName();
      }
    }
  }
}