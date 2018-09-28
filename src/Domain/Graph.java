package Domain;

import java.util.ArrayList;

public class Graph<N, A> {

	private ArrayList<N> nodes;
	private ArrayList<A> arcs;

	public Graph(String filename) {
		ArrayList<N> nodes;
		ArrayList<A> arcs;
	}

	public boolean belongNode(int id) {
		for (N i : nodes) {
			if (i.getID() == id) {
				return true;
			}
		}
		return false;
	}

	public int[] positionNode(int id) {
		int[] xy = new int[2];
		if (!belongNode(id)) {
			return null;
		} else {
			for (N i : nodes) {
				if (i.getID() == id) {
					xy[0] = i.getX();
					xy[1] = i.getY();
				}
			}
		}
	}

	public ArrayList<A> adjacentNode(int id) {
		ArrayList<A> adjlist;
		if (!belongNode(id)) {
			return null;
		} else {
			for (A i : arcs) {
				if (id == i.getSource() || id == i.getTarget()) {
					adjlist.add(i);
				}
			}
		}

	}

}
