package domain;

public class Node {

	private String y;
	private String x;
	private String id;

	public Node(String y, String x, String id) {
		this.y = y;
		this.x = x;
		this.id = id;
	}

	public String getID() {
		return id;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}
}
