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
		if (treenodes.isEmpty()) {
			treenodes.add(tn);
		} else {
			for (int i = 0; i < treenodes.size(); i++) {
				if (treenodes.get(i).getF() <= tn.getF()) {
					treenodes.add(i, tn);
					break;
				} else if (tn.getF() < treenodes.get(treenodes.size() - 1).getF()) {
					treenodes.add(tn);
				}
			}
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