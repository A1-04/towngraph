package domain;

import java.io.FileNotFoundException;
import java.io.IOException;
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

	public TreeNode Search(Problem p, String technique, int prof_max, int inc_prof) {
		int prof_actual = inc_prof;
		TreeNode solution = new TreeNode();
		while (/* NoSolution */prof_actual <= prof_max) {
			solution = Busqueda_acotada(p, technique, prof_actual);
			prof_actual += inc_prof;
		}
		return solution;
	}

	public TreeNode Busqueda_acotada(Problem p, String technique, int depth) {
		boolean sol = false;// boolean o TreeNode
		Frontier fringe = new Frontier();
		TreeNode n = new TreeNode(null, p.getI_state(), 0, 0, 0, technique);
		fringe.insert(n);
		TreeNode actualN = new TreeNode();

		while (!sol && !fringe.isEmpty()) {
			actualN = null; // selecciona_nodo(fringe)

			if (p.isGoal(actualN.getCurrentState())) {
				sol = true;
			} else {
				LinkedList<Object[]> succesorsList = p.getSpace().successors(actualN.getCurrentState());
				LinkedList<TreeNode> nodesList = null; // CreaListaNodosArbol(succesorsList,actualN,depth,"estrategia")
				fringe.insertList(nodesList);
			}
		}

		if (sol) {
			return null; // CreaSolución(actualN);
		} else {
			return null;
		}
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

}
