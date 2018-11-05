package presentation;

import java.util.Hashtable;

import domain.TSFAlgorithm;
import domain.TreeNode;


public class P3 {
	public static void  main(String[]args) {
		String Jname = "";
		int depth = 0;
		String technique = "";
		boolean prunning = false;

		TSFAlgorithm alg = new TSFAlgorithm();
		TreeNode sol = new TreeNode();

		switch(technique) {
		case "BFS":
			if(!prunning) {
				sol = alg.BFS();
			}else {
				sol = alg.prBFS();
			}
			break;
		case "DFS":
			if(!prunning) {
				sol = alg.DFS(depth);
			}else {
				sol = alg.prDFS(depth);
			}
			break;
		case "UCS":
			if(!prunning) {
				sol = alg.UCS();
			}else {
				sol = alg.prUCS();
			}
			break;
		case "IDS":
			if(!prunning) {
				sol = alg.IDS(depth);
			}else {
				sol = alg.prIDS(depth);
			}
			break;
		default:
			System.err.println("This is not a valid option");
		}
	}

}
