package base.model;

import base.util.StringUtil;

public class Student {
	private int id;
	private String name;
	private String lastname;
	private String alternativeName;
	
	public Student(String name, String lastname) {
		this.name = name;
		this.lastname = lastname;
	}
	
	public Student(String name, String lastname, String alternativeName) {
		this.name = name;
		this.lastname = lastname;
		this.alternativeName = alternativeName;
	}
	
	public Student(int id, String name, String lastname, String alternative_name) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.alternativeName = alternative_name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(StringUtil.isBlank(name)) {
			System.out.println("Nombre invalido");
		} else {
			this.name = name;
		}
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		if(StringUtil.isBlank(lastname)) {
			System.out.println("Apellido invalido");
		} else {
			this.lastname = lastname;
		}
	}

	public String getAlternativeName() {
		return alternativeName;
	}

	public void setAlternative_name(String alternativeName) {
		this.alternativeName = alternativeName;
	}
	
	public String getNameAndLastnameNoSpace() {
		return String.format("%s%s", name, lastname);
	}
	
	public String getFullName() {
		if(alternativeName != null) {
			return String.format("%s %s (%s)", name, lastname, alternativeName);
		} else {
			return String.format("%s %s", name, lastname);
		}
	}
	
	public String toString() {
		if(StringUtil.isBlank(alternativeName)) {
			return String.format("ID: %s - Alumno: %s %s", id, name, lastname);
		} else {
			return String.format("ID: %s - Alumno: %s %s (%s)", id, name, lastname, alternativeName);
		}
	}
}
