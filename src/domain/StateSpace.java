package domain;

import java.util.ArrayList;

public class StateSpace {
	
	private ArrayList<State> states = new ArrayList<State>();

	public void successors(State s) {
		for(Node n : s.getN_list()) {
			//System.out.printf("\nI am in %s and I go to %s \n New State: %s\n", s.getID, n.getID(), n.getID());
		}
	}

	public boolean belongNode(State s) {
		for (State a : states) {
			if (a.getActualNode().getID().equals(s.getActualNode().getID()))
				return true;
		}
		return false;
	}
}
