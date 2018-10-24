package domain;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public class Problem {
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, ParseException {
		System.out.println("---P2 testing---\n");
		demo();
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
			TSFReader.parseJSON(space, first, filename, g);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

	public static void stresstest() {
		TreeNode n = new TreeNode();

		double t = 0;
		System.out.println("\n--Stress test started--");
		System.out.println("Time consumed (with ArrayList)");
		// System.out.println("Time consumed (with LinkedList)");
		// System.out.println("Time consumed (with Vector)");
		t = System.currentTimeMillis();
		Frontier f = new Frontier();
		for (int i = 0; i < 1000000; i++) {
			f.insert(n);
			// System.out.println("Adding...");
		}
		for (int i = 0; i < 1000000; i++) {
			// System.out.println("Removing...");
			f.isEmpty();
			f.remove();
		}

		t = System.currentTimeMillis() - t;
		System.out.println(t / 1000 + " s");
		System.out.println("\nLinkedList give us the best results among the 3 DS chosen.");

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
}
