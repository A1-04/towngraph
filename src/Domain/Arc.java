package Domain;

public class Arc {

	private int id;
	private int source;
	private int target;

	public Arc(int id, int source, int target) {
		this.id = id;
		this.source = source;
		this.target = target;
	}

	public int getID() {
		return id;
	}

	public int getSource() {
		return source;
	}

	public int getTarget() {
		return target;
	}
}
