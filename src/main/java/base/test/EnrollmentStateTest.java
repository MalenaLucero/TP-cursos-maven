package base.test;

import java.sql.Connection;
import java.sql.SQLException;

import base.controller.EnrollmentStateController;
import base.enums.EnrollmentState;
import base.util.PrintUtil;

public class EnrollmentStateTest {
	public static void testStateChange(Connection connection) throws SQLException {
		PrintUtil.printMessage("Pruebas en editar estado de inscripcion");
		EnrollmentStateController.changeByEnrollment(14, connection, EnrollmentState.cancelado);
		EnrollmentStateController.changeByEnrollment(14, connection, EnrollmentState.activo);
		EnrollmentStateController.changeByStudentId(connection, 1, EnrollmentState.cancelado);
		EnrollmentStateController.changeByStudentId(connection, 1, EnrollmentState.activo);
		EnrollmentStateController.changeByCourseIdAndDivision(connection, 1, "A", EnrollmentState.cancelado);
		EnrollmentStateController.changeByCourseIdAndDivision(connection, 1, "A", EnrollmentState.activo);
		EnrollmentStateController.changeByCourseId(connection, 1, EnrollmentState.cancelado);
		EnrollmentStateController.changeByCourseId(connection, 1, EnrollmentState.activo);
	}
}
