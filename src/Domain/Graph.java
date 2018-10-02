package Domain;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {

	private ArrayList<Node> nodes;
	private ArrayList<Arc> arcs;
	private HashMap<Node, ArrayList<Node>> adjlist;;

	public Graph(String filename) {
		// xml readed with a method: readNodes() and readArcs
		ArrayList<Node> aux = new ArrayList<Node>();
		for (Node i : nodes) {
			for (Arc a : arcs) {
				if (i.getID() == a.getSource()) {
					Node n = returnNode(a.getTarget());
					aux.add(n);
				} else if (i.getID() == a.getTarget()) {
					Node n = returnNode(a.getSource());
					aux.add(n);
				}
			}
			adjlist.put(i, aux);
		}
	}

	public boolean belongNode(int id) {
		for (Node i : nodes) {
			if (i.getID() == id) {
				if (adjlist.containsKey(i)) {
					return true;
				}
			}
		}
		return false;
	}

	public double[] positionNode(int id) {
		double[] pos = new double[2];
		if (!belongNode(id)) {
			return null;
		} else {
			for (Node i : nodes) {
				if (i.getID() == id) {
					pos[0] = i.getX();
					pos[1] = i.getY();
				}
			}
		}
		return pos;
	}

	public ArrayList<Node> adjacentNode(int id) {
		ArrayList<Node> values = new ArrayList<Node>();
		Node aux;
		if (!belongNode(id)) {
			return null;
		} else {
			aux = returnNode(id);
			if (adjlist.containsKey(aux)) {
				values = adjlist.get(aux);
			}
		}
		return values;
	}

	// to return the object node from an id
	public Node returnNode(int id) {
		for (Node i : nodes) {
			if (id == i.getID()) {
				return i;
			}
		}
		return null;
	}

}
