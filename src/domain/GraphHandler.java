package domain;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GraphHandler extends DefaultHandler {
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Arc> arcs = new ArrayList<Arc>();
	private Node node;
	private Arc arc;
	private StringBuilder buffer = new StringBuilder();
	private String d, d_length, d_name;

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Arc> getArcs() {
		return arcs;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		buffer.append(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
		case "data":
			if (d.equals("d4")) {
				node.setY(buffer.toString());
				d = "";
			} else if (d.equals("d5")) {
				node.setX(buffer.toString());
				d = "";
			} else if (d_name.equals(d)) {
				arc.setName(buffer.toString());
				d = "";
			} else if (d_length.equals(d)) {
				arc.setDistance(buffer.toString());
				d = "";
			}
		}

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		switch (qName) {
		case "key":
			if (attributes.getValue("attr.name").equals("name") && attributes.getValue("for").equals("edge")) {
				d_name = attributes.getValue("id");
			} else if (attributes.getValue("attr.name").equals("length")) {
				d_length = attributes.getValue("id");
			}
			break;
		case "node":
			node = new Node();
			nodes.add(node);
			node.setID(attributes.getValue("id"));
			break;
		case "data":
			if (attributes.getValue("key").equals("d4")) {
				d = "d4";
			} else if (attributes.getValue("key").equals("d5")) {
				d = "d5";
			} else if (attributes.getValue("key").equals(d_name)) {
				d = d_name;
			} else if (attributes.getValue("key").equals(d_length)) {
				d = d_length;
			}
			buffer.delete(0, buffer.length());
			break;
		case "edge":
			arc = new Arc();
			arcs.add(arc);
			arc.setSource(attributes.getValue("source"));
			arc.setTarget(attributes.getValue("target"));
			arc.setID(arc.getSource() + " " + arc.getTarget());
			break;
		}
	}
}
