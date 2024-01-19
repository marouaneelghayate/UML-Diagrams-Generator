package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;


public class Entity {
	private Class<?> cls;
	public static final int INTERNAL = 0, EXTERNAL = 1;
	private int scope;
	private String type;

	public Entity(Class<?> cls, boolean isLocal) {
		this(cls);
		
		if(cls.isArray()) {
			this.cls  = cls.getComponentType();
		}
		if(isLocal) {
			scope = INTERNAL;
		}
	}
	public Entity(Class<?> cls) {
		this.cls = cls;
		if(cls.isAnnotation())
			type = "annotation";
		else if(cls.isInterface())
			type = "interface";
		else
			type = "class";
		
		scope = EXTERNAL;
	}
	
	
	public Class<?> getCls() {
		return cls;
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
		if(cls.getSuperclass() != null) {
			return new Entity(cls.getSuperclass());
		}
		return null;
	}
	public String getName() {
		return cls.getSimpleName();
	}
	public String getFullName() {
		return cls.getName();
	}
	
	public String getPackageName() {
		return cls.getPackageName();
	}
	
	public Entity[] getAggregates() {
		return Arrays.stream(cls.getDeclaredFields())
				.filter(item -> !item.getType().isPrimitive())
				.map( item -> new Entity(item.getType()))
				.toArray(Entity[]::new);
	}
	public Entity[] getComponants() {
		return Arrays.stream(cls.getDeclaredClasses())
				.map( item -> new Entity(item))
				.toArray(Entity[]::new);
	}
	public Entity[] getInterfaces() {
		return Arrays.stream(cls.getInterfaces())
				.map(item -> new Entity(item))
				.toArray(Entity[]::new);
	}
	public int getScope() {
		return scope;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Entity)obj).getFullName().equals(getFullName());
	}
}
