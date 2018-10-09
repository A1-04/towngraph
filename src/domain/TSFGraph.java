package domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class TSFGraph {

	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Arc> arcs = new ArrayList<Arc>();
	private HashMap<Node, ArrayList<Node>> adjlist = new HashMap<Node, ArrayList<Node>>();

	public TSFGraph(String filename) throws IOException, ParserConfigurationException, SAXException {
		readXML(filename);
		ArrayList<Node> aux = new ArrayList<Node>();
		for (Node i : nodes) {
			aux = new ArrayList<Node>();
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

	public TSFGraph() {

	}

	private void readXML(String filename) throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

		nodes = handler.getNodes();
		System.out.println(nodes.get(0).getY());
		System.out.println(nodes.get(0).getX());
		System.out.println(nodes.get(0).getID());
		arcs = handler.getArcs();

		

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

	public ArrayList<Arc> adjacentNode(String id) {
		ArrayList<Node> values = new ArrayList<Node>();
		ArrayList<Arc> adjacents = new ArrayList<Arc>();
		Node aux;
		if (!belongNode(id)) {
			return null;
		} else {
			aux = returnNode(id);
			if (adjlist.containsKey(aux)) {
				values = adjlist.get(aux);
			}
		}
		values = filterNodes(values);
		adjacents = createArcs(values, aux);
		return adjacents;
	}

	public ArrayList<Arc> createArcs(ArrayList<Node> values, Node aux) {
		ArrayList<Arc> adjnodes = new ArrayList<Arc>();
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

	public ArrayList<Node> filterNodes(ArrayList<Node> values) {
		ArrayList<Node> aux = new ArrayList<Node>();
		aux.add(values.get(0));
		for (Node i : values) {
			if (!aux.contains(i)) {
				aux.add(i);
			}
		}
		return aux;
	}
}
