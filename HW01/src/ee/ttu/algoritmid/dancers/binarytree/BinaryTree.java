package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;
import ee.ttu.algoritmid.dancers.DancerImpl;
import ee.ttu.algoritmid.dancers.DancingCouple;
import ee.ttu.algoritmid.dancers.HW01;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static ee.ttu.algoritmid.dancers.Dancer.Gender.FEMALE;
import static ee.ttu.algoritmid.dancers.Dancer.Gender.MALE;

public class BinaryTree {
  private Node root;
  private boolean changeHeight;
  private Node balancePoint;

  public BinaryTree(){
    this.root = null;
  }

  /**
   * Recursively add new dancer to a node, if node doesn't exist, create it.
   * @param currentNode - current node being looked at.
   * @param parentNode - parent of the current node.
   * @param value - dancer height.
   * @param dancer - dancer being added.
   */
  private Node addDancer(Node currentNode, Node parentNode, int value, Dancer dancer){
    if (currentNode == null){
      Node newNode = new Node(value, parentNode);
      newNode.dancers.add(dancer);
      changeHeight = true;
      return newNode;
    }

    else if (value < currentNode.value){
      currentNode.left = addDancer(currentNode.left, currentNode, value, dancer);
      if (changeHeight) {
        currentNode.leftHeight++;
      }
    }

    else if (value > currentNode.value){
      currentNode.right = addDancer(currentNode.right, currentNode, value, dancer);
      if (changeHeight) {
        currentNode.rightHeight++;
      }
    }

    else {
      currentNode.dancers.add(dancer);
    }

    if (currentNode.rightHeight == currentNode.leftHeight) {
      changeHeight = false;
    }

    else if (Math.abs(currentNode.rightHeight - currentNode.leftHeight) > 1 && balancePoint == null) {
      balancePoint = currentNode;
    }
    if (balancePoint != null && balancePoint.value == 155) {
      System.out.println(balancePoint.leftHeight);
      System.out.println(balancePoint.rightHeight);
    }
    return currentNode;
  }

  public void add(int value, Dancer dancer){
    changeHeight = false;
    balancePoint = null;
    System.out.println(root == null ? 0 : "root rightheight " + root.rightHeight);
    root = addDancer(root, null, value, dancer);
    if (root != null) {
      if (root.right != null) {
        System.out.println("root right height after insertion " + root.right.rightHeight);
      }
    }
    System.out.println("root rightheight " + root.rightHeight);
    balance();
    System.out.println("root rightheight after balance " + root.rightHeight);
  }

