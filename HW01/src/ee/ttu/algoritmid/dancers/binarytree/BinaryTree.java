package ee.ttu.algoritmid.dancers.binarytree;

import ee.ttu.algoritmid.dancers.Dancer;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
  private Node root;
  private Node balancePoint;
  private Node newNode;

  public BinaryTree(){
    this.root = null;
  }

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
    if (balancePoint != null) {
      Node newRoot;
      Node parent = balancePoint.parent;

      int balance = getBalanceValue(balancePoint);

      // LL
      if (balance > 1 && getBalanceValue(balancePoint.left) >= 0) {
        newRoot = rightRotate(balancePoint);
      }
      // RR
      else if (balance < -1 && getBalanceValue(balancePoint.right) <= 0) {
        newRoot = leftRotate(balancePoint);
      }
      // LR
      else if (balance > 1 && getBalanceValue(balancePoint.left) < 0) {
        balancePoint.left = leftRotate(balancePoint.left);
        newRoot = rightRotate(balancePoint);
      }
      // RL
      else {
//      else if (balance < -1 && getBalanceValue(balancePoint.right) > 0) {
        balancePoint.right = rightRotate(balancePoint.right);
        newRoot = leftRotate(balancePoint);
      }
//      else {
//        System.out.println("I reached something I shouldn't have reached");
//        return;
//      }

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

  public static void main(String[] args) {


  }
}
