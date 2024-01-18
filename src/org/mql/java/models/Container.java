package org.mql.java.models;

import java.util.List;

public interface Container {
	public void addPackage(Package p);
	public List<Package> getPackages();
}