  private void childCallsForHeightDecrement(Node child) {
    if (child != null && child.parent != null && child.parent.value == 155) {
      System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
      System.out.println(balancePoint);
    }
    if (child.parent != null) {
      if (child.parent.left == child) {
        child.parent.leftHeight--;
        if (child.parent.leftHeight >= child.parent.rightHeight) {
          childCallsForHeightDecrement(child.parent);
        }
      } else {
        child.parent.rightHeight--;
        if (child.parent.rightHeight >= child.parent.leftHeight) {
          childCallsForHeightDecrement(child.parent);
        }
      }
      if (Math.abs(child.parent.leftHeight - child.parent.rightHeight) > 1 && balancePoint == null) {
        balancePoint = child.parent;
      }
      if (child != null && child.parent != null && child.parent.value == 155 && balancePoint != null) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(balancePoint.value);
      }
    }
  }

  public void deleteNode(Node nodeToDelete) {
    if (nodeToDelete.left == null || nodeToDelete.right == null) {
      childCallsForHeightDecrement(nodeToDelete);
      deleteOneChildOrChildlessNode(nodeToDelete);
      if (root == nodeToDelete) {
        root = null;
      }
    } else {
      Node replacementNode = findAndDeleteSmallest(nodeToDelete.right);
      if (changeHeight) {
        nodeToDelete.rightHeight--;
        if (Math.abs(nodeToDelete.leftHeight - nodeToDelete.rightHeight) > 1 && balancePoint == null) {
          balancePoint = replacementNode;
        }
        if (nodeToDelete.rightHeight == nodeToDelete.leftHeight) {
          childCallsForHeightDecrement(nodeToDelete);
        } else {
          changeHeight = false;
        }
      }
      replacementNode.parent = nodeToDelete.parent;
      replacementNode.left = nodeToDelete.left;
      replacementNode.right = nodeToDelete.right;
      replacementNode.leftHeight = nodeToDelete.leftHeight;
      replacementNode.rightHeight = nodeToDelete.rightHeight;
      if (nodeToDelete.right != null) {
        nodeToDelete.right.parent = replacementNode;
      }
      if (nodeToDelete.left != null) {
        nodeToDelete.left.parent = replacementNode;
      }
      if (nodeToDelete.parent != null) {
        if (nodeToDelete.parent.left == nodeToDelete) {
          nodeToDelete.parent.left = replacementNode;
        }
        else {
          nodeToDelete.parent.right = replacementNode;
        }
      }
      else {
        root = replacementNode;
      }
    }
    balance();
  }

  private void deleteOneChildOrChildlessNode(Node nodeToDelete) {
    if (nodeToDelete.parent != null) {
      if (nodeToDelete.parent.left == nodeToDelete) {
        if (nodeToDelete.left != null) {
          nodeToDelete.parent.left = nodeToDelete.left;
          nodeToDelete.left.parent = nodeToDelete.parent;
        } else if (nodeToDelete.right != null) {
          nodeToDelete.parent.left = nodeToDelete.right;
          nodeToDelete.right.parent = nodeToDelete.parent;
        } else {
          nodeToDelete.parent.left = null;
        }
//        nodeToDelete.parent.leftHeight--;
      } else {
        if (nodeToDelete.left != null) {
          nodeToDelete.parent.right = nodeToDelete.left;
          nodeToDelete.left.parent = nodeToDelete.parent;
        } else if (nodeToDelete.right != null) {
          nodeToDelete.parent.right = nodeToDelete.right;
          nodeToDelete.right.parent = nodeToDelete.parent;
        } else {
          nodeToDelete.parent.right = null;
        }
//        nodeToDelete.parent.rightHeight--;
      }
    }
    else {
      if (nodeToDelete.left != null) {
        root = nodeToDelete.left;
      }
      else {
        root = nodeToDelete.right;
      }
    }
    changeHeight = true;
  }

  private Node findAndDeleteSmallest(Node currentNode) {
    if (currentNode.left == null) {
      deleteOneChildOrChildlessNode(currentNode);
      return currentNode;
    } else {
      Node smallest = findAndDeleteSmallest(currentNode.left);
      if (changeHeight) {
        currentNode.leftHeight--;
      }
      if (currentNode.rightHeight > currentNode.leftHeight) {
        changeHeight = false;
        if (currentNode.rightHeight - currentNode.leftHeight == 2 && balancePoint == null) {
          balancePoint = currentNode;
        }
      }
      return smallest;
    }
  }

  public String toString(){
    return root == null ? "" : root.printTree(new StringBuilder(), true, new StringBuilder()).toString();
  }

  public Node findLessOrEqual(int value) {
    return findLessOrEqualRecursive(root, value);
  }

  private Node findLessOrEqualRecursive(Node node, int value) {
    if (node == null) {
      return null;
    }
    if (node.value == value) {
      return node;
    }
    if (node.value > value) {
      if (node.left != null) {
        return findLessOrEqualRecursive(node.left, value);
      }
      return null;
    }
    Node rightNode = findLessOrEqualRecursive(node.right, value);
    if (rightNode == null) {
      return node;
    }
    return rightNode;
  }

  public Node findMoreOrEqual(int value) {
    return findMoreOrEqualRecursive(root, value);
  }

  private Node findMoreOrEqualRecursive(Node node, int value) {
    if (node == null) {
      return null;
    }
    if (node.value == value) {
      return node;
    }
    if (node.value < value) {
      if (node.right != null) {
        return findMoreOrEqualRecursive(node.right, value);
      }
      return null;
    }
    Node leftNode = findMoreOrEqualRecursive(node.left, value);
    if (leftNode == null) {
      return node;
    }
    return leftNode;
  }

  private int getBalanceValue(Node node) {
    return node.leftHeight - node.rightHeight;
  }

  // balance, rightRotate and leftRotate heavily inspired from https://www.geeksforgeeks.org/avl-tree-set-1-insertion/
  private void balance() {
//    System.out.println("I did balance");
    if (balancePoint != null) {
//      System.out.println("I ACTUALLY did balance with node value " + balancePoint.value);
      Node newRoot;
      Node parent = balancePoint.parent;

      int balance = getBalanceValue(balancePoint);

      // Left Left Case
      if (balance > 1 && getBalanceValue(balancePoint.left) >= 0) {
//        System.out.println("LL");
        newRoot = rightRotate(balancePoint);
      }

      // Right Right Case
      else if (balance < -1 && getBalanceValue(balancePoint.right) <= 0) {
//        System.out.println("RR");
        newRoot = leftRotate(balancePoint);
//        System.out.println(root.left);

      }

      // Left Right Case
      else if (balance > 1 && getBalanceValue(balancePoint.left) < 0) {
        balancePoint.left = leftRotate(balancePoint.left);
        newRoot = rightRotate(balancePoint);
      }

      // Right Left Case
//      else {
      else if (balance < -1 && getBalanceValue(balancePoint.right) > 0) {
        balancePoint.right = rightRotate(balancePoint.right);
        newRoot = leftRotate(balancePoint);
      }
      else {
        System.out.println("I reached something I shouldn't have reached");
        return;
      }

      if (parent == null) {
        root = newRoot;
      }
      else {
        int prevheight;
        int newheight;
        if (parent.left == balancePoint) {
          parent.left = newRoot;
          prevheight = parent.leftHeight;
          parent.leftHeight = 1 + Math.max(newRoot.rightHeight, newRoot.leftHeight);
          newheight = parent.leftHeight;
        } else {
          parent.right = newRoot;
          prevheight = parent.rightHeight;
          parent.rightHeight = 1 + Math.max(newRoot.rightHeight, newRoot.leftHeight);
          newheight = parent.rightHeight;
        }
        if (prevheight - newheight == 1) {
          childCallsForHeightDecrement(parent);
        }
      }
      if (balancePoint.value == 160) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println("root right rightheight after balance " + root.right.rightHeight);
      }
      balancePoint = null;
    }
  }

  private Node rightRotate(Node rotationRoot) {
    Node rotationLeft = rotationRoot.left;
    Node rotationLeftRight = rotationLeft.right;
    rotationRoot.left = rotationLeftRight;
    rotationLeft.right = rotationRoot;
    rotationRoot.leftHeight = (rotationLeftRight == null ? 0 : 1 + Math.max(rotationLeftRight.rightHeight, rotationLeftRight.leftHeight));
    rotationLeft.rightHeight = 1 + Math.max(rotationRoot.rightHeight, rotationRoot.leftHeight);

    Node rotationRootParent = rotationRoot.parent;
    rotationRoot.parent = rotationLeft;
    rotationLeft.parent = rotationRootParent;
    if (rotationLeftRight != null) {
      rotationLeftRight.parent = rotationRoot;
    }
    return rotationLeft;
  }

  private Node leftRotate(Node rotationRoot) {
    Node rotationRight = rotationRoot.right;
    Node rotationRightLeft = rotationRight.left;
    rotationRoot.right = rotationRightLeft;
    rotationRight.left = rotationRoot;
    rotationRoot.rightHeight = (rotationRightLeft == null ? 0 : 1 + Math.max(rotationRightLeft.rightHeight, rotationRightLeft.leftHeight));
    rotationRight.leftHeight = 1 + Math.max(rotationRoot.rightHeight, rotationRoot.leftHeight);


    Node rotationRootParent = rotationRoot.parent;
    rotationRoot.parent = rotationRight;
    rotationRight.parent = rotationRootParent;
    if (rotationRightLeft != null) {
      rotationRightLeft.parent = rotationRoot;
    }
    return rotationRight;
  }

  public List<Dancer> getContentsInOrder() {
    return inOrderContents(root, new ArrayList<>());
  }

  public List<Dancer> inOrderContents(Node node, List<Dancer> list) {
    if (node == null) {
      return list;
    }
    inOrderContents(node.left, list);
    list.addAll(node.dancers);
    System.out.println(node.dancers);
    inOrderContents(node.right, list);
    return list;
  }
