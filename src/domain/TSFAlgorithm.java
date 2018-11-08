package domain;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

public class TSFAlgorithm {
	public static LinkedList<TreeNode> search(Problem p, String technique, int prof_max, int inc_prof, boolean pruning)
			throws IOException {
		int prof_actual = inc_prof;
		LinkedList<TreeNode> solution = new LinkedList<>();
		while (solution.isEmpty() && prof_actual <= prof_max) {
			if (pruning) {
				solution = bounded_search_pruning(p, technique, prof_actual);
				prof_actual += inc_prof;
			} else {
				solution = bounded_search(p, technique, prof_actual);
				prof_actual += inc_prof;
			}
		}
		return solution;
	}

	public static LinkedList<TreeNode> bounded_search(Problem p, String technique, int depth) throws IOException {
		boolean sol = false;
		Frontier fringe = new Frontier();
		TreeNode firstn = new TreeNode(null, p.getI_state(), 0, 0, 0, technique);
		fringe.insert(firstn);
		TreeNode actualN = new TreeNode();
		LinkedList<Object[]> succesorsList = new LinkedList<>();
		TreeNode node = new TreeNode();
		State s = new State();
		Object[] thess = new Object[3];

		while (!sol && !fringe.isEmpty()) {
			actualN = fringe.remove();

			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else {
				succesorsList = p.getSpace().successors(actualN.getCurrentState());
				while (!succesorsList.isEmpty()) {
					thess = succesorsList.remove();
					if (actualN.getParent() == null) {
						s = (State) thess[1];
						node = new TreeNode(s, actualN, actualN.getD() + 1, technique);
						fringe.insert(node);
					} else if (actualN.getParent().getCurrentState().getActualNode().getID()
							.equals((((State) thess[1]).getActualNode().getID()))) {
					} else {
						s = (State) thess[1];
						node = new TreeNode(s, actualN, actualN.getD() + 1, technique);
						fringe.insert(node);
					}
				}
			}
		}
		if (sol) {
			return create_solution(actualN);
		} else {
			return null;
		}
	}

	public static LinkedList<TreeNode> bounded_search_pruning(Problem p, String technique, int depth)
			throws IOException {
		Hashtable<String, Float> VL = new Hashtable<String, Float>();
		boolean sol = false;
		Frontier fringe = new Frontier();
		TreeNode firstn = new TreeNode(null, p.getI_state(), 0, 0, 0, technique);
		fringe.insert(firstn);
		TreeNode actualN = new TreeNode();
		LinkedList<Object[]> succesorsList = new LinkedList<>();
		TreeNode node = new TreeNode();
		State s = new State();
		Object[] thess = new Object[3];
		float aux = 0;

		while (!sol && !fringe.isEmpty()) {
			actualN = fringe.remove();
			if (!VL.containsKey(actualN.getCurrentState().getMD5())) {
				VL.put(actualN.getCurrentState().getMD5(), actualN.getF());
			}

			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else {
				succesorsList = p.getSpace().successors(actualN.getCurrentState());
				while (!succesorsList.isEmpty()) {
					thess = succesorsList.remove();
					if (actualN.getParent() == null) {
						s = (State) thess[1];
						node = new TreeNode(s, actualN, actualN.getD() + 1, technique);
						fringe.insert(node);
					} else if (actualN.getParent().getCurrentState().getActualNode().getID()
							.equals((((State) thess[1]).getActualNode().getID()))) {
					} else {
						s = (State) thess[1];
						node = new TreeNode(s, actualN, actualN.getD() + 1, technique);
						if (!VL.containsKey(node.getCurrentState().getMD5())) {
							fringe.insert(node);
						} else {
							aux = VL.get(node.getCurrentState().getMD5());
							if (Math.abs(node.getF()) < Math.abs(aux)) {
								fringe.insert(node);
								VL.replace(node.getCurrentState().getMD5(), aux, node.getF());
							}
						}

					}
				}
			}
		}
		if (sol) {
			return create_solution(actualN);
		} else {
			return null;
		}
	}

	public static LinkedList<TreeNode> create_solution(TreeNode n_actual) throws IOException {
		LinkedList<TreeNode> sol = new LinkedList<TreeNode>();
		sol.add(n_actual);
		while (n_actual.getParent() != null) {
			sol.add(n_actual.getParent());
			n_actual = n_actual.getParent();
		}
		Collections.reverse(sol);

		return sol;
	}

}
