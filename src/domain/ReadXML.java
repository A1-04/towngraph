package domain;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class ReadXML {
	
	static ArrayList<Node> parseXMLnodes(String filename)
			throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

		return handler.getNodes();
	}
	static ArrayList<Arc> parseXMLarcs(String filename)
			throws IOException, ParserConfigurationException, SAXException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser = factory.newSAXParser();
		File file = new File(filename);
		GraphHandler handler = new GraphHandler();
		saxParser.parse(file, handler);

	    return handler.getArcs();
	}
}
