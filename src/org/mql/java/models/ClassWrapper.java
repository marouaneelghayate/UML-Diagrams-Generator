package org.mql.java.models;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ClassWrapper {
	private Class<?> cls;
	private String name, fullName, packageName;
	private ClassWrapper superClass;
	private List<ClassWrapper> interfaces,componants, aggregates;
	private String type;

	public ClassWrapper(Class<?> cls) {
		super();
		this.cls = cls;
		name = cls.getSimpleName();
		packageName = cls.getPackageName();
		fullName = cls.getName();
		interfaces = new Vector<ClassWrapper>();
		componants = new Vector<ClassWrapper>();
		aggregates = new Vector<ClassWrapper>();
		if(cls.isAnnotation())
			type = "annotation";
		else if(cls.isInterface())
			type = "interface";
		else
			type = "class";
	}
	
	public void discover() {
		
		Class<?> parent = cls.getSuperclass();
		if(parent != null) {
			superClass = new ClassWrapper(parent);
		}
		interfaces = Arrays.stream(cls.getInterfaces())
			.map( item -> new ClassWrapper(item))
			.toList();
		
		componants = Arrays.stream(cls.getDeclaredClasses())
			.map( item -> new ClassWrapper(item))
			.toList();
		
		aggregates = Arrays.stream(cls.getDeclaredFields())
			.filter(item -> !item.getType().isPrimitive())
			.map( item -> new ClassWrapper(item.getType()))
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

	public ClassWrapper getSuperClass() {
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
	
	public List<ClassWrapper> getAggregates() {
		return aggregates;
	}
	public List<ClassWrapper> getComponants() {
		return componants;
	}
	public List<ClassWrapper> getInterfaces() {
		return interfaces;
	}
}
