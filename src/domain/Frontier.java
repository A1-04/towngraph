package domain;

import java.util.LinkedList;
import java.util.List;

public class Frontier {
	private List<TreeNode> treenodes;

	public Frontier() {
		createFrontier();
	}

	private void createFrontier() {
		treenodes = new LinkedList<TreeNode>();
	}

	public void insert(TreeNode tn) {
		treenodes.add(tn);

	}

	public void insertList(LinkedList<TreeNode> nodesList) {
		for (TreeNode i : nodesList) {
			insert(i);
		}
	}

	public TreeNode remove() {
		return treenodes.remove(0);
	}

	public boolean isEmpty() {
		if (treenodes.size() == 0) {
			return true;
		}
		return false;
	}

}