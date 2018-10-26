package domain;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class TSFGraph {

	private LinkedList<Node> nodes = new LinkedList<Node>();
	private LinkedList<Arc> arcs = new LinkedList<Arc>();
	private HashMap<Node, LinkedList<Node>> adjlist = new HashMap<Node, LinkedList<Node>>();

	public TSFGraph(String filename) throws IOException, ParserConfigurationException, SAXException {
		nodes = TSFReader.parseXMLnodes(filename);
		arcs = TSFReader.parseXMLarcs(filename);
		LinkedList<Node> aux = new LinkedList<Node>();
		for (Node i : nodes) {
			aux = new LinkedList<Node>();
			for (Arc a : arcs) {
				if (i.getID().equals(a.getSource())) {
					Node n = returnNode(a.getTarget());
					aux.add(n);
				}
			}
			adjlist.put(i, aux);
		}
	}

	public TSFGraph() {

	}

	public boolean belongNode(String id) {
		for (Node i : nodes) {
			if (i.getID().equals(id)) {
				if (adjlist.containsKey(i)) {
					return true;
				}
			}
		}
		return false;
	}

	public String[] positionNode(String id) {
		String[] pos = new String[2];
		if (!belongNode(id)) {
			return null;
		} else {
			for (Node i : nodes) {
				if (i.getID().equals(id)) {
					pos[0] = i.getX();
					pos[1] = i.getY();
				}
			}
		}
		return pos;
	}

	public LinkedList<Arc> adjacentNode(String id) {
		LinkedList<Node> values = new LinkedList<Node>();
		LinkedList<Arc> adjacents = new LinkedList<Arc>();
		Node aux;
		if (!belongNode(id)) {
			return null;
		} else {
			aux = returnNode(id);
			if (adjlist.containsKey(aux)) {
				values = adjlist.get(aux);
			}
		}
		adjacents = createArcs(values, aux);
		return adjacents;
	}

	public LinkedList<Node> adjacentNodes(String id) {
		LinkedList<Node> values = new LinkedList<Node>();
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

	public LinkedList<Arc> createArcs(LinkedList<Node> values, Node aux) {
		LinkedList<Arc> adjnodes = new LinkedList<Arc>();
		Arc arc = new Arc();
		for (Node i : values) {
			arc = returnArc(aux.getID() + " " + i.getID());
			if (arc != null) {
				adjnodes.add(arc);
			}
		}
		return adjnodes;
	}

	// to return the object node from an id
	public Node returnNode(String id) {
		for (Node i : nodes) {
			if (id.equals(i.getID())) {
				return i;
			}
		}
		return null;
	}

	// to return the object arc from an id
	public Arc returnArc(String id) {
		for (Arc a : arcs) {
			if (id.equals(a.getID())) {
				return a;
			}
		}
		return null;
	}
}
