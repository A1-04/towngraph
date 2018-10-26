package domain;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StateSpace {
	private TSFGraph g;

	private LinkedList<State> states = new LinkedList<State>();

	public StateSpace(String filename) throws IOException, ParserConfigurationException, SAXException {
		g = new TSFGraph(filename);
	}

	public StateSpace() {
	}

	public LinkedList<Object[]> successors(State s) {
		LinkedList<Node> adj = g.adjacentNodes(s.getActualNode().getID());
		LinkedList<Node> o_list = s.getN_list();
		LinkedList<Node> n_list = o_list;
		Object[] auxReturn = new Object[3];
		LinkedList<Object[]> toReturn = new LinkedList<Object[]>();
		Arc ar = new Arc();
		State st = new State();

		for (Node a : adj) {
			n_list = o_list;
			for (Node b : n_list) {
				if (a.getID().equals(b.getID())) {
					n_list.remove(b);
				}
			}
			String a1 = "I am in " + s.getActualNode().getID() + " and I go to " + a.getID();
			st = new State(a, n_list);
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
