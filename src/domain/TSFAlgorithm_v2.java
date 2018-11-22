package domain;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class TSFAlgorithm_v2 {
	public static LinkedList<TreeNode> search(Problem p, String strategy, int prof_max, int inc_prof, boolean pruning) {
		int prof_actual = inc_prof;
		LinkedList<TreeNode> sol = new LinkedList<>();
		while (sol.isEmpty() && prof_actual < prof_max) {
			sol = bounded_search(p, strategy, prof_actual, pruning);
			prof_actual = prof_actual + inc_prof;
		}
		return sol;
	}

	private static LinkedList<TreeNode> bounded_search(Problem p, String strategy, int prof_max, boolean pruning) {
		Frontier fringe = new Frontier();
		TreeNode n_inicial = new TreeNode(null, p.getI_state(), 0, 0, strategy);
		fringe.insert(n_inicial);
		boolean sol = false;
		TreeNode n_actual = new TreeNode();
		Hashtable<String, Float> VL = new Hashtable<String, Float>();

		while (!sol && !fringe.isEmpty()) {
			n_actual = fringe.remove();

			if (pruning) {
				if (!VL.containsKey(n_actual.getCurrentState().getMD5())) {
					VL.put(n_actual.getCurrentState().getMD5(), n_actual.getF());
				} else {
					float f_aux = VL.get(n_actual.getCurrentState().getMD5());
					if (Math.abs(n_actual.getF()) <= Math.abs(f_aux)) {
						VL.replace(n_actual.getCurrentState().getMD5(), f_aux, n_actual.getF());
					}
				}
			}
			if (p.isGoal(n_actual.getCurrentState())) {
				sol = true;
			} else {
				LinkedList<Object[]> ls = p.getSpace().successors(n_actual.getCurrentState());
				LinkedList<TreeNode> ln = makeListTreeNodes(ls, n_actual, prof_max, strategy);
				if (pruning) {
					makePruning(ln, VL, fringe);
				} else {
					fringe.insertList(ln);
				}
			}
		}

		if (sol) {
			return createSolution(n_actual);
		} else {
			return new LinkedList<TreeNode>();
		}
	}

	private static void makePruning(LinkedList<TreeNode> ln, Hashtable<String, Float> VL, Frontier fringe) {
		Iterator<TreeNode> it = ln.iterator();
		while (it.hasNext()) {
			TreeNode n_aux = it.next();
			if (!VL.containsKey(n_aux.getCurrentState().getMD5())) {
				VL.put(n_aux.getCurrentState().getMD5(), n_aux.getF());
				fringe.insert(n_aux);
			} else {
				float f_aux = VL.get(n_aux.getCurrentState().getMD5());
				if (Math.abs(n_aux.getF()) <= Math.abs(f_aux)) {
					VL.replace(n_aux.getCurrentState().getMD5(), f_aux, n_aux.getF());
					fringe.insert(n_aux);
				}
			}
		}
	}

	private static LinkedList<TreeNode> createSolution(TreeNode n_actual) {
		LinkedList<TreeNode> sol = new LinkedList<>();
		sol.add(n_actual);
		while (n_actual.getParent() != null) {
			sol.add(n_actual.getParent());
			n_actual = n_actual.getParent();
		}
		return sol;
	}

	private static LinkedList<TreeNode> makeListTreeNodes(LinkedList<Object[]> ls, TreeNode n_actual, int prof_max,
			String strategy) {
		LinkedList<TreeNode> ln = new LinkedList<>();
		Iterator<Object[]> it = ls.iterator();
		while (it.hasNext()) {
			State aux = (State) it.next()[1];
			if (n_actual.getD() + 1 < prof_max) {
				if (n_actual.getParent() == null) {
					TreeNode tn = new TreeNode(aux, n_actual, strategy);
					ln.add(tn);
				} else {
					if (!n_actual.getParent().equals(aux)) {
						TreeNode tn = new TreeNode(aux, n_actual, strategy);
						ln.add(tn);
					}
				}
			}
		}
		return ln;
	}
}
