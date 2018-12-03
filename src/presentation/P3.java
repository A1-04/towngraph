package presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import domain.Problem;
import domain.TSFAlgorithm;
import domain.TreeNode;

public class P3 {
	public static void main(String[] args) throws IOException {
		System.out.println("-----\tTowngraph P4 (v4.2beta)\t-----");
		int success = 0;
		do {
			success = select_strategy();
		} while (success == 0);

		return;
	}

	public static int select_strategy() throws IOException {
		Scanner readt = new Scanner(System.in);
		Scanner readp = new Scanner(System.in);
		Scanner read = new Scanner(System.in);
		String Jname = "";
		int depth = 0, inc_prof = 0;
		String technique = "";
		String aux = "";
		boolean pruning = false;
		Problem p = new Problem();
		int[] n_generated = new int[1];

		LinkedList<TreeNode> sol = new LinkedList<>();

		System.out.print("\n-- Insert the json filename: ");
		Jname = read.nextLine();
		try {
			p = new Problem(Jname);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			System.out.println(e.getMessage());
			return 0;
		}

		System.out.println("\n-- Select an strategy to run the algorithm --");
		System.out.print("Type the strategy (BFS, DFS, DLS, UCS, IDS, GS or A*): ");
		technique = readt.next();
		while (!technique.equalsIgnoreCase("BFS") && !technique.equalsIgnoreCase("DFS")
				&& !technique.equalsIgnoreCase("DLS") && !technique.equalsIgnoreCase("UCS")
				&& !technique.equalsIgnoreCase("IDS") && !technique.equalsIgnoreCase("GS")
				&& !technique.equalsIgnoreCase("A*")) {
			System.out.print("\nThat is not a valid technique. Try again: ");
			technique = readt.next();
		}

		technique = technique.toUpperCase();
		System.out.println("\n-- Tell me the maximum depth:");
		try {
			depth = readt.nextInt();
			while (depth < 0) {
				System.out.print("Please insert a valid number: ");
				depth = readt.nextInt();
			}

		} catch (Exception e) {
			System.out.println("ERROR: You must insert a number. Restarting...");
			return 0;
		}

		System.out.println("\n-- Now tell me the increment of depth:");
		try {
			inc_prof = readt.nextInt();
			while (inc_prof < 0 || inc_prof > depth) {
				System.out.print(
						"The increment of depth cannot be lower than zero or higher than the maximum depth. Try again: ");
				inc_prof = readt.nextInt();
			}

		} catch (Exception e) {
			System.out.println("ERROR: You must insert a number. Restarting...");
			return 0;
		}

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
			System.out.println("\n-- You choose " + technique
					+ " with pruning. Running the algorithm... \t Maximum depth: " + depth + " --");
		} else {
			System.out.println("\n-- You choose " + technique
					+ " without pruning. Running the algorithm... \t Maximum depth: " + depth + " --");
		}

		long startTime = System.currentTimeMillis();
		sol = TSFAlgorithm.search(p, technique, depth, inc_prof, pruning, n_generated);
		long endTime = System.currentTimeMillis() - startTime;
		toFile(sol, p, endTime, technique, n_generated, inc_prof);

		return 1;
	}

	public static void toFile(LinkedList<TreeNode> solution, Problem p, long endTime, String technique,
			int[] n_generated, int inc_prof) {
		System.out.println("\n-- Writing to an output file... --");
		File f = new File("output.txt");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write("-- Solution of the algorithm:");
			fw.write(System.lineSeparator());
			fw.write(System.lineSeparator());

			if (solution == null) {
				fw.write("There is no solution for this problem.");
			} else {
				fw.write("Strategy: " + technique);
				fw.write(System.lineSeparator());
				fw.write("Generated nodes: " + n_generated[0]);
				fw.write(System.lineSeparator());
				fw.write("Depth: " + solution.get(0).getD());
				fw.write(System.lineSeparator());
				fw.write(("Increment of Depth (Default): " + inc_prof));
				fw.write(System.lineSeparator());
				fw.write("Cost: " + solution.get(0).getPathcost());
				fw.write(System.lineSeparator());
				fw.write("Time: " + endTime + " ms");
				fw.write(System.lineSeparator());
				fw.write(System.lineSeparator());
				int a = 1;
				for (int i = solution.size() - 2; i >= 0; i--) {
					fw.write((a++) + ". " + solution.get(i).getAction()  + "\t("+ p.getSpace().getGraph().returnArc((solution.get(i).getParent().getCurrentState().getActualNode().getID() + " " + solution.get(i).getCurrentState().getActualNode().getID())).getName() +")");
					fw.write(System.lineSeparator());
					fw.write("--> Cost: " + solution.get(i).getPathcost());
					fw.write(System.lineSeparator());
				}

			}

			fw.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n-- Operation finished. Program closed.--");
	}

}
