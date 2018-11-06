package presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;

import domain.TSFAlgorithm;
import domain.TreeNode;

public class P3 {
	public static void main(String[] args) {
		System.out.println("-----\tTowngraph P3 (v3.1beta)\t-----");
		String Jname = "";
		select_strategy();
		toFile();
		return;
	}

	public static void select_strategy() {
		Scanner readt = new Scanner(System.in);
		Scanner readp = new Scanner(System.in);
		int depth = 0;
		String technique = "";
		String aux = "";
		boolean pruning = false;

		TSFAlgorithm alg = new TSFAlgorithm();
		TreeNode sol = new TreeNode();
		System.out.println("\n-- Select an strategy to run the algorithm --");
		System.out.print("Type the strategy (BFS, DFS, UCS or IDS): ");
		technique = readt.next();
		while (!technique.equalsIgnoreCase("BFS") && !technique.equalsIgnoreCase("DFS")
				&& !technique.equalsIgnoreCase("UCS") && !technique.equalsIgnoreCase("IDS")) {
			System.out.print("\nThat is not a valid technique. Try again: ");
			technique = readt.next();
		}

		technique = technique.toUpperCase();
		System.out.print("\nYou type " + technique + ". Do you want pruning? (y for yes and n for no):  ");
		aux = readp.next();
		while (!aux.equalsIgnoreCase("y") && !aux.equalsIgnoreCase("n")) {
			if (aux.equalsIgnoreCase("y")) {
				pruning = true;
			} else if (aux.equalsIgnoreCase("n")) {
				pruning = false;
				System.out.println();
			} else {
				System.out.print("\nThat is not a valid answer. Try again: ");
				aux = readp.next();
			}
		}
		if (pruning) {
			System.out.println("\n-- You choose " + technique + " with pruning. Running the algorithm... --");
		} else {
			System.out.println("\n-- You choose " + technique + " without pruning. Running the algorithm... --");
		}

		switch (technique) {
		case "BFS":
			if (!pruning) {
				sol = alg.BFS();
			} else {
				sol = alg.prBFS();
			}
			break;
		case "DFS":
			if (!pruning) {
				sol = alg.DFS(depth);
			} else {
				sol = alg.prDFS(depth);
			}
			break;
		case "UCS":
			if (!pruning) {
				sol = alg.UCS();
			} else {
				sol = alg.prUCS();
			}
			break;
		case "IDS":
			if (!pruning) {
				sol = alg.IDS(depth);
			} else {
				sol = alg.prIDS(depth);
			}
			break;
		}
	}

	public static void toFile(/* sequence of states */) {
		System.out.println("\n-- Writing to an output file... --");
		File f = new File("output.txt");
		try {
			FileWriter fw = new FileWriter(f);
			// fw.write(sequence of states);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n-- Operation finished. Program closed.--");
	}

}
