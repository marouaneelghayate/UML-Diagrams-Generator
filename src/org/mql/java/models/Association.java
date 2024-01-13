package org.mql.java.models;

public class Association {

	private String type;
	private String start;
	private String end;

	public Association(String type, String start, String end) {
		super();
		this.type = type;
		this.start = start;
		this.end = end;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
}
