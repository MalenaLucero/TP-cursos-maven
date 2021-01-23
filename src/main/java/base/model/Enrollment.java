package base.model;

import base.enums.CourseState;
import base.enums.Division;
import base.enums.EnrollmentState;
import base.util.ValidationUtil;

public class Enrollment {
	private int id;
	private int idCourse;
	private int idStudent;
	private EnrollmentState enrollmentState;
	private int idTeacher;
	private Division division;
	private int grade1;
	private int grade2;
	private int averageGrade;
	private CourseState courseState;
	private int year;
	
	public Enrollment(int idCourse, int idStudent) {
		this.idCourse = idCourse;
		this.idStudent = idStudent;
		this.enrollmentState = EnrollmentState.activo;
		this.division = Division.A;
		this.courseState = CourseState.cursando;
		this.year = 2021;
	}
	
	public Enrollment(int idCourse, String division, int year) {
		this.idCourse = idCourse;
		this.division = Division.valueOf(division);
		this.year = year;
	}
	
	public Enrollment(int idCourse, int idStudent, String enrollmentState, int idTeacher,
			String division, int grade1, int grade2, String courseState, int year) {
		this.idCourse = idCourse;
		this.idStudent = idStudent;
		this.enrollmentState = EnrollmentState.valueOf(enrollmentState);
		this.idTeacher = idTeacher;
		this.division = Division.valueOf(division);
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.averageGrade = calculateAverageGrade();
		this.courseState = CourseState.valueOf(courseState);
		this.year = year;
	}
	
	public Enrollment(int id, int idCourse, int idStudent, String enrollmentState, int idTeacher,
			String division, int grade1, int grade2, int averageGrade, String courseState, int year) {
		this.id = id;
		this.idCourse = idCourse;
		this.idStudent = idStudent;
		this.enrollmentState = EnrollmentState.valueOf(enrollmentState);
		this.idTeacher = idTeacher;
		this.division = Division.valueOf(division);
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.averageGrade = averageGrade;
		this.courseState = CourseState.valueOf(courseState);
		this.year = year;
	}

	public int getId() {
		return id;
	}

	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public int getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public int getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(int averageGrade) {
		if(ValidationUtil.isGradeValid(averageGrade)) {
			this.averageGrade = averageGrade;
			if(this.averageGrade > 5) {
				setCourseState(CourseState.aprobado.getValue());
			} else {
				setCourseState(CourseState.desaprobado.getValue());
			}
		}
	}

	public String getCourseState() {
		return courseState.getValue();
	}

	public void setCourseState(String courseState) {
		this.courseState = CourseState.valueOf(courseState);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getEnrollmentState() {
		return enrollmentState.getValue();
	}

	public void setEnrollmentState(String enrollmentState) {
		this.enrollmentState = EnrollmentState.valueOf(enrollmentState);
	}

	public int getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getDivision() {
		return division.getValue();
	}

	public void setDivision(String division) {
		this.division = Division.valueOf(division);
	}

	public int getGrade1() {
		return grade1;
	}

	public void setGrade1(int grade1) {
		if(ValidationUtil.isGradeValid(grade1)) {
			this.grade1 = grade1;
			setAverageGrade(calculateAverageGrade());
		}
	}

	public int getGrade2() {
		return grade2;
	}

	public void setGrade2(int grade2) {
		if(ValidationUtil.isGradeValid(grade2)) {
			this.grade2 = grade2;
			setAverageGrade(calculateAverageGrade());
		}
	}

	public String toString() {
		if(this.averageGrade == 0) {
			return String.format("ID: %s - ID alumno: %s - ID curso: %s", id, idStudent, idCourse);
		} else {
			return String.format("ID: %s - ID alumno: %s - ID curso: %s - Estado: %s - Notas: %s, %s, %s - Año: %s",
								id, idStudent, idCourse, courseState, grade1, grade2, averageGrade, year);
		}
	}
	
	private int calculateAverageGrade() {
		return (grade1 + grade2) / 2;
	}
}
