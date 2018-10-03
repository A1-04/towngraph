package presentation;

import java.io.IOException;
import java.util.ArrayList;

import domain.Node;
import domain.TSFGraph;

public class Principal {
	public static void main(String[] args) throws IOException {
		TSFGraph g = new TSFGraph("/home/mandre/repos/towngraph/data/Montiel.graphml.xml");
		System.out.println(g.belongNode("4053186049"));
		String[] holo = g.positionNode("4053186049");
		for (int i = 0; i < 2; i++)
			System.out.println(holo[i]);
		ArrayList<Node> a = g.adjacentNode("3096311519");
		for (Node i : a) {
			System.out.println(i.getID());
		}
	}
}
