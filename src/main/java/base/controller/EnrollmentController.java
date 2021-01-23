package base.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import base.DAO.EnrollmentDAO;
import base.IO.ImportEnrollment;
import base.model.Enrollment;
import base.model.Student;
import base.util.PrintUtil;
import base.util.ResponseUtil;

public class EnrollmentController{
	public static void findAll(Connection connection) throws SQLException {
		System.out.println("Listado de inscripciones");
		List<Enrollment> enrollments = EnrollmentDAO.getAll(connection);
		printEnrollments(enrollments);
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		System.out.println("Buscar inscripcion por ID");
		Enrollment enrollment = EnrollmentDAO.findById(connection, id);
		printEnrollment(enrollment);
	}
	
	public static void findByCourseIdAndStudentId(Connection connection, int id_course, int id_student) throws SQLException {
		System.out.println("Buscar inscripcion por curso y alumno");
		Enrollment enrollment = EnrollmentDAO.findByCourseAndStudent(connection, id_course, id_student);
		printEnrollment(enrollment);
	}
	
	public static void insert(Connection connection, Enrollment enrollment) throws SQLException {
		System.out.println("Agregar inscripcion");
		if (EnrollmentDAO.findByCourseAndStudent(connection, enrollment.getIdCourse(), enrollment.getIdStudent()) != null) {
			System.err.println("El alumno ya esta inscripto");
		} else {
			int res = EnrollmentDAO.insert(enrollment, connection);
			ResponseUtil.addMessage(res);
		}
	}

	public static void update(Connection connection, Enrollment enrollment) throws SQLException {
		System.out.println("Editar inscripcion");
		int res = EnrollmentDAO.update(connection, enrollment);
		ResponseUtil.editMessage(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		System.out.println("Eliminar inscripcion");
		int res = EnrollmentDAO.delete(id, connection);
		ResponseUtil.deleteMessage(res);
	}
	
	public static void findStudentsByCourseId(Connection connection, int id_course) throws SQLException {
		System.out.println("Alumnos del curso con ID " + id_course);
		List<Student> students = EnrollmentDAO.getStudentsByCourse(connection, id_course);
		printStudents(students);
	}
	
	public static void findStudentsByCourseIdAndDivision(Connection connection, int id_course, String division) throws SQLException {
		System.out.println("Alumnos del curso con ID " + id_course + " comision " + division);
		List<Student> students = EnrollmentDAO.getStudentsByCourseAndDivision(connection, id_course, division);
		printStudents(students);
	}
	
	public static void insertImport(Connection connection) throws SQLException {
		try {
			Enrollment enrollment = ImportEnrollment.importData();
			insert(connection, enrollment);
		} catch (IOException e) {
			PrintUtil.printExceptionMessage("Error al importar inscripcion", e.getMessage());
		}
	}
	
	private static void printEnrollment(Enrollment enrollment) {
		if(enrollment == null) {
			System.err.println("No se encontro la inscripcion");
		} else {
			System.out.println(enrollment);
		}
	}
	
	private static void printEnrollments(List<Enrollment> enrollments) {
		if(enrollments.size() == 0) {
			System.err.println("No se encontraron inscripciones");
		} else {
			for(Enrollment enrollment: enrollments) {
				System.out.println(enrollment);
			}
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