//  /**
//   * Traverses tree in pre-order using recursion
//   * Prints out visited node
//   * @param root - node where recursion starts.
//   *               root != this.root
//   */
//  private void preOrderRecursion(Node root){
//    if (root != null){
//      System.out.print(root.value + " ");
//      preOrderRecursion(root.left);
//      preOrderRecursion(root.right);
//    }
//  }
//
//  /**
//   * Traverses tree in pre-order using loop
//   * Prints out visited node
//   * @param node to be traversed
//   */
//  private void preOrderLoop(Node node){
//
//    // base case
//    if (node == null) {
//      return;
//    }
//
//    Stack<Node> nodeStack = new Stack<Node>();
//    nodeStack.push(root);
//
//    // steps
//    while (!nodeStack.empty()) {
//
//      Node newNode = nodeStack.peek();
//      System.out.print(newNode.value + " ");
//      nodeStack.pop();
//
//      if (newNode.right != null) {
//        nodeStack.push(newNode.right);
//      }
//      if (newNode.left != null) {
//        nodeStack.push(newNode.left);
//      }
//    }
//  }
//
//  /**
//   * Traverses tree as post order
//   * @param root
//   */
//  private void postOder(Node root){
//    //TODO implement me
//  }
//
//  /**
//   * Traverses tree as in-order
//   * @param root - node to be traversed
//   */
//  private void inOrder(Node root){
//    //TODO implement me
//  }
//
//  /**
//   * Find node
//   * @param node to be searched
//   */
//  private void searchNode(Node node){
//    //TODO implement me
//  }
//
//  /**
//   * Simple method to create binary tree
//   */
//  private BinaryTree createBinaryTree(){
//    BinaryTree binaryTree = new BinaryTree();
//
//    binaryTree.add(6);
//    binaryTree.add(4);
//    binaryTree.add(8);
//    binaryTree.add(3);
//    binaryTree.add(5);
//    binaryTree.add(7);
//    binaryTree.add(9);
//
//    return binaryTree;

