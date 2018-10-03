package presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

import domain.Node;
import domain.TSFGraph;

public class Principal {
	public static void main(String[] args) throws IOException {
		Scanner read = new Scanner(System.in);
		System.out.println("-----\tTowngraph P1 (v0.2)\t-----");
		System.out.println();
		System.out.println("Enter the name of the town:");
		File f = new File(".");
		String filename = read.nextLine();
		TSFGraph g = new TSFGraph(f.getCanonicalPath() + "/data/" + filename + ".graphml.xml");
		P1(g);
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
		}

	}
}
