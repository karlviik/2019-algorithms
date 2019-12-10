package ee.ttu.algoritmid.bond;

public class Node {
	String name;
	Node parent;

	public Node(String name) {
		this.name = name;
		this.parent = this;
	}
}
