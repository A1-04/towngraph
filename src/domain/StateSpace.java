package domain;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class StateSpace {

	private ArrayList<State> states = new ArrayList<State>();

	public StateSpace(String filename) throws IOException, ParserConfigurationException, SAXException {

	}

	public StateSpace() {
		
	}

	public String successors(State s) {
		/*
		 * TSFGraph g = new TSFGraph(); String st = "Im in " + s.getActualNode().getID()
		 * + " and I go to " + g.adjacentNode(s.getActualNode().getID()) + ", " +
		 * s.getActualNode().getID(); String cost =
		 * g.returnArc(s.getActualNode().getID() + " " +
		 * g.adjacentNode(s.getActualNode().getID())) .getDistance();
		 */
		states.add(s);
		return null;
	}

	public boolean belongNode(State s) {
		for (State a : states) {
			if (a.getActualNode().getID().equals(s.getActualNode().getID()))
				return true;
		}
		return false;
	}
}
