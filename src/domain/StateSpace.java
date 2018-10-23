package domain;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StateSpace {
	private TSFGraph g;

	private ArrayList<State> states = new ArrayList<State>();

	public StateSpace(String filename) throws IOException, ParserConfigurationException, SAXException {
		g = new TSFGraph(filename);
	}

	public StateSpace() {
	}

	public ArrayList<Object[]> successors(State s) {
		ArrayList<Node> adj = g.adjacentNodes(s.getActualNode().getID());
		ArrayList<Node> n_list = s.getN_list();
		ArrayList<Node> aux = n_list;
		Object[] auxReturn = new Object[3];
		ArrayList<Object[]> toReturn = new ArrayList<Object[]>();
		Arc ar = new Arc();
		State st = new State();

		for (Node a : adj) {
			aux = n_list;
			for (Node b : n_list) {
				if (a.getID().equals(b.getID())) {
					aux.remove(b);
				}
			}
			String a1 = "I am in " + s.getActualNode().getID() + " and I go to " + a.getID();
			st = new State(a, aux);
			ar = g.returnArc(s.getActualNode().getID() + " " + a.getID());

			auxReturn[0] = (Object) a1;
			auxReturn[1] = (Object) st;
			auxReturn[2] = (Object) ar.getDistance();
			toReturn.add(auxReturn);
		}
		return toReturn;
	}

	public boolean belongNode(State s) {
		for (State a : states) {
			if (a.getActualNode().getID().equals(s.getActualNode().getID()))
				return true;
		}
		return false;
	}
}
