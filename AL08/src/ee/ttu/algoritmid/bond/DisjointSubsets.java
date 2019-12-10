package ee.ttu.algoritmid.bond;

import java.util.HashMap;

public class DisjointSubsets {
	private HashMap<String, Node> elementMapper = new HashMap<>();

	public String find(String element) throws IllegalArgumentException {
		if (!elementMapper.containsKey(element)) {
			throw new IllegalArgumentException();
		}
		Node node = elementMapper.get(element);
		while (!node.parent.equals(node)) {
			node = node.parent;
		}
		return node.name;
	}

	public void union(String element1, String element2) throws IllegalArgumentException {
		if (!elementMapper.containsKey(element1) || !elementMapper.containsKey(element2)) {
			throw new IllegalArgumentException();
		}
		Node node1 = elementMapper.get(element1);
		while (!node1.parent.equals(node1)) {
			node1 = node1.parent;
		}
		Node node2 = elementMapper.get(element2);
		while (!node2.parent.equals(node2)) {
			node2 = node2.parent;
		}
		if (node1.name.equals("U") || node1.name.equals("A")) {
			node2.parent = node1;
		}
		else {
			node1.parent = node2;
		}
	}

	public void addSubset(String element) throws IllegalArgumentException {
		if (elementMapper.containsKey(element)) {
			throw new IllegalArgumentException();
		}
		Node node = new Node(element);
		elementMapper.put(element, node);
	}

}