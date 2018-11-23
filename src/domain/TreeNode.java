package domain;

import java.util.Random;

public class TreeNode implements Comparable<TreeNode> {
	private TreeNode parent;
	private State currentState;
	private float pathcost;
	private String action;
	private int d;
	private float f; // value that determines the insertion order in the frontier

	public TreeNode() {

	}

	public TreeNode(TreeNode parent, State currentState, int d, int pathcost, String strategy) {
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
		} else if (strategy.equals("GS")) {
			// f = h(currentState);
		} else if (strategy.equals("A*")) {
			// f = h(currentState) + this.pathcost;
		} else {
			this.f = rnd.nextFloat() * 1000 + 1;
		}
	}

	public TreeNode(State currentState, TreeNode actualN, String strategy, float pathcost) {
		this.currentState = currentState;
		this.parent = actualN;
		this.d = actualN.getD() + 1;
		this.pathcost += this.getParent().getPathcost() + pathcost;
		Random rnd = new Random();
		if (strategy.equals("BFS")) {
			this.f = d;
		} else if (strategy.equals("DFS") || strategy.equals("DLS") || strategy.equals("IDS")) {
			this.f = -d;
		} else if (strategy.equals("UCS")) {
			this.f = this.pathcost;
		} else if (strategy.equals("GS")) {
			f = h(currentState);
		} else if (strategy.equals("A*")) {
			f = h(currentState) + this.pathcost;
		} else {
			this.f = rnd.nextFloat() * 1000 + 1;
		}
	}

	private float h(State s) {
		return distance(parent.currentState.getActualNode(), s.getActualNode());
	}

	private float distance(Node node1, Node node2) {
		float lng1 = Float.parseFloat(node1.getX());
		float lng2 = Float.parseFloat(node2.getX());
		float lat1 = Float.parseFloat(node1.getY());
		float lat2 = Float.parseFloat(node1.getY());
		float earth_radius = 6371009;

		double phi1 = Math.toRadians(lat1);
		double phi2 = Math.toRadians(lat2);
		double d_phi = phi2 - phi1;

		double theta1 = Math.toRadians(lng1);
		double theta2 = Math.toRadians(lng2);
		double d_theta = theta2 - theta1;

		double h = Math.pow(Math.sin(d_phi / 2), 2)
				+ Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin(d_theta / 2), 2);
		h = Math.min(1.0, h);

		double arc = 2 * Math.asin(Math.sqrt(h));

		// return distance in units of earth_radius
		double dist = arc * earth_radius;
		return (float) dist;
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

	public void setPathcost(float pathcost) {
		this.pathcost += this.getParent().getPathcost() + pathcost;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
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

	@Override
	public int compareTo(TreeNode t) {
		if (this.getF() > t.getF()) {
			return 1;
		} else if (this.getF() < t.getF()) {
			return -1;
		} else
			return 0;
	}

}
