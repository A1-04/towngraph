package domain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class TSFAlgorithm {
	private Hashtable<String, Float> VL = new Hashtable<String, Float>();
	private String Jname = "";
	private Problem p = new Problem();

	public TSFAlgorithm() {

	}

	public TSFAlgorithm(Hashtable<String, Float> vL, String jname, Problem p)
			throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException {
		super();
		VL = vL;
		Jname = jname;
		this.p = new Problem(Jname);
	}

	public static LinkedList<TreeNode> search(Problem p, String technique, int prof_max, int inc_prof)
			throws IOException {
		int prof_actual = inc_prof;
		LinkedList<TreeNode> solution = new LinkedList<>();
		while (solution.isEmpty() && prof_actual <= prof_max) {
			solution = bounded_search(p, technique, prof_actual);
			prof_actual += inc_prof;
		}
		return solution;
	}

	public static LinkedList<TreeNode> bounded_search(Problem p, String technique, int depth) throws IOException {
		boolean sol = false; // boolean or TreeNode
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
					// System.out.println("ACTION: " + thess[0]);
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
		if (sol)

		{
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
