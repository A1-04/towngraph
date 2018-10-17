package domain;

import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Problem {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		Scanner read = new Scanner(System.in);
		String filename = "";
		System.out.println("---P2 testing---\n");
		System.out.print("Insert the json filename: ");
		filename = read.next();

		StateSpace space = new StateSpace();
		State first = new State();
		readJSON(space, first, filename);
	}

	public static boolean isGoal(State s) {
		if (s.getN_list().isEmpty()) {
			return true;
		}
		return false;
	}

	public static void readJSON(StateSpace space, State first, String filename) {

	}
}
