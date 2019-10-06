package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;
import ee.ttu.algoritmid.dancers.DancerImpl;
import ee.ttu.algoritmid.dancers.DancingCouple;
import ee.ttu.algoritmid.dancers.HW01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ee.ttu.algoritmid.dancers.Dancer.Gender.FEMALE;
import static ee.ttu.algoritmid.dancers.Dancer.Gender.MALE;

public class BinaryTree {
  private Node root;
  private Node balancePoint;
  private Node newNode;

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
      newNode = new Node(value, parentNode);
      newNode.dancers.add(dancer);
      return newNode;
    }

    else if (value < currentNode.value){
      currentNode.left = addDancer(currentNode.left, currentNode, value, dancer);
    }
    else if (value > currentNode.value){
      currentNode.right = addDancer(currentNode.right, currentNode, value, dancer);
    }
    else {
      currentNode.dancers.add(dancer);
    }
    return currentNode;
  }

  public void add(int value, Dancer dancer){
    balancePoint = null;
    newNode = null;
    root = addDancer(root, null, value, dancer);
    updateHeights(newNode);
    newNode = null;
    balance();
  }

  private void updateHeights(Node node) {
    if (node == null) {
      return;
    }
    node.leftHeight = node.left == null ? 0 : 1 + Math.max(node.left.leftHeight, node.left.rightHeight);
    node.rightHeight = node.right == null ? 0 : 1 + Math.max(node.right.leftHeight, node.right.rightHeight);
    if (Math.abs(node.leftHeight - node.rightHeight) > 1 && balancePoint == null) {
      balancePoint = node;
    }
    updateHeights(node.parent);
  }

  public void deleteNode(Node nodeToDelete) {
    if (nodeToDelete.left == null || nodeToDelete.right == null) {
      deleteOneChildOrChildlessNode(nodeToDelete);
      updateHeights(nodeToDelete.parent);
    } else {
      Node replacementNode = findAndDeleteSmallest(nodeToDelete.right);
      if (balancePoint == nodeToDelete) {
        balancePoint = replacementNode;
      }

      replacementNode.parent = nodeToDelete.parent;
      replacementNode.left = nodeToDelete.left;
      replacementNode.right = nodeToDelete.right;
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
      updateHeights(replacementNode);
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
      }
    }
    else {
      if (nodeToDelete.left != null) {
        root = nodeToDelete.left;
        nodeToDelete.left.parent = null;
      }
      else if (nodeToDelete.right != null) {
        root = nodeToDelete.right;
        nodeToDelete.right.parent = null;
      } else {
        root = null;
      }
    }
  }

  private Node findAndDeleteSmallest(Node currentNode) {
    if (currentNode.left == null) {
      deleteOneChildOrChildlessNode(currentNode);
      updateHeights(currentNode.parent);
      return currentNode;
    } else {
      return findAndDeleteSmallest(currentNode.left);
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
        newRoot = rightRotate(balancePoint);
      }
      // Right Right Case
      else if (balance < -1 && getBalanceValue(balancePoint.right) <= 0) {
        newRoot = leftRotate(balancePoint);
      }
      // Left Right Case
      else if (balance > 1 && getBalanceValue(balancePoint.left) < 0) {
        balancePoint.left = leftRotate(balancePoint.left);
        newRoot = rightRotate(balancePoint);
      }
      // Right Left Case
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
      updateHeights(parent);
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
    if (rotationRootParent != null) {
      if (rotationRootParent.left == rotationRoot) {
        rotationRootParent.left = rotationLeft;
      }
      else {
        rotationRootParent.right = rotationLeft;
      }
    }
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
    if (rotationRootParent != null) {
      if (rotationRootParent.left == rotationRoot) {
        rotationRootParent.left = rotationRight;
      }
      else {
        rotationRootParent.right = rotationRight;
      }
    }
    if (rotationRightLeft != null) {
      rotationRightLeft.parent = rotationRoot;
    }
    return rotationRight;
  }

  public List<Dancer> getContentsInOrder() {
    return inOrderContents(root, new ArrayList<>());
  }

  private List<Dancer> inOrderContents(Node node, List<Dancer> list) {
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

//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(155);
//
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(150);
//
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(130);
//
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(128);
//
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(120);
//
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(109);
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(98);
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(95);
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(93);
//    requests.add(new DancerImpl("M", MALE, 9999));
//    responds.add(90);










    testTreeEndToEnd(requests, responds);
  }

  private static void testTreeEndToEnd(List<Dancer> requests, List<Integer> responds) {
    HW01 solution = new HW01();

    for (int i = 0; i < requests.size(); i++) {
      testRequestResponse(solution, requests.get(i), responds.get(i));
//      System.out.println(solution.femaleTree.toString());
//      System.out.println(solution.maleTree.toString());
//      System.out.println("I asked for partner to " + requests.get(i));
//      System.out.println("I expected " + responds.get(i));
      System.out.println(solution.returnWaitingList());
      System.out.println("-------------------------------------------------------------------------");
    }


  }


  public static void testCustom() throws Exception {
      List<Dancer> requests = new ArrayList<>();
      List<Integer> responds = new ArrayList<>();
      Random RANDOM = new Random();
      HW01 solution = new HW01();
      for (int i = 0; i < 10000; i++) {
          Dancer dancer = new DancerImpl("Dab", RANDOM.nextBoolean() ? MALE : FEMALE, 1 + RANDOM.nextInt(100000));
          requests.add(dancer);
          DancingCouple couple = solution.findPartnerFor(dancer);
          responds.add(couple == null ? null : couple.getFemaleDancer() == dancer ? couple.getMaleDancer().getHeight() : couple.getFemaleDancer().getHeight());
          System.out.println(solution.femaleTree.toString());
          System.out.println(solution.maleTree.toString());
      }
      System.out.println(solution.femaleTree.toString());
      System.out.println(solution.maleTree.toString());
      testTreeEndToEnd(requests, responds);
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

  public static void main(String[] args) throws Exception {
//    testMaleTreeEndToEndPublic();
//    testFemaleTreeEndToEndPublic();
    testCustom();

//    System.out.println(tree.contains(7));
//    System.out.println(tree.contains(11));
//
//    tree.preOrderRecursion(tree.root);
//    System.out.println();
//    tree.preOrderLoop(tree.root);

  }
}
