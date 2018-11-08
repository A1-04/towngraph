package domain;

import java.util.LinkedList;
import java.util.Random;

public class TreeNode {
	private TreeNode parent;
	private State currentState;
	private float pathcost;
	private LinkedList<State> action;
	private int d;
	private float f; // value that determines the insertion order in the frontier

	public TreeNode() {

	}

	public TreeNode(TreeNode parent, State currentState, float pathcost, int d, float f, String strategy) {
		super();
		this.parent = parent;
		this.currentState = currentState;
		this.pathcost = pathcost;
		this.d = d;
		Random rnd = new Random();
		if (strategy.equals("BFS")) {
			this.f = d;
		} else if (strategy.equals("DFS") || strategy.equals("DLS") || strategy.equals("IDS")) {
			this.f = -d;
		} else if (strategy.equals("UCS")) {
			this.f = pathcost;
		} else {
			this.f = rnd.nextFloat() * 1000 + 1;
		}
	}

	public TreeNode(State currentState, TreeNode actualN, int depth, String strategy, float pathcost) {
		this.currentState = currentState;
		this.parent = actualN;
		this.pathcost = pathcost;
		this.d = depth;
		Random rnd = new Random();
		if (strategy.equals("BFS")) {
			this.f = d;
		} else if (strategy.equals("DFS") || strategy.equals("DLS") || strategy.equals("IDS")) {
			this.f = -d;
		} else if (strategy.equals("UCS")) {
			this.f = pathcost;
		} else {
			this.f = rnd.nextFloat() * 1000 + 1;
		}
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public float getPathcost() {
		return pathcost;
	}

	public void setPathcost(int pathcost) {
		this.pathcost = pathcost;
	}

	public LinkedList<State> getAction() {
		return action;
	}

	public void setAction(LinkedList<State> action) {
		this.action = action;
	}

	public int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}

	public float getF() {
		return f;
	}

	public void setF(int f) {
		this.f = f;
	}

}
