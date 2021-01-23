package base.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import base.DAO.GradeDAO;
import base.DAO.StudentDAO;
import base.IO.ExportGradesBooklet;
import base.model.Course;
import base.model.Enrollment;
import base.model.Student;
import base.util.PrintUtil;

public class GradeController {
	public static void findByStudentIdAndCourseId(Connection connection, int id_student, int id_course) throws SQLException {
		PrintUtil.printMessage("Notas del alumno con ID " + id_student + " en el curso con ID " + id_course);
		Map<String, Object> gradesMap = GradeDAO.getByStudentAndCourse(connection, id_student, id_course);
		printStudent(gradesMap);
		printCourse(gradesMap);
		printGrades(gradesMap);
	}
	
	public static void findByStudentId(Connection connection, int id_student) throws SQLException {
		PrintUtil.printMessage("Notas del alumno con ID " + id_student);
		List<Map<String, Object>> gradesList = GradeDAO.getByStudent(connection, id_student);
		printStudent(gradesList.get(0));
		for(Map<String, Object> gradesMap: gradesList) {
			printCourse(gradesMap);
			printGrades(gradesMap);
		}
	}
	
	public static void exportGradesBooklet(Connection connection, int studentId, int year) throws SQLException {
		Student student = StudentDAO.findById(connection, studentId);
		if(student != null) {
			List<Map<String, Object>> gradesMap = GradeDAO.findByStudentIdAndYear(connection, studentId, year);
			try {
				ExportGradesBooklet.generate(gradesMap, year);
			} catch (IOException e) {
				PrintUtil.printExceptionMessage("Error al exportar el boletin", e.getMessage());
			}
		} else {
			System.err.println("Alumno no encontrado");
		}
	}
	
	public static void findByCourseId(Connection connection, int id_course) throws SQLException {
		PrintUtil.printMessage("Notas del curso con ID " + id_course);
		List<Map<String, Object>> gradesList = GradeDAO.getByCourse(connection, id_course);
		printCourse(gradesList.get(0));
		for(Map<String, Object> gradesMap: gradesList) {
			printStudent(gradesMap);
			printGrades(gradesMap);
		}
	}
	
	public static void findOverallBestAverage(Connection connection) throws SQLException {
		PrintUtil.printMessage("Mejores promedios del año");
		List<Map<String, Object>> gradesList = GradeDAO.getOverallBestAverage(connection);
		for(Map<String, Object> gradesMap: gradesList) {
			printStudent(gradesMap);
			printCourse(gradesMap);
			printGrades(gradesMap);
		}
	}
	
	public static void findBestAverageByCourseId(Connection connection, int id_course) throws SQLException {
		PrintUtil.printMessage("Mejores promedios del curso con ID " + id_course + " año 2020");
		List<Map<String, Object>> gradesList = GradeDAO.getBestAverageByCourse(connection, id_course);
		printCourse(gradesList.get(0));
		for(Map<String, Object> gradesMap: gradesList) {
			printStudent(gradesMap);
			printGrades(gradesMap);
		}
	}
	
	private static void printStudent(Map<String, Object> gradesMap) {
		Student student = (Student) gradesMap.get("student");
		System.out.println(student);
	}
	
	private static void printCourse(Map<String, Object> gradesMap) {
		Course course = (Course) gradesMap.get("course");
		System.out.println("Materia: " + course.getName());
	}
	
	private static void printGrades(Map<String, Object> gradesMap) {
		Enrollment enrollment = (Enrollment) gradesMap.get("enrollment");
		System.out.print("Nota 1: " + enrollment.getGrade1());
		System.out.print(", Nota 2: " + enrollment.getGrade2());
		System.out.print(", Promedio: " + enrollment.getAverageGrade());
		System.out.print(", Estado de la cursada: " + enrollment.getCourseState());
		System.out.println();
	}
}
