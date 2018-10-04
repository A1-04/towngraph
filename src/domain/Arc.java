package domain;

public class Arc {

	private String id;
	private String source;
	private String target;
	private String name;
	private String distance;

	public Arc(String id, String source, String target, String name, String distance) {
		this.id = id;
		this.source = source;
		this.target = target;
		this.name=name;
		this.distance=distance;
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
	public String getName() {
		return name;
	}
	public String getDistance() {
		return distance;
	}
}
