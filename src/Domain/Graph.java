package Domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.soap.Node;

public class Graph {

	private ArrayList<Node> nodes;
	private HashMap<Node, ArrayList<Node>> adjlist;;

	public Graph(String filename) {
		ArrayList<Node> adjlist;
	}

	public boolean belongNode(int id) {
		Node aux;
		for (Node i : nodes) {
			if (i.getID() == id) {
				aux = i;
			}
		}
		if (adjlist.containsKey(aux)) {
			return true;
		}
		return false;
	}

	public int[] positionNode(int id) {
		int[] xy = new int[2];
		if (!belongNode(id)) {
			return null;
		} else {
			for (Node i : nodes) {
				if (i.getID() == id) {
					xy[0] = i.getX();
					xy[1] = i.getY();
				}
			}
		}
	}

	public Collection adjacentNode(int id) {
		Collection values;
		Node aux;
		if (!belongNode(id)) {
			return null;
		} else {
			aux = returnNode(id);
			if (adjlist.containsKey(aux)) {
				// values = adjlist.values(); necesitamos un método que devuelva los valores de
				// una key
			}
		}
		return values;
	}

	public Node returnNode(int id) {
		for (Node i : nodes) {
			if (id == i.getID()) {
				return i;
			}
		}
		return null;
	}
	// método para recuperar el nodo de una id

}
