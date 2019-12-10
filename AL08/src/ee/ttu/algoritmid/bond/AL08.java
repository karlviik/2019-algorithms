package ee.ttu.algoritmid.bond;

public class AL08 {

	public enum Network {
		FRIENDLY, UNFRIENDLY, UNKNOWN;
	}

	private DisjointSubsets disjointSubsets = new DisjointSubsets();

	public AL08() {
		disjointSubsets.addSubset("A");
		disjointSubsets.addSubset("U");
	}

	public DisjointSubsets getDisjointSubsets() {
		return disjointSubsets;
	}

	public void talkedToEachOther(String name1, String name2) {
		disjointSubsets.union(name1, name2);
	}

	public void addPerson(String name) {
		disjointSubsets.addSubset(name);
	}

	public void friendly(String name) {
		disjointSubsets.union("A", name);
	}

	public void unfriendly(String name) {
		disjointSubsets.union("U", name);
	}

	public Network memberOfNetwork(String name) {
		String parent = disjointSubsets.find(name);
		if (parent.equals("A")) {
			return Network.FRIENDLY;
		}
		if (parent.equals("U")) {
			return Network.UNFRIENDLY;
		}
		return Network.UNKNOWN;
	}

}