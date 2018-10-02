package Domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Graph {

	private ArrayList<Node> nodes;
	private ArrayList<Arc> arcs;
	private HashMap<Node, ArrayList<Node>> adjlist;;

	public Graph(String filename) {
		ArrayList<Node> aux;
		for (Node i : nodes) {
			for (Arc a : arcs) {
				if (i.getID() == a.getSource() || i.getID() == a.getTarget()) {
					aux.add(a);
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

	public int[] positionNode(int id) {
		int[] pos = new int[2];
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
	}

	public ArrayList<Node> adjacentNode(int id) {
		ArrayList<Node> values;
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