//  }


//  private boolean contains(int value){
//    return containsNode(root, value);
//  }


  public static void testMaleTreeEndToEndPublic() {
    List<Dancer> requests = new ArrayList<>();
    List<Integer> responds = new ArrayList<>();

    requests.add(new DancerImpl("M", MALE, 150));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 130));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 135));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 149));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 200));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 170));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 160));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 133));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 125));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 190));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 140));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 195));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 148));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 210));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 138));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 205));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 165));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 163));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 168));
    responds.add(null);


    requests.add(new DancerImpl("F", FEMALE, 145));
    responds.add(150);

    requests.add(new DancerImpl("F", FEMALE, 134));
    responds.add(140);

    requests.add(new DancerImpl("F", FEMALE, 159));
    responds.add(165);

    requests.add(new DancerImpl("F", FEMALE, 140));
    responds.add(148);

    requests.add(new DancerImpl("F", FEMALE, 156));
    responds.add(163);


    requests.add(new DancerImpl("M", MALE, 169));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 139));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 180));
    responds.add(null);

    requests.add(new DancerImpl("M", MALE, 134));
    responds.add(null);


    requests.add(new DancerImpl("F", FEMALE, 164));
    responds.add(169);

    requests.add(new DancerImpl("F", FEMALE, 134));
    responds.add(139);

    requests.add(new DancerImpl("F", FEMALE, 129));
    responds.add(134);

    requests.add(new DancerImpl("F", FEMALE, 175));
    responds.add(180);


    testTreeEndToEnd(requests, responds);
  }

  public static void testFemaleTreeEndToEndPublic() {
    List<Dancer> requests = new ArrayList<>();
    List<Integer> responds = new ArrayList<>();

    requests.add(new DancerImpl("F", FEMALE, 110));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 90));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 95));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 109));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 160));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 130));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 120));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 93));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 85));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 150));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 100));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 155));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 108));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 170));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 98));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 165));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 125));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 123));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 128));
    responds.add(null);


    requests.add(new DancerImpl("M", MALE, 115));
    responds.add(110);

    requests.add(new DancerImpl("M", MALE, 107));
    responds.add(100);

    requests.add(new DancerImpl("M", MALE, 132));
    responds.add(125);

    requests.add(new DancerImpl("M", MALE, 113));
    responds.add(108);

    requests.add(new DancerImpl("M", MALE, 130));
    responds.add(123);


    requests.add(new DancerImpl("F", FEMALE, 129));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 99));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 140));
    responds.add(null);

    requests.add(new DancerImpl("F", FEMALE, 94));
    responds.add(null);


    requests.add(new DancerImpl("M", MALE, 134));
    responds.add(129);

    requests.add(new DancerImpl("M", MALE, 106));
    responds.add(99);

    requests.add(new DancerImpl("M", MALE, 99));
    responds.add(94);

    requests.add(new DancerImpl("M", MALE, 147));
    responds.add(140);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(170);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(165);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(160);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(155);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(150);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(130);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(128);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(120);

    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(109);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(98);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(95);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(93);
    requests.add(new DancerImpl("M", MALE, 9999));
    responds.add(90);










    testTreeEndToEnd(requests, responds);
  }

  private static void testTreeEndToEnd(List<Dancer> requests, List<Integer> responds) {
    HW01 solution = new HW01();

    for (int i = 0; i < requests.size(); i++) {
      testRequestResponse(solution, requests.get(i), responds.get(i));
      System.out.println(solution.femaleTree.toString());
    }
    System.out.println(solution.femaleTree.toString());
    BinaryTree femaleTree = solution.femaleTree;
    if (femaleTree.root != null) {
      System.out.println(solution.femaleTree.root.rightHeight);
      System.out.println(solution.femaleTree.root.leftHeight);

    }
    System.out.println(solution.returnWaitingList());
    System.out.println("female tree root is null? " + (femaleTree.root == null));
  }


  private static void testRequestResponse(HW01 solution, Dancer dancer, Integer expectedPartnerHeight) {

    DancingCouple couple = solution.findPartnerFor(dancer);

    if (couple == null) {
      if (expectedPartnerHeight != null) {
        System.out.println("Partner wasn't found, but should have");
      }
    } else {
      if (expectedPartnerHeight == null) {
        System.out.println("Partner was found, but shouldn't have");
      } else {
        Dancer partner = dancer.getGender() == MALE ? couple.getFemaleDancer() : couple.getMaleDancer();

        if (partner.getHeight() != expectedPartnerHeight) {
          System.out.println("Partner of wrong height found");
        }
      }
    }
  }
  public static void main(String[] args) {
//    testMaleTreeEndToEndPublic();
    testFemaleTreeEndToEndPublic();


//    System.out.println(tree.contains(7));
//    System.out.println(tree.contains(11));
//
//    tree.preOrderRecursion(tree.root);
//    System.out.println();
//    tree.preOrderLoop(tree.root);

  }
}
