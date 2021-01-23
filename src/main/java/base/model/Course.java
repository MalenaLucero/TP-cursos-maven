package base.model;

import base.util.StringUtil;

public class Course {
	private int id;
	private String name;
	private int idCatedra;
	
	public Course(String name) {
		setName(name);
	}
	
	public Course(String name, int idCatedra) {
		this.idCatedra = idCatedra;
		setName(name);
	}
	
	public Course(int id, String name, int idCatedra) {
		this.id = id;
		setName(name);
		this.idCatedra = idCatedra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(!StringUtil.isBlank(name)) {
			this.name = name;
		} 
	}
	
	public int getCatedra() {
		return idCatedra;
	}

	public void setCatedra(int idCatedra) {
		this.idCatedra = idCatedra;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return String.format("ID: %s - Curso: %s - ID Catedra: %s", id, name, idCatedra);
	}
}
