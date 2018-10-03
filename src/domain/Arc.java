package domain;

public class Arc {

	private String id;
	private String source;
	private String target;

	public Arc(String id, String source, String target) {
		this.id = id;
		this.source = source;
		this.target = target;
	}

	public String getID() {
		return id;
	}

	public String getSource() {
		return source;
	}

	public String getTarget() {
		return target;
	}
}
