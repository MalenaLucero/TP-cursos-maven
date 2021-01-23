package base.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import base.DAO.StudentDAO;
import base.model.Course;
import base.model.Enrollment;
import base.model.Student;
import base.util.PrintUtil;
import base.util.ResponseUtil;

public class StudentController{
	public static void findAll(Connection connection) throws SQLException {
		System.out.println("Listado de alumnos");
		List<Student> students = StudentDAO.getAll(connection);
		printStudents(students);
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		System.out.println("Buscar alumno por ID");
		Student student = StudentDAO.findById(connection, id);
		printStudent(student);
	}
	
	public static void findByNameAndLastname(Connection connection, String name, String lastname) throws SQLException {
		System.out.println("Buscar alumno por nombre y apellido");
		List<Student> students = StudentDAO.findByNameAndLastname(connection, name, lastname);
		printStudents(students);
	}
	
	public static void findByLastname(Connection connection, String lastname) throws SQLException {
		System.out.println("Buscar alumno por apellido");
		List<Student> students = StudentDAO.findByLastname(connection, lastname);
		printStudents(students);
	}
	
	public static void findBySimilarity(Connection connection, String searchString) throws SQLException {
		System.out.println("Buscar alumno que contenga " + searchString);
		List<Student> students = StudentDAO.findBySimilarity(connection, searchString);
		printStudents(students);
	}
	
	public static void findCompleteProfile(Connection connection, int id) throws SQLException {
		System.out.println("Perfil completo del alumno");
		Student student = StudentDAO.findById(connection, id);
		System.out.println("ID: " + student.getId());
		System.out.println("Nombre: " + student.getName());
		System.out.println("Apellido: " + student.getLastname());
		PrintUtil.printIfNotBlank("Nombre alternativo: ", student.getAlternativeName());
		double average = StudentDAO.getOverallAverage(connection, id);
		System.out.println("Promedio general: " + average);
		List<Map<String, Object>> courseList = StudentDAO.getCourses(connection, id);
		System.out.println("Materias cursadas:");
		for(Map<String, Object> map: courseList) {
			Course course = (Course)map.get("course");
			Enrollment enrollment = (Enrollment)map.get("enrollment");
			System.out.print(course.getName());
			System.out.print(" Division: " + enrollment.getDivision());
			System.out.print(" Estado: " + enrollment.getCourseState());
			System.out.println(" Año: " + enrollment.getYear());
		}
	}
	
	public static void insert(Connection connection, Student student) throws SQLException {
		System.out.println("Agregar alumno");
		int res = StudentDAO.insert(student, connection);
		ResponseUtil.addMessage(res);
	}

	public static void update(Connection connection, Student student) throws SQLException {
		System.out.println("Editar alumno");
		int res = StudentDAO.update(connection, student);
		ResponseUtil.editMessage(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		System.out.println("Eliminar alumno");
		int res = StudentDAO.delete(id, connection);
		ResponseUtil.deleteMessage(res);
	}
	
	private static void printStudent(Student student) {
		if(student == null) {
			System.err.println("No se encontro el alumno");
		} else {
			System.out.println(student);
		}
	}
	
	private static void printStudents(List<Student> students) {
		if(students.size() == 0) {
			System.err.println("No se encontraron alumnos");
		} else {
			for(Student student: students) {
				System.out.println(student);
			}
		}
	}
}
