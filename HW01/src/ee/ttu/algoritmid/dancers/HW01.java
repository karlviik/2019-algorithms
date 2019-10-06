package ee.ttu.algoritmid.dancers;
import ee.ttu.algoritmid.dancers.binarytree.BinaryTree;
import ee.ttu.algoritmid.dancers.binarytree.Node;

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
      couple = new DancingCoupleImpl(dancer, potentialNode.getDancers().get(0));
    } else {
      potentialNode = maleTree.findMoreOrEqual(dancer.getHeight() + 5);
      if (potentialNode == null) {
        femaleTree.add(dancer.getHeight(), dancer);
        return null;
      }
      couple = new DancingCoupleImpl(potentialNode.getDancers().get(0), dancer);
    }
    potentialNode.getDancers().remove(0);
    if (potentialNode.getDancers().size() == 0) {
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
    return null;
  }
}