package domain;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

public class TSFGraph {

	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Arc> arcs = new ArrayList<Arc>();
	private HashMap<Node, ArrayList<Node>> adjlist = new HashMap<Node, ArrayList<Node>>();

	public TSFGraph(String filename) throws IOException {
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

	private void readXML(String filename) throws IOException {
		Graph graph = new TinkerGraph();
		GraphMLReader reader = new GraphMLReader(graph);

		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		reader.inputGraph(is);

		Iterable<Vertex> vertices = graph.getVertices();
		Iterator<Vertex> verticesIterator = vertices.iterator();

		while (verticesIterator.hasNext()) {

			Vertex vertex = verticesIterator.next();

			Node node = new Node(vertex.getProperty("x").toString(), vertex.getProperty("y").toString(),
					vertex.getId().toString());
			nodes.add(node);
			Iterable<Edge> edges = vertex.getEdges(Direction.IN);
			Iterator<Edge> edgesIterator = edges.iterator();

			while (edgesIterator.hasNext()) {

				Edge edge = edgesIterator.next();
				edge.setProperty("d7",
						edge.getVertex(Direction.IN).getId() + " " + edge.getVertex(Direction.OUT).getId());
				Arc arc = new Arc();

				if (edge.getProperty("name") == null) {
					arc = new Arc(edge.getProperty("d7").toString(), edge.getVertex(Direction.IN).getId().toString(),
							edge.getVertex(Direction.OUT).getId().toString(), "Unnamed",
							edge.getProperty("length").toString());
				} else {
					arc = new Arc(edge.getProperty("d7").toString(), edge.getVertex(Direction.IN).getId().toString(),
							edge.getVertex(Direction.OUT).getId().toString(), edge.getProperty("name").toString(),
							edge.getProperty("length").toString());
				}
				arcs.add(arc);
			}
		}

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
			adjnodes.add(arc);
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
		String[] splited = new String[2];
		String aux = "";
		for (Arc a : arcs) {
			splited = id.split(" ");
			aux = splited[1] + " " + splited[0];
			if (id.equals(a.getID()) || aux.equals(a.getID())){
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
