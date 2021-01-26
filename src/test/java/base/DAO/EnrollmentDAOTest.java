package base.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import base.controller.EnrollmentController;
import base.model.Enrollment;

public class EnrollmentDAOTest {
	@Test
	public void insertInvalidEnrollmentTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		List<Enrollment> totalBefore = EnrollmentDAO.getAll(connection);
		Enrollment enrollment = new Enrollment(4, 1);
		EnrollmentController.insert(connection, enrollment);
		List<Enrollment> totalAfter = EnrollmentDAO.getAll(connection);
		Assert.assertEquals(totalBefore.size(), totalAfter.size());
	}
	
	@Test
	public void insertValidEnrollmentTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		List<Enrollment> totalBefore = EnrollmentDAO.getAll(connection);
		Enrollment enrollment = new Enrollment(1, 3);
		EnrollmentController.insert(connection, enrollment);
		List<Enrollment> totalAfter = EnrollmentDAO.getAll(connection);
		Assert.assertEquals(totalBefore.size() + 1, totalAfter.size());
		Enrollment findEnrollment = EnrollmentDAO.findByCourseAndStudent(connection, 1, 3);
		int res = EnrollmentDAO.delete(findEnrollment.getId(), connection);
		Assert.assertTrue(res == 1);
	}
}
