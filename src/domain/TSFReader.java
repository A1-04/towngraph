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

	static LinkedList<Arc> parseXMLarcs(String filename) throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

		return handler.getArcs();
	}

	public static void parseJSON(StateSpace space, State first, String filename, TSFGraph g)
			throws IOException, ParseException, ParserConfigurationException, SAXException {
		JSONParser parser = new JSONParser();
		File f = new File(".");
		String[] ab = new String[3];
		try {
			Object object = parser.parse(new FileReader(f.getCanonicalPath() + "/data/" + filename + ".json"));

			// convert Object to JSONObject
			JSONObject jsonObject = (JSONObject) object;
			String name = (String) jsonObject.get("graphlmfile");
			JSONObject intst = (JSONObject) jsonObject.get("IntSt");
			String node = (String) intst.get("node");

			ab = name.split("/");
			g = new TSFGraph(f.getCanonicalPath() + "/data/" + ab[2] + ".xml");

			// Reading the array
			JSONArray listNodes = (JSONArray) intst.get("listNodes");
			String id = (String) intst.get("id");

			// Printing all the values
			System.out.println("Name: " + name);
			System.out.println("Node: " + node);
			System.out.print("Nodes:");

			for (Object a : listNodes) {
				System.out.print("\t" + a.toString() + "\n");
			}
			System.out.println("Id: " + id);

		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		}
	}
}
