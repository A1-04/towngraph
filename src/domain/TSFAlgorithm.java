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

	public boolean Search(Problem p, String technique, int prof_max, int inc_prof) throws IOException {
		int prof_actual = inc_prof;
		boolean solution = false;
		while (!solution && prof_actual <= prof_max) {
			solution = busqueda_acotada(p, technique, prof_actual);
			prof_actual += inc_prof;
		}
		return solution;
	}

	public boolean busqueda_acotada(Problem p, String technique, int depth) throws IOException {
		boolean sol = false; // boolean or TreeNode
		Frontier fringe = new Frontier();
		TreeNode n = new TreeNode(null, p.getI_state(), 0, 0, 0, technique);
		fringe.insert(n);
		TreeNode actualN = new TreeNode();
		LinkedList<Object[]> succesorsList = new LinkedList<>();
		LinkedList<TreeNode> nodesList = new LinkedList<TreeNode>();
		TreeNode a = new TreeNode();

		while (!sol && !fringe.isEmpty()) {
			actualN = fringe.remove();

			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else {
				succesorsList = p.getSpace().successors(actualN.getCurrentState());
				nodesList = null; // CreaListaNodosArbol(succesorsList,actualN,depth,"estrategia")
				a = new TreeNode(succesorsList, actualN, depth, technique);
				fringe.insertList(nodesList);
			}
		}
		if (sol)
			return true;
		else
			return false;
	}

	public TreeNode BFS() {

		return null;
	}

	public TreeNode DFS(int depth) {
		return null;
	}

	public TreeNode UCS() {
		return null;
	}

	public TreeNode IDS(int depth) {
		return null;
	}

	public TreeNode prBFS() {

		return null;
	}

	public TreeNode prDFS(int depth) {
		return null;
	}

	public TreeNode prUCS() {
		return null;
	}

	public TreeNode prIDS(int depth) {
		return null;
	}

	public LinkedList<TreeNode> create_solution(TreeNode n_actual) throws IOException {
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
