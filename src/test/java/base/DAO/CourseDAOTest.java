package base.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import base.model.Course;

public class CourseDAOTest {
	@Test
	public void findCourseByIdTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = CourseDAO.findById(connection, 1);
		Assert.assertTrue(course != null);
	}
	
	@Test
    public void listAllCoursesTest() throws ClassNotFoundException, SQLException{
		Connection connection = AdminDB.obtenerConexion();
		List<Course> courses = CourseDAO.getAll(connection);
        Assert.assertTrue(courses.size() > 0);
    }
	
	@Test
	public void insertCourseTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = new Course("test");
		int id = CourseDAO.insert(course, connection);
		Assert.assertTrue(id != 0);
		CourseDAO.delete(id, connection);
	}
	
	@Test
	public void updateCourseTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course newCourse = new Course("test");
		int id = CourseDAO.insert(newCourse, connection);
		Course course = CourseDAO.findById(connection, id);
		course.setCatedra(1);
		int res = CourseDAO.update(connection, course);
		Assert.assertTrue(res == 1);
		CourseDAO.delete(id, connection);
	}
	
	@Test
	public void deleteCourseTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = new Course("test");
		int id = CourseDAO.insert(course, connection);
		int res = CourseDAO.delete(id, connection);
		Assert.assertTrue(res == 1);
	}
}
