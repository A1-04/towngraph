package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Frontier {
	private ArrayList<TreeNode> treenodes;

	public Frontier() {

	}

	public ArrayList<TreeNode> createFrontier() {
		treenodes = new ArrayList<TreeNode>();
		Collections.sort(treenodes, new Comparator<TreeNode>() {
			public int compare(TreeNode a, TreeNode b) {
				return new Float(a.getF()).compareTo(new Float(b.getF()));
			}
		});
		return treenodes;
	}

	public void insert(TreeNode tn) {
		treenodes.add(tn);
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