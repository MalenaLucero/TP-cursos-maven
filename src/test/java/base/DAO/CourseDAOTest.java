package base.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import base.model.Course;

public class CourseDAOTest {
	@Test
    public void listAllCourses() throws ClassNotFoundException, SQLException{
		Connection connection = AdminDB.obtenerConexion();
		List<Course> courses = CourseDAO.getAll(connection);
        Assert.assertTrue(courses.size() > 0);
    }
	
	@Test
	public void insertCourse() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = new Course("test");
		int res = CourseDAO.insert(course, connection);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void updateCourse() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = CourseDAO.findByName(connection, "test");
		course.setCatedra(1);
		int res = CourseDAO.update(connection, course);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void deleteCourse() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = CourseDAO.findByName(connection, "test");
		int res = CourseDAO.delete(course.getId(), connection);
		Assert.assertEquals(1, res);
	}
}
