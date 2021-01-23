package base.test;

import java.sql.Connection;
import java.sql.SQLException;

import base.DAO.EnrollmentDAO;
import base.controller.EnrollmentController;
import base.model.Enrollment;
import base.util.PrintUtil;

public class EnrollmentTest {
	public static void testCrud(Connection connection) throws SQLException {
		PrintUtil.printMessage("Pruebas en CRUD de inscripciones");
		EnrollmentController.findAll(connection);
		EnrollmentController.findById(connection, 14);
		EnrollmentController.findByCourseIdAndStudentId(connection, 1, 62);
		Enrollment addEnrollment = new Enrollment(1, 3);
		EnrollmentController.insert(connection, addEnrollment);
		Enrollment editEnrollment = EnrollmentDAO.findByCourseAndStudent(connection, addEnrollment.getIdCourse(), addEnrollment.getIdStudent());
		editEnrollment.setGrade1(8);
		EnrollmentController.update(connection, editEnrollment);
		EnrollmentController.delete(connection, editEnrollment.getId());
	}
	
	public static void testStudentsSearch(Connection connection) throws SQLException {
		EnrollmentController.findStudentsByCourseId(connection, 1);
		EnrollmentController.findStudentsByCourseIdAndDivision(connection, 1, "A");
	}
}
