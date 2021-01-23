package base.test;

import java.sql.Connection;
import java.sql.SQLException;

import base.controller.GradeController;

public class GradeTest {
	public static void test(Connection connection) throws SQLException {
		GradeController.findByStudentIdAndCourseId(connection, 1, 1);
		GradeController.findByStudentId(connection, 1);
		GradeController.findByCourseId(connection, 1);
		GradeController.findOverallBestAverage(connection);
		GradeController.findBestAverageByCourseId(connection, 1);
		GradeController.exportGradesBooklet(connection, 1, 2020);
	}
}
