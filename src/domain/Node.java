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

	public Node() {

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

	public void setID(String id) {
		this.id = id;
	}

	public void setY(String y) {
		this.y = y;
	}

	public void setX(String x) {
		this.x = x;
	}
}
