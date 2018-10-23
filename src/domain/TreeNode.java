package domain;

import java.util.ArrayList;
import java.util.Random;

public class TreeNode {
	private Node parent;
	private State currentState;
	private int pathcost;
	private ArrayList<State> action;
	private int d;
	private float f; // value that determines the insertion order in the frontier

	public TreeNode() {
		Random rnd = new Random();
		this.f = rnd.nextFloat() * 1000 + 1;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public int getPathcost() {
		return pathcost;
	}

	public void setPathcost(int pathcost) {
		this.pathcost = pathcost;
	}

	public ArrayList<State> getAction() {
		return action;
	}

	public void setAction(ArrayList<State> action) {
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
