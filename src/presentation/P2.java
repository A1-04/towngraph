package presentation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import domain.Frontier;
import domain.Problem;
import domain.State;
import domain.TreeNode;

public class P2 {
	public static void main(String[] args)
			throws IOException, ParserConfigurationException, SAXException, ParseException {
		System.out.println("-----\tTowngraph P2 (v3)\t-----");
		demo();
		listtest();
		stresstest();
		System.out.println("---P2 closed---");
	}

	public static void demo()
			throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException {
		String filename = "";
		Scanner read = new Scanner(System.in);
		System.out.println("\n--Demo started--");
		System.out.print("Insert the json filename: ");
		filename = read.next();

		new Problem(filename);

		System.out.print("-Problem created succesfully.\n");
	}

	public static void listtest() {
		TreeNode n = new TreeNode();
		double t = 0;

		System.out.println("\n--Lists test started--");
		System.out.println("EXAMPLE: Time consumed (with LinkedList, 50000 elements, addition and remove)");
		t = System.currentTimeMillis();
		Frontier f = new Frontier();
		for (int i = 0; i < 50000; i++) {
			f.insert(n);
		}
		for (int i = 0; i < 50000; i++) {
			f.isEmpty();
			f.remove();
		}

		t = System.currentTimeMillis() - t;
		System.out.println(t + " ms");
		System.out.println(
				"\nLinkedList give us the best results among the 3 DS chosen so we chose it (more info in doc).");

	}

	public static void stresstest() {
		TreeNode n = new TreeNode();
		TreeNode n2 = new TreeNode();
		TreeNode n3 = new TreeNode();
		Frontier f = new Frontier();

		System.out.println("\n--Stress test started--");
		System.out.println("Adding elements...");
		while (true) {
			try {
				f.insert(n);
				f.insert(n2);
				f.insert(n3);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static boolean isGoal(State s) {
		if (s.getN_list().isEmpty()) {
			return true;
		}
		return false;
	}
}
