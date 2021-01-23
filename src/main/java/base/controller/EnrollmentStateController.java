package base.controller;

import java.sql.Connection;
import java.sql.SQLException;

import base.DAO.EnrollmentDAO;
import base.DAO.EnrollmentStateDAO;
import base.enums.EnrollmentState;
import base.model.Enrollment;
import base.util.ResponseUtil;

public class EnrollmentStateController {
	public static void changeByEnrollment(int id, Connection connection, EnrollmentState state) throws SQLException {
		generateMessage(state, "inscripcion con ID " + id);
		Enrollment enrollment = EnrollmentDAO.findById(connection, id);
		if(enrollment == null) {
			System.out.println("Inscripcion no encontrada");
		} else {
			if(enrollment.getEnrollmentState().equals(state.getValue())) {
				System.err.println("La inscripcion ya tiene estado " + state.getValue());
			} else {
				changeEnrollmentState(connection, enrollment, state.getValue());
			} 
		}
	}
	
	public static void changeByStudentId(Connection connection, int id_student, EnrollmentState state) throws SQLException {
		generateMessage(state, "inscripciones del alumno con ID " + id_student);
		int res = EnrollmentStateDAO.changeByStudent(connection, id_student, state.getValue());
		ResponseUtil.numberOfModifiedElements(res);
	}
	
	public static void changeByCourseId(Connection connection, int id_course, EnrollmentState state) throws SQLException {
		generateMessage(state, "inscripciones del curso con ID " + id_course);
		int res = EnrollmentStateDAO.changeByCourse(connection, id_course, state.getValue());
		ResponseUtil.numberOfModifiedElements(res);
	}
	
	public static void changeByCourseIdAndDivision(Connection connection, int id_course, String division, EnrollmentState state) throws SQLException {
		generateMessage(state, "inscripciones del curso con ID " + id_course + " comision " + division);
		int res = EnrollmentStateDAO.changeByCourseAndDivision(connection, id_course, division, state.getValue());
		ResponseUtil.numberOfModifiedElements(res);
	}

	private static void changeEnrollmentState(Connection connection, Enrollment enrollment, String state) throws SQLException {
		enrollment.setEnrollmentState(state);
		int res = EnrollmentDAO.update(connection, enrollment);
		ResponseUtil.editMessage(res);
	}
	
	private static void generateMessage(EnrollmentState state, String message) {
		if(state.getValue().equals(EnrollmentState.activo.getValue())) {
			System.out.println("Activar " + message);
		} else {
			System.out.println("Cancelar " + message);
		}
	}
}
