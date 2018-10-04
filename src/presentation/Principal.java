package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import domain.Node;
import domain.TSFGraph;

public class Principal {
	public static void main(String[] args) {
		System.out.println("-----\tTowngraph P1 (v0.2.1)\t-----");
		System.out.println();
		start();
	}

	public static void start() {
		char exit = 0;
		do {
			Scanner read = new Scanner(System.in);
			System.out.println("Enter the name of the town: (or 0 to exit the program)");
			File f = new File(".");
			String filename = read.nextLine();
			if (filename.length() == 1 && filename.charAt(0) == '0') {
				System.out.println("---> Program closed.");
				System.exit(0);
			}
			TSFGraph g = new TSFGraph();
			try {
				g = new TSFGraph(f.getCanonicalPath() + "/data/" + filename + ".graphml.xml");
			} catch (FileNotFoundException e) {
				System.out.println("----- ERROR: File not found, try again.");
				start();
			} catch (IOException e) {
				System.out.print(e.getMessage());
				System.out.println("---> Program closed.");
				System.exit(0);
			}
			P1(g);
			System.out.println("\nPress 0 to exit the program. Press any key to try again.");
			exit = read.next().charAt(0);
		} while (exit != '0');
		System.out.println("--> Program closed.");
	}

	public static void P1(TSFGraph g) {
		Scanner read = new Scanner(System.in);
		boolean belong;
		System.out.println("Insert the node:");
		String node = read.next();
		System.out.print("--> Belong the node to the graph? ");

		belong = g.belongNode(node);
		System.out.println(g.belongNode(node));
		if (belong) {
			System.out.print("--> Location of the node: ");
			String[] coor = g.positionNode(node);
			for (int i = 0; i < 2; i++)
				System.out.print(coor[i] + " ");
			System.out.println();
			System.out.print("--> Adjacent Nodes: ");
			ArrayList<Node> a = g.adjacentNode(node);
			for (Node i : a) {
				System.out.print(i.getID() + " ");
			}
		} else {
			System.out.println("This node does not belong to the graph.");
			System.out.println();
		}

	}
}
