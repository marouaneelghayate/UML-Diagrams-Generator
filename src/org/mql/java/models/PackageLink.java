package org.mql.java.models;

public class PackageLink implements Link{

	private int type;
	private String start;
	private String end;
	public static final int USE = 0, ACCESS = 2, IMPORT = 2;

	public PackageLink(int type, String start, String end) {
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
