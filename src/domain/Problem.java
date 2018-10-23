package domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class Problem {
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, ParseException {
		Scanner read = new Scanner(System.in);
		String filename = "";
		System.out.println("---P2 testing---\n");
		stresstest();
	}

	public static void demo() {
		String filename = "";
		Scanner read = new Scanner(System.in);
		System.out.println("--Demo started--");
		System.out.print("Insert the json filename: ");

		filename = read.next();

		TSFGraph g = new TSFGraph();
		StateSpace space = new StateSpace();
		State first = new State();
		try {
			readJSON(space, first, filename, g);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

	public static void stresstest() {
		TreeNode n = new TreeNode();

		double t = 0;
		System.out.println("Time consumed (with ArrayList)");
		t = System.nanoTime();
		Frontier f = new Frontier();
		f.insert(n);
		f.isEmpty();
		f.remove();
		t = System.nanoTime() - t;
		System.out.println(t + " ns");

		/*
		 * System.out.println("LISTS TEST:"); f.insert(n); f.insert(n2); f.insert(n3);
		 * 
		 * System.out.println(f.remove().getF()); System.out.println(f.remove().getF());
		 * System.out.println(f.remove().getF());
		 */

	}

	public static boolean isGoal(State s) {
		if (s.getN_list().isEmpty()) {
			return true;
		}
		return false;
	}

	public static void readJSON(StateSpace space, State first, String filename, TSFGraph g)
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
