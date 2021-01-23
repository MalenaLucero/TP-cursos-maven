package base.model;

import java.util.Date;

public class Teacher {
	private int id;
	private String name;
	private String lastname;
	private String alternativeName1;
	private String alternativeName2;
	private String description;
	private String image;
	private Date birthdate;
	
	public Teacher(String name, String lastname) {
		this.name = name;
		this.lastname = lastname;
	}
	
	public Teacher(String name, String lastname, String alternativeName1, String alternativeName2,
					String description, String image, Date birthdate) {
		this.name = name;
		this.lastname = lastname;
		this.alternativeName1 = alternativeName1;
		this.alternativeName2 = alternativeName2;
		this.description = description;
		this.image = image;
		this.birthdate = birthdate;
	}
	
	public Teacher(int id, String name, String lastname, String alternativeName1,
					String alternativeName2, String description, String image, Date birthdate) {
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.alternativeName1 = alternativeName1;
		this.alternativeName2 = alternativeName2;
		this.description = description;
		this.image = image;
		this.birthdate = birthdate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAlternativeName1() {
		return alternativeName1;
	}

	public void setAlternativeName1(String alternativeName1) {
		this.alternativeName1 = alternativeName1;
	}

	public String getAlternativeName2() {
		return alternativeName2;
	}

	public void setAlternativeName2(String alternativeName2) {
		this.alternativeName2 = alternativeName2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public Date getBirthdate() {
		 return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getNameAndLastnameNoSpace() {
		return String.format("%s%s", name, lastname);
	}
	
	public String getFullName() {
		if(alternativeName1 != null && alternativeName2 != null) {
			return String.format("%s %s (%s - %s)", name, lastname, alternativeName1, alternativeName2);
		} else {
			return String.format("%s %s (%s)", name, lastname, alternativeName1);
		}
	}
	
	public String toString() {
		return String.format("ID: %s - Docente: %s %s (%s)", id, name, lastname, alternativeName1);
	}
	
	public String toStringBirthday() {
		return String.format("Docente: %s %s - Fecha de cumpleaños: %s", name, lastname, birthdate);
	}
}
