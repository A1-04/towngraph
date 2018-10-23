package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Frontier {
	private ArrayList<TreeNode> treenodes;

	public Frontier() {
		createFrontier();
	}

	private void createFrontier() {
		treenodes = new ArrayList<TreeNode>();
	}

	public void insert(TreeNode tn) {
		if (treenodes.isEmpty()) {
			treenodes.add(tn);
		} else {
			for (int i = 0; i < treenodes.size(); i++) {
				if (treenodes.get(i).getF() < tn.getF()) {
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