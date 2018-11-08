package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class TSFReader {

	static LinkedList<Node> parseXMLnodes(String filename)
			throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

		return handler.getNodes();
	}

	static LinkedList<Arc> parseXMLarcs(String filename)
			throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

		return handler.getArcs();
	}

	public static Object[] parseJSON(StateSpace space, State instate, String filename)
			throws IOException, ParseException, ParserConfigurationException, SAXException, FileNotFoundException {
		JSONParser parser = new JSONParser();
		File f = new File(".");
		String[] ab = new String[3];
		Object object = parser.parse(new FileReader(f.getCanonicalPath() + "/data/" + filename + ".json"));
		Node n = new Node();
		LinkedList<Node> listNodes = new LinkedList<Node>();
		Object[] toReturn = new Object[2];

		// convert Object to JSONObject
		JSONObject jsonObject = (JSONObject) object;
		String name = (String) jsonObject.get("graphlmfile");
		JSONObject intst = (JSONObject) jsonObject.get("IntSt");
		String node = (String) intst.get("node");

		ab = name.split("/");
		space = new StateSpace(f.getCanonicalPath() + "/data/" + ab[2] + ".xml");

		// Reading the array
		JSONArray JSlistNodes = (JSONArray) intst.get("listNodes");

		// Printing all the values
		n = space.getGraph().returnNode(node);

		for (Object a : JSlistNodes) {
			Node aux = space.getGraph().returnNode(a.toString());
			listNodes.add(aux);
		}

		try {
			instate = new State(n, listNodes);
		} catch (NullPointerException e) {
			return null;
		}

		toReturn[0] = space;
		toReturn[1] = instate;
		return toReturn;
	}
}
