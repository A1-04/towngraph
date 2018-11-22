package domain;

import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public class TSFAlgorithm {
	public static LinkedList<TreeNode> search(Problem p, String technique, int prof_max, boolean pruning)
			throws IOException {
		LinkedList<TreeNode> solution = new LinkedList<>();
		try {
			while (solution.isEmpty()) {
				if (pruning) {
					solution = bounded_search_pruning(p, technique, prof_max);
				} else {
					solution = bounded_search(p, technique, prof_max);
				}
			}
		} catch (NullPointerException e) {
			return null;
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
		Object[] thess = new Object[3];
		TSFGraph g = new TSFGraph();
		g = p.getSpace().getGraph();

		while (!sol && !fringe.isEmpty()) {
			actualN = fringe.remove();

			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else if (depth > actualN.getD()) {
				succesorsList = p.getSpace().successors(actualN.getCurrentState());
				while (!succesorsList.isEmpty()) {
					thess = succesorsList.remove();
					if (actualN.getParent() == null) {
						node = makeNode(thess, g, actualN, fringe, technique);
						fringe.insert(node);

					} else if (actualN.getParent().getCurrentState().getActualNode().getID()
							.equals((((State) thess[1]).getActualNode().getID()))) {
					} else {
						node = makeNode(thess, g, actualN, fringe, technique);
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
		Object[] thess = new Object[3];
		TSFGraph g = new TSFGraph();
		g = p.getSpace().getGraph();

		while (!sol && !fringe.isEmpty()) {
			actualN = fringe.remove();

			if (!VL.containsKey(actualN.getCurrentState().getMD5())) {
				VL.put(actualN.getCurrentState().getMD5(), actualN.getF());
			}
			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else if (depth > actualN.getD()) {
				succesorsList = p.getSpace().successors(actualN.getCurrentState());
				while (!succesorsList.isEmpty()) {
					thess = succesorsList.remove();
					if (actualN.getParent() == null) {
						node = makeNode(thess, g, actualN, fringe, technique);
						fringe.insert(node);

						debug(actualN, sol, fringe);
					} else if (actualN.getParent().getCurrentState().getActualNode().getID()
							.equals((((State) thess[1]).getActualNode().getID()))) {
					} else {
						node = makeNode(thess, g, actualN, fringe, technique);
						if (!VL.containsKey(node.getCurrentState().getMD5())) {
							fringe.insert(node);

							debug(actualN, sol, fringe);
						} else {
							hasBetterF(VL, node, fringe);

							debug(actualN, sol, fringe);

						}
					}
				}
			}
		}

		if (sol)

		{
			return create_solution(actualN);
		} else {
			return null;
		}
	}

	private static void debug(TreeNode actualN, boolean sol, Frontier fringe) {
		Iterator<TreeNode> it = fringe.getTreenodes().iterator();

		System.out.println("\nNODE INSERTED: " + actualN.getCurrentState().getActualNode().getID() + " DEPTH: "
				+ actualN.getD() + "\n");
		System.out.println("\nSOLUCION: " + sol + "\n");
		System.out.println("Estado del problema " + actualN.getCurrentState().getN_list().size() + "\n");
		System.out.println("--FRINGE with size " + fringe.getTreenodes().size() + "---------------------");
		while (it.hasNext()) {
			System.out.println(it.next().getCurrentState().getActualNode().getID());
		}
		System.out.println("---------------------------------------------");
	}

	private static TreeNode makeNode(Object[] thess, TSFGraph g, TreeNode actualN, Frontier fringe, String technique) {
		Arc arc = g.returnArc(
				actualN.getCurrentState().getActualNode().getID() + " " + ((State) thess[1]).getActualNode().getID());

		TreeNode node = new TreeNode((State) thess[1], actualN, (String) thess[0], technique,
				actualN.getPathcost() + Float.parseFloat(arc.getDistance()));
		return node;
	}

	private static void hasBetterF(Hashtable<String, Float> VL, TreeNode node, Frontier fringe) {
		float aux = VL.get(node.getCurrentState().getMD5());
		if (Math.abs(node.getF()) < Math.abs(aux)) {
			fringe.insert(node);
			VL.replace(node.getCurrentState().getMD5(), aux, node.getF());
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
