package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;

import java.util.ArrayList;
import java.util.List;

public class Node {

  int value;
  List<Dancer> dancers;
  Node left;
  Node right;
  Node parent;

  public Node(int value, Node parent) {
    this.value = value;
    this.dancers = new ArrayList<>();
    this.parent = parent;
    this.left = null;
    this.right = null;
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

  public Node getLeft() {
    return left;
  }

  public void setLeft(Node left) {
    this.left = left;
  }

  public Node getRight() {
    return right;
  }

  public void setRight(Node right) {
    this.right = right;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public void addDancer(Dancer dancer) {
    dancers.add(dancer);
  }

  public int getValue() {
    return value;
  }

  public List<Dancer> getDancers() {
    return dancers;
  }
}
