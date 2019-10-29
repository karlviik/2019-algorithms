package ee.ttu.algoritmid.subtreesum;


public class SubtreeSum {

  /**
   * Calculate sum of all left children for every node
   * @param rootNode root node of the tree. Use it to traverse the tree.
   * @return root node of the tree where for every node is computed sum of it's all left children
   */
  public Node calculateLeftSums(Node rootNode) {

    if (rootNode == null) {
      return rootNode;
    }
    if (rootNode.getLeft() != null) {
      calculateLeftSums(rootNode.getLeft());
      rootNode.setSumOfAllLeft(rootNode.getLeft().getSumOfAllChildren() + rootNode.getLeft().getValue());
    } else {
      rootNode.setSumOfAllLeft(0);
    }
    if (rootNode.getRight() != null) {
      calculateLeftSums(rootNode.getRight());
      rootNode.setSumOfAllChildren(rootNode.getSumOfAllLeft() + rootNode.getRight().getSumOfAllChildren() + rootNode.getRight().getValue());
    } else {
      rootNode.setSumOfAllChildren(rootNode.getSumOfAllLeft());
    }

    return rootNode;
  }


  public static void main(String[] args) throws Exception {
    /**
     *  Use this example to test your solution
     *                  Tree:
     *                   15
     *               /       \
     *             10         17
     *           /   \       /  \
     *         3     13     5    25
     */
    Node rootNode = new Node(15);
    Node a = new Node(10);
    Node b = new Node(17);
    Node c = new Node(3);
    Node d = new Node(13);
    Node e = new Node(5);
    Node f = new Node(25);

    rootNode.setLeft(a);
    rootNode.setRight(b);
    a.setLeft(c);
    a.setRight(d);
    b.setLeft(e);
    b.setRight(f);

    SubtreeSum solution = new SubtreeSum();
    solution.calculateLeftSums(rootNode);

    if (rootNode.getSumOfAllLeft() != 26 ||
        a.getSumOfAllLeft() != 3 ||
        b.getSumOfAllLeft() != 5 ||
        c.getSumOfAllLeft() != 0) {
      throw new Exception("There is a mistake in your solution.");
    }

    System.out.println("Your solution should be working fine in basic cases, try to push.");
  }

}