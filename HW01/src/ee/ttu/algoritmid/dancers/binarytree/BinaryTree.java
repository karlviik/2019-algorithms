package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;

public class BinaryTree {
  private Node root;

  private BinaryTree(){
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

  private void add(int value, Dancer dancer){
    root = addDancer(root, null, value, dancer);
  }

  public String toString(){
    return root.printTree(new StringBuilder(), true, new StringBuilder()).toString();
  }

  public Node findLessOrEqual(Node node, int value) {
    if (node == null) {
      return null;
    }
    if (node.value == value) {
      return node;
    }
    if (node.value > value) {
      return findLessOrEqual(node.left, value);
    }
    Node rightNode = findLessOrEqual(node.right, value);
    if (rightNode == null) {
      return node;
    }
    return rightNode;
  }

  public Node findMoreOrEqual(Node node, int value) {
    if (node == null) {
      return null;
    }
    if (node.value == value) {
      return node;
    }
    if (node.value < value) {
      return findMoreOrEqual(node.right, value);
    }
    Node leftNode = findMoreOrEqual(node.left, value);
    if (leftNode == null) {
      return node;
    }
    return leftNode;
  }

//  /**
//   * Check if value exsists in given node
//   * @param root node where search is being made
//   * @param value to be searched
//   */
//  private boolean containsNode(Node root, int value){
//    if (root == null){
//      return false;
//    }
//
//    if (root.value == value){
//      return true;
//    }
//
//    return root.value < value ? containsNode(root.right, value) : containsNode(root.left, value);

//  }
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



  public static void main(String[] args) {
    BinaryTree tree = new BinaryTree();

    System.out.println(tree.toString());

    System.out.println(tree.contains(7));
    System.out.println(tree.contains(11));

    tree.preOrderRecursion(tree.root);
    System.out.println();
    tree.preOrderLoop(tree.root);

  }
}
