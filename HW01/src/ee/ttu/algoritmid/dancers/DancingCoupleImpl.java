package ee.ttu.algoritmid.dancers;

public class DancingCoupleImpl implements DancingCouple {

  private Dancer maleDancer;
  private Dancer femaleDancer;

  public DancingCoupleImpl(Dancer maleDancer, Dancer femaleDancer) {
    this.maleDancer = maleDancer;
    this.femaleDancer = femaleDancer;
  }

  @Override
  public Dancer getMaleDancer() {
    return maleDancer;
  }

  @Override
  public Dancer getFemaleDancer() {
    return femaleDancer;
  }
}
