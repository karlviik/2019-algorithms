package ee.ttu.algoritmid.dancers;
import ee.ttu.algoritmid.dancers.binarytree.BinaryTree;
import ee.ttu.algoritmid.dancers.binarytree.Node;

import java.util.ArrayList;
import java.util.List;

public class HW01 implements Dancers {

  public BinaryTree femaleTree = new BinaryTree();
  public BinaryTree maleTree = new BinaryTree();

  public HW01() {
  }


  @Override
  public DancingCouple findPartnerFor(Dancer dancer) throws IllegalArgumentException {
    if (dancer == null || dancer.getName() == null || dancer.getName().length() == 0 || dancer.getGender() == null || dancer.getHeight() <= 0) {
      throw new IllegalArgumentException();
    }
    Node potentialNode;
    DancingCouple couple;
    boolean fromFemale = false;
    if (dancer.getGender().equals(Dancer.Gender.MALE)) {
      fromFemale = true;
      potentialNode = femaleTree.findLessOrEqual(dancer.getHeight() - 5);
      if (potentialNode == null) {
        maleTree.add(dancer.getHeight(), dancer);
        return null;
      }
      couple = new DancingCoupleImpl(dancer, potentialNode.getDancers().remove(0));
    } else {
      potentialNode = maleTree.findMoreOrEqual(dancer.getHeight() + 5);
      if (potentialNode == null) {
        femaleTree.add(dancer.getHeight(), dancer);
        return null;
      }
      couple = new DancingCoupleImpl(potentialNode.getDancers().remove(0), dancer);

    }
    if (potentialNode.getDancers().isEmpty()) {
      if (fromFemale) {
        femaleTree.deleteNode(potentialNode);
      } else {
        maleTree.deleteNode(potentialNode);
      }
    }
    return couple;
  }

  @Override
  public List<Dancer> returnWaitingList() {
    List<Dancer> femaleDancers = femaleTree.getContentsInOrder();
    List<Dancer> maleDancers = maleTree.getContentsInOrder();
    int maleCounter = 0;
    int femaleCounter = 0;
    boolean femalesLeft = femaleDancers.size() != 0;
    boolean malesLeft = maleDancers.size() != 0;
    List<Dancer> combinedList = new ArrayList<>();
    while (malesLeft || femalesLeft) {
      if (femalesLeft && malesLeft) {
        if (femaleDancers.get(femaleCounter).getHeight() >= maleDancers.get(maleCounter).getHeight()) {
          combinedList.add(femaleDancers.get(femaleCounter));
          femaleCounter++;
          if (femaleCounter == femaleDancers.size()) {
            femalesLeft = false;
          }
        } else {
          combinedList.add(maleDancers.get(maleCounter));
          maleCounter++;
          if (maleCounter == maleDancers.size()) {
            malesLeft = false;
          }
        }
      }
      else if (femalesLeft) {
        combinedList.add(femaleDancers.get(femaleCounter));
        femaleCounter++;
        if (femaleCounter == femaleDancers.size()) {
          femalesLeft = false;
        }
      }
      else {
        combinedList.add(maleDancers.get(maleCounter));
        maleCounter++;
        if (maleCounter == maleDancers.size()) {
          malesLeft = false;
        }
      }
    }
    return combinedList;
  }
}