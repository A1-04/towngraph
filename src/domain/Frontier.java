package domain;

import java.util.LinkedList;
import java.util.PriorityQueue;

public class Frontier {
	private PriorityQueue<TreeNode> treenodes;

	public Frontier() {
		createFrontier();
	}

	private void createFrontier() {
		treenodes = new PriorityQueue<TreeNode>();
	}

	public void insert(TreeNode tn) {
		treenodes.add(tn);
	}

	public void insertList(LinkedList<TreeNode> nodesList) {
		treenodes.addAll(nodesList);
	}

	public PriorityQueue<TreeNode> getTreenodes() {
		return treenodes;
	}

	public TreeNode remove() {
		return treenodes.poll();
	}

	public boolean isEmpty() {
		if (treenodes.size() == 0) {
			return true;
		}
		return false;
	}

}