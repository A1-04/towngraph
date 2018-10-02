package Domain;

public class Node {

	private double y;
	private double x;
	private int id;

	public Node(double y, double x, int id) {
		this.y = y;
		this.x = x;
		this.id = id;
	}

	public int getID() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
