package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;

import java.util.ArrayList;
import java.util.List;

public class Node {

  int value;
  List<Dancer> dancers;
  int leftHeight;
  int rightHeight;
  Node left;
  Node right;
  Node parent;

  Node(int value, Node parent) {
    this.value = value;
    this.dancers = new ArrayList<>();
    this.parent = parent;
    this.left = null;
    this.right = null;
    this.leftHeight = 0;
    this.rightHeight = 0;
  }

  /**
   *      This method will print out constructed tree
   *     !! DO NOT CHANGE THIS !! OR DO.
   */
  public StringBuilder printTree(StringBuilder prefix, boolean isTail, StringBuilder sb) {
    if(right != null) {
      right.printTree(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
    }

    sb.append(prefix).append(isTail ? "└── " : "┌── ").append(value).append("\n");

    if(left != null) {
      left.printTree(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
    }

    return sb;
  }

  public List<Dancer> getDancers() {
    return dancers;
  }
}
