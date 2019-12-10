package ee.ttu.algoritmid.bond;

import java.util.ArrayList;
import java.util.HashMap;

public class DisjointSubsets {
	private HashMap<String, Node> elementMapper = new HashMap<>();

	public String find(String element) throws IllegalArgumentException {
		if (!elementMapper.containsKey(element)) {
			throw new IllegalArgumentException();
		}
		Node node = elementMapper.get(element);
		ArrayList<Node> optimization = new ArrayList<>();
		while (!node.parent.equals(node)) {
			optimization.add(node);
			node = node.parent;
		}
		for (Node subnode : optimization) {
			subnode.parent = node;
		}
		return node.name;
	}

	public void union(String element1, String element2) throws IllegalArgumentException {
		if (!elementMapper.containsKey(element1) || !elementMapper.containsKey(element2)) {
			throw new IllegalArgumentException();
		}
		Node node1 = elementMapper.get(element1);
		ArrayList<Node> optimization = new ArrayList<>();

		while (!node1.parent.equals(node1)) {
			optimization.add(node1);

			node1 = node1.parent;
		}
		for (Node subnode : optimization) {
			subnode.parent = node1;
		}
		Node node2 = elementMapper.get(element2);
		optimization.clear();
		while (!node2.parent.equals(node2)) {
			optimization.add(node1);

			node2 = node2.parent;
		}
		for (Node subnode : optimization) {
			subnode.parent = node2;
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