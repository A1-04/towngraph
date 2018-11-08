package presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import domain.Arc;
import domain.Problem;
import domain.TSFAlgorithm;
import domain.TSFGraph;
import domain.TreeNode;

public class P3 {
	public static void main(String[] args) {
		System.out.println("-----\tTowngraph P3 (v3.1beta)\t-----");
		select_strategy();
		return;
	}

	public static void select_strategy() {
		Scanner readt = new Scanner(System.in);
		Scanner readp = new Scanner(System.in);
		Scanner read = new Scanner(System.in);
		String Jname = "";
		int depth = 999;
		String technique = "";
		String aux = "";
		boolean pruning = false;
		Problem p = new Problem();

		LinkedList<TreeNode> sol = new LinkedList<>();

		System.out.print("\n--Insert the json filename: ");
		Jname = read.next();
		try {
			p = new Problem(Jname);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			System.out.println(e.getMessage());
			select_strategy();
		}

		System.out.println("\n-- Select an strategy to run the algorithm --");
		System.out.print("Type the strategy (BFS, DFS, DLS, UCS or IDS): ");
		technique = readt.next();
		while (!technique.equalsIgnoreCase("BFS") && !technique.equalsIgnoreCase("DFS")
				&& !technique.equalsIgnoreCase("DLS") && !technique.equalsIgnoreCase("UCS")
				&& !technique.equalsIgnoreCase("IDS")) {
			System.out.print("\nThat is not a valid technique. Try again: ");
			technique = readt.next();
		}

		technique = technique.toUpperCase();
		System.out.print("\nYou type " + technique + ". Do you want pruning? (y for yes and n for no):  ");
		aux = readp.next();
		while (!aux.equalsIgnoreCase("y") && !aux.equalsIgnoreCase("n")) {
			System.out.print("\nThat is not a valid answer. Try again: ");
			aux = readp.next();
		}
		if (aux.equalsIgnoreCase("y")) {
			pruning = true;
		} else if (aux.equalsIgnoreCase("n")) {
			pruning = false;
		}

		if (pruning) {
			System.out.println("\n-- You choose " + technique + " with pruning. Running the algorithm... --");
		} else {
			System.out.println("\n-- You choose " + technique + " without pruning. Running the algorithm... --");
		}

		try {
			sol = TSFAlgorithm.search(p, technique, depth, 1, pruning);
			for (TreeNode i : sol) {
				System.out.println(i.getCurrentState().getActualNode().getID());
			}
			System.out.println();
			toFile(sol, p);

		} catch (IOException e) {
			System.out.println(e.getMessage());
			select_strategy();
		}
	}

	public static void toFile(LinkedList<TreeNode> solution, Problem p) {
		float solution_cost = 0;
		System.out.println("\n-- Writing to an output file... --");
		File f = new File("output.txt");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write("Solution to the algorithm");
			fw.write(System.lineSeparator());
			fw.write(System.lineSeparator());
			for (int i = 0; i < solution.size() - 1; i++) {
				fw.write(String.format("Go from node %s to node %s",
						solution.get(i).getCurrentState().getActualNode().getID(),
						solution.get(i + 1).getCurrentState().getActualNode().getID()));
				fw.write(System.lineSeparator());

				solution_cost += solution.get(i).getPathcost() + solution.get(i + 1).getPathcost();
			}
			fw.write(System.lineSeparator());
			fw.write("Cost of the solution: " + solution_cost);
			fw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n-- Operation finished. Program closed.--");
	}

}
