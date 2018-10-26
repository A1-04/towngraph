package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;

import domain.Arc;
import domain.TSFGraph;

public class P1 {
	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		System.out.println("-----\tTowngraph P1 (v2.1beta)\t-----");
		System.out.println();
		start();
		System.exit(0);
	}

	public static int start() throws ParserConfigurationException, SAXException {
		char exit = 0;
		do {
			Scanner read = new Scanner(System.in);

			System.out.println("Enter the name of the town: (or 0 to exit the program)");
			File f = new File(".");
			String filename = read.nextLine();
			// long timeIni = System.nanoTime();
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
			test(g /* timeIni */);
			System.out.println("\nPress 0 to exit the program. Press any key to try again.");
			exit = read.next().charAt(0);
		} while (exit != '0');
		System.out.println("--> Program closed.");
		return 0;
	}

	public static void test(TSFGraph g /* long timeIni */) {
		Scanner read = new Scanner(System.in);
		boolean belong;
		// long timeFin = System.nanoTime();
		// System.out.println("\nTime elapsed: " + (timeFin - timeIni) + " ns");
		System.out.println("Insert the node:");
		String node = read.next();
		// long timeAdjI = System.nanoTime();
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
			ArrayList<Arc> a = g.adjacentNode(node);
			for (Arc i : a) {
				System.out.println("\n" + i.getID() + "\t" + i.getName() + "\t" + i.getDistance());
			}
		} else {
			System.out.println("This node does not belong to the graph.");
			System.out.println();
		}
		// long timeAdjF = System.nanoTime();
		// System.out.println("\nTime elapsed: " + (timeAdjF - timeAdjI) + " ns");
	}
}
