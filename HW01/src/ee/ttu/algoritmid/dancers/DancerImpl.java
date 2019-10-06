package ee.ttu.algoritmid.dancers;

import javax.print.DocFlavor;

public class DancerImpl implements Dancer {

  private String name;
  private Gender gender;
  private int height;

  public DancerImpl(String name, Gender gender, int height) throws IllegalArgumentException {
    if (name == null || name.length() == 0 || gender == null || height <= 0) {
      throw new IllegalArgumentException();
    }
    this.name = name;
    this.gender = gender;
    this.height = height;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Gender getGender() {
    return gender;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public String toString() {
    return height + " " + gender;
  }
}
