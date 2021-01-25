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
	public void insertAndDeleteCourseTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course course = new Course("test");
		int id = CourseDAO.insert(course, connection);
		if(id != 0) {
			int deleteRes = CourseDAO.delete(id, connection);
			Assert.assertEquals(1, deleteRes);
		} else {
			Assert.assertTrue("The element was not inserted", id != 0);
		}
	}
	
	@Test
	public void updateCourseTest() throws ClassNotFoundException, SQLException {
		Connection connection = AdminDB.obtenerConexion();
		Course newCourse = new Course("test");
		int id = CourseDAO.insert(newCourse, connection);
		if(id != 0) {
			Course course = CourseDAO.findById(connection, id);
			course.setCatedra(1);
			int res = CourseDAO.update(connection, course);
			if(res == 1) {
				int deleteRes = CourseDAO.delete(id, connection);
				Assert.assertEquals(1, deleteRes);
			} else {
				Assert.assertTrue("The element was not updated", res != 1);
			}
		} else {
			Assert.assertTrue("The element was not inserted", id != 0);
		}
	}
}
