package org.mql.java.models;

public class EntityLink implements Link{

	private int type;
	private String start;
	private String end;
	public static final int ASSOCIATION = 0, EXTENSION = 1, IMPLEMENTATION = 2,AGGREGATION = 3,  COMPOSITION = 4;

	public EntityLink(int type, String start, String end) {
		super();
		this.type = type;
		this.start = start;
		this.end = end;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
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
