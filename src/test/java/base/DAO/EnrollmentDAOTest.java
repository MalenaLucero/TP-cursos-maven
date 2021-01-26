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
	public void insertEnrollmentTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		List<Enrollment> totalBefore = EnrollmentDAO.getAll(connection);
		Enrollment enrollment = new Enrollment(4, 1);
		EnrollmentController.insert(connection, enrollment);
		List<Enrollment> totalAfter = EnrollmentDAO.getAll(connection);
		Assert.assertEquals(totalBefore.size(), totalAfter.size());
	}
}
