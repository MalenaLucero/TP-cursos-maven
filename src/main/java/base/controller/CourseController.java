package base.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import base.DAO.CourseDAO;
import base.model.Course;
import base.util.ResponseUtil;

public class CourseController{
	public static void findAll(Connection connection) throws SQLException {
		System.out.println("Listado de cursos");
		List<Course> courses = CourseDAO.getAll(connection);
		printCourses(courses);
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		System.out.println("Buscar curso por ID");
		Course course = CourseDAO.findById(connection, id);
		printCourse(course);
	}
	
	public static void findByName(Connection connection, String name) throws SQLException {
		System.out.println("Buscar curso por nombre");
		Course course = CourseDAO.findByName(connection, name);
		printCourse(course);
	}
	
	public static void insert(Connection connection, Course course) throws SQLException {
		System.out.println("Agregar curso");
		if(CourseDAO.findByName(connection, course.getName()) == null) {
			int res = CourseDAO.insert(course, connection);
			ResponseUtil.addMessage(res);
		} else {
			System.err.println("El curso ya existe");
		}
	}

	public static void update(Connection connection, Course course) throws SQLException {
		System.out.println("Editar curso");
		int res = CourseDAO.update(connection, course);
		ResponseUtil.editMessage(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		System.out.println("Eliminar curso");
		int res = CourseDAO.delete(id, connection);
		ResponseUtil.deleteMessage(res);
	}
	
	private static void printCourse(Course course) {
		if(course == null) {
			System.err.println("No se encontro el curso");
		} else {
			System.out.println(course);
		}
	}
	
	private static void printCourses(List<Course> courses) {
		if(courses.size() == 0) {
			System.err.println("No se encontraron cursos");
		} else {
			for(Course course: courses) {
				System.out.println(course);
			}
		}
	}
}
