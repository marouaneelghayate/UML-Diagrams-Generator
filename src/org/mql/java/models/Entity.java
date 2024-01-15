package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Entity {
	private Class<?> cls;
	private String name, fullName, packageName;
	private Entity superClass;
	private List<Entity> interfaces, componants, aggregates;
	public static final int INTERNAL = 0, EXTERNAL = 1;
	private int scope;
	private String type;

	public Entity(Class<?> cls, boolean isLocal) {
		this(cls);
		if(isLocal) {
			discover();
		}
	}
	public Entity(Class<?> cls) {
		this.cls = cls;
		name = cls.getSimpleName();
		packageName = cls.getPackageName();
		fullName = cls.getName();
		interfaces = new Vector<Entity>();
		componants = new Vector<Entity>();
		aggregates = new Vector<Entity>();
		scope = EXTERNAL;
		if(cls.isAnnotation())
			type = "annotation";
		else if(cls.isInterface())
			type = "interface";
		else
			type = "class";
		
	}
	
	private void discover() {
		scope = INTERNAL;
		
		Class<?> parent = cls.getSuperclass();
		if(parent != null) {
			superClass = new Entity(parent);
		}
		interfaces = Arrays.stream(cls.getInterfaces())
			.map( item -> new Entity(item))
			.toList();
		
		componants = Arrays.stream(cls.getDeclaredClasses())
			.map( item -> new Entity(item))
			.toList();
		
		aggregates = Arrays.stream(cls.getDeclaredFields())
			.filter(item -> !item.getType().isPrimitive())
			.map( item -> new Entity(item.getType()))
			.toList();

	}


	@Override
	public String toString() {
		return cls.toString() ;
	}
	
	public Field[] getFields() {
		return cls.getDeclaredFields();
	}
	
	public Method[] getMethods() {
		return cls.getDeclaredMethods();
	}
	
	public String getType() {
		return type;
	}

	public Entity getSuperClass() {
		return superClass;
	}
	public String getName() {
		return name;
	}
	public String getFullName() {
		return fullName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public List<Entity> getAggregates() {
		return aggregates;
	}
	public List<Entity> getComponants() {
		return componants;
	}
	public List<Entity> getInterfaces() {
		return interfaces;
	}
	public int getScope() {
		return scope;
	}
}
