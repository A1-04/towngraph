package presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import domain.Node;
import domain.Problem;
import domain.TSFAlgorithm;
import domain.TreeNode;
import domain.GPXWriter;

public class P3 {
	public static void main(String[] args) throws IOException {
		System.out.println("-----\tTowngraph v5.0\t-----");
		int success = -1;
		do {
			success = menu();
		} while (success == -1);
		return;
	}

	public static int menu() throws IOException {
		int[] n_generated = new int[1];
		LinkedList<TreeNode> sol = new LinkedList<>();

		Problem p = readJSON();
		while (p == null)
			p = readJSON();

		String technique = selectStrategy();

		int depth = selectDepth();
		while (depth == -1)
			depth = selectDepth();

		int inc_prof = selectIncDepth(technique, depth);
		while (inc_prof == -1)
			inc_prof = selectIncDepth(technique, depth);

		int heuristic = checkHeuristic(technique);
		while (heuristic == -1)
			heuristic = checkHeuristic(technique);

		boolean pruning = checkPruning(technique, depth);

		long startTime = System.currentTimeMillis();
		sol = TSFAlgorithm.search(p, technique, depth, inc_prof, pruning, n_generated, heuristic);
		long endTime = System.currentTimeMillis() - startTime;
		if (!sol.isEmpty()) {
			toFile(sol, p, endTime, technique, n_generated[0], inc_prof, heuristic);
			if (GPXWriter.writePath(sol, sol.get(sol.size() - 1), technique, n_generated[0], sol.get(0).getD(),
					sol.get(0).getPathcost()) == -1) {
				System.out.println("-- There was an error creating the GPX file.");
			}
		} else
			System.out.print("\n-- There is no solution for this problem.--\n-- Program closed.--");
		return 0;
	}

	private static Problem readJSON() {
		Scanner read = new Scanner(System.in);
		Problem p;
		System.out.print("\n-- Insert the json filename: ");
		String Jname = read.nextLine();
		try {
			p = new Problem(Jname);
		} catch (IOException | ParseException | ParserConfigurationException | SAXException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return p;
	}

	private static String selectStrategy() {
		Scanner read = new Scanner(System.in);
		System.out.println("\n-- Select an strategy to run the algorithm --");
		System.out.print("Type the strategy (BFS, DFS, DLS, UCS, IDS, GS or A*): ");
		String technique = read.next();
		while (!technique.equalsIgnoreCase("BFS") && !technique.equalsIgnoreCase("DFS")
				&& !technique.equalsIgnoreCase("DLS") && !technique.equalsIgnoreCase("UCS")
				&& !technique.equalsIgnoreCase("IDS") && !technique.equalsIgnoreCase("GS")
				&& !technique.equalsIgnoreCase("A*")) {
			System.out.print("\nThat is not a valid technique. Try again: ");
			technique = read.next();
		}
		technique = technique.toUpperCase();
		return technique;
	}

	private static int selectDepth() {
		Scanner read = new Scanner(System.in);
		int depth = -1;
		System.out.println("\n-- Tell me the maximum depth:");
		try {
			depth = read.nextInt();
			while (depth <= 0) {
				System.out.print("Please insert a valid number: ");
				depth = read.nextInt();
			}

		} catch (Exception e) {
			System.out.println("ERROR: You must insert a number. Restarting...");
			return -1;
		}
		return depth;
	}

	private static int selectIncDepth(String technique, int depth) {
		Scanner read = new Scanner(System.in);
		int inc_prof = -1;
		if (technique.equals("IDS")) {
			System.out.print("\n-- Now tell me the increment of depth:");
			try {
				inc_prof = read.nextInt();
				while (inc_prof < 0 || inc_prof > depth) {
					System.out.print(
							"The increment of depth cannot be lower than zero or higher than the maximum depth. Try again: ");
					inc_prof = read.nextInt();
				}

			} catch (Exception e) {
				System.out.println("ERROR: You must insert a number. Restarting...");
				return -1;
			}
		} else {
			inc_prof = depth;
		}
		return inc_prof;
	}

	private static int checkHeuristic(String technique) {
		Scanner read = new Scanner(System.in);
		int heuristic = -1;
		if (technique.equals("A*")) {
			System.out.print("\n-- Now tell me the heuristic you want to use (0 = basic, 1 = best): ");
			try {
				heuristic = read.nextInt();
				while (heuristic < 0 || heuristic > 1) {
					System.out.print("Please choose a valid heuristic: ");
					heuristic = read.nextInt();
				}

			} catch (Exception e) {
				System.out.println("ERROR: You must insert a valid number. Restarting...");
				return -1;
			}
		} else {
			return -2;
		}
		return heuristic;
	}

	private static boolean checkPruning(String technique, int depth) {
		boolean pruning = false;
		Scanner read = new Scanner(System.in);
		System.out.print("\nYou type " + technique + ". Do you want pruning? (y for yes and n for no):  ");
		String aux = read.next();
		while (!aux.equalsIgnoreCase("y") && !aux.equalsIgnoreCase("n")) {
			System.out.print("\nThat is not a valid answer. Try again: ");
			aux = read.next();
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
		return pruning;
	}

	public static void toFile(LinkedList<TreeNode> solution, Problem p, long endTime, String technique, int n_generated,
			int inc_prof, int heuristic) {
		LinkedList<Node> rem = new LinkedList<Node>();
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
				fw.write("Generated nodes: " + n_generated);
				fw.write(System.lineSeparator());
				fw.write("Depth: " + solution.get(0).getD());
				fw.write(System.lineSeparator());
				if (technique.equals("IDS")) {
					fw.write(("Increment of Depth: " + inc_prof));
					fw.write(System.lineSeparator());
				} else if (technique.equals("A*")) {
					fw.write(("Heuristic: H" + heuristic));
					fw.write(System.lineSeparator());
				}
				fw.write("Cost: " + solution.get(0).getPathcost());
				fw.write(System.lineSeparator());
				fw.write("Time: " + endTime + " ms");
				fw.write(System.lineSeparator());
				fw.write(System.lineSeparator());
				int a = 1;
				for (int i = solution.size() - 2; i >= 0; i--) {
					fw.write((a++) + ". " + solution.get(i).getAction() + "\t("
							+ p.getSpace().getGraph()
									.returnArc((solution.get(i).getParent().getCurrentState().getActualNode().getID()
											+ " " + solution.get(i).getCurrentState().getActualNode().getID()))
									.getName()
							+ ")");
					fw.write(System.lineSeparator());
					fw.write("Nodes remaining for visit: [ | ");
					rem = solution.get(i).getCurrentState().getN_list();
					for (int z = 0; z < rem.size(); z++) {
						fw.write(rem.get(z).getID() + " | ");
					}
					fw.write("]");
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
