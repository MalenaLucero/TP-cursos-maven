package base.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.model.Course;
import base.model.Enrollment;
import base.model.Student;

public class StudentDAO {
	public static List<Student> getAll(Connection connection) throws SQLException {
		String listString = "SELECT * FROM alumno";
		PreparedStatement listStudents = connection.prepareStatement(listString);
		ResultSet res = listStudents.executeQuery();
		return generateStudentList(res);
	}
	
	public static List<Student> getRandom(Connection connection, int quantity) throws SQLException {
		String listString = "SELECT * FROM alumno ORDER BY RAND() LIMIT ?";
		PreparedStatement listStudents = connection.prepareStatement(listString);
		listStudents.setInt(1, quantity);
		ResultSet res = listStudents.executeQuery();
		return generateStudentList(res);
	}
	
	public static Student findById(Connection connection, int id) throws SQLException {
		String listString = "SELECT * from alumno WHERE id= ?";
		PreparedStatement findStudent = connection.prepareStatement(listString);
		findStudent.setInt(1, id);
		ResultSet res = findStudent.executeQuery();
		return res.next() ? generateStudent(res) : null;
	}
	
	public static List<Student> findByNameAndLastname(Connection connection, String name, String lastname) throws SQLException {
		String listString = "SELECT * from alumno WHERE nombre = ? AND apellido = ?";
		PreparedStatement findStudents = connection.prepareStatement(listString);
		findStudents.setString(1, name);
		findStudents.setString(2, lastname);
		ResultSet res = findStudents.executeQuery();
		return generateStudentList(res);
	}
	
	public static List<Student> findByLastname(Connection connection, String lastname) throws SQLException {
		String listString = "SELECT * from alumno WHERE apellido = ?";
		PreparedStatement findStudents = connection.prepareStatement(listString);
		findStudents.setString(1, lastname);
		ResultSet res = findStudents.executeQuery();
		return generateStudentList(res);
	}
	
	public static List<Student> findBySimilarity(Connection connection, String searchString) throws SQLException {
		String string = "SELECT * FROM alumno WHERE nombre LIKE ? or apellido LIKE ? " + 
						"OR nombre_alternativo LIKE ?";
		PreparedStatement listTeachers = connection.prepareStatement(string);
		listTeachers.setString(1, "%" + searchString + "%");
		listTeachers.setString(2, "%" + searchString + "%");
		listTeachers.setString(3, "%" + searchString + "%");
		ResultSet res = listTeachers.executeQuery();
		return generateStudentList(res);
	}
	
	public static double getOverallAverage(Connection connection, int id) throws SQLException {
		String string = "SELECT AVG(promedio) AS promedio_general " + 
						"FROM inscripcion WHERE id_alumno = ?";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id);
		ResultSet res = query.executeQuery();
		return res.next() ? res.getDouble("promedio_general") : 0;
	}
	
	public static List<Map<String, Object>> getCourses(Connection connection, int id) throws SQLException {
		String string = "SELECT c.nombre, i.* FROM inscripcion i, curso c " + 
						"WHERE i.id_curso = c.id AND i.id_alumno = ? ORDER BY i.ciclo_lectivo DESC";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id);
		ResultSet res = query.executeQuery();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while(res.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			Course course = new Course(res.getString("nombre"));
			Enrollment enrollment = generateEnrollment(res);
			map.put("course", course);
			map.put("enrollment", enrollment);
			list.add(map);
		}
		return list;
	}
	
	public static int insert(Student student, Connection connection) throws SQLException {
		String insertString = "INSERT INTO alumno (nombre, apellido, nombre_alternativo) values (?, ?, ?)";
		PreparedStatement addStudent = connection.prepareStatement(insertString);
		addStudent.setString(1, student.getName());
		addStudent.setString(2, student.getLastname());
		addStudent.setString(3, student.getAlternativeName());
		return addStudent.executeUpdate();
	}
	
	public static int update(Connection connection, Student student) throws SQLException {
		String editString = "UPDATE alumno SET nombre = ?, apellido = ?, nombre_alternativo = ? WHERE id = ?";
		PreparedStatement editAlumno = connection.prepareStatement(editString);
		editAlumno.setString(1, student.getName());
		editAlumno.setString(2, student.getLastname());
		editAlumno.setString(3, student.getAlternativeName());
		editAlumno.setInt(4, student.getId());
		return editAlumno.executeUpdate();
	}
	
	public static int delete(int id, Connection connection) throws SQLException {
		String deleteString = "DELETE FROM alumno WHERE id = ?";
		PreparedStatement deleteStudent = connection.prepareStatement(deleteString);
		deleteStudent.setInt(1, id);
		return deleteStudent.executeUpdate();
	}
	
	private static Student generateStudent(ResultSet res) throws SQLException {
		return new Student(res.getInt("id"), res.getString("nombre"), res.getString("apellido"), res.getString("nombre_alternativo"));
	}
	
	private static List<Student> generateStudentList(ResultSet res) throws SQLException{
		List<Student> students = new ArrayList<Student>();
		while(res.next()) {
			students.add(generateStudent(res));
		}
		return students;
	}
	
	private static Enrollment generateEnrollment(ResultSet res) throws SQLException {
		return new Enrollment(res.getInt("id"), res.getInt("id_curso"), res.getInt("id_alumno"),
				res.getString("estado_inscripcion"), res.getInt("id_docente"), res.getString("comision"), res.getInt("nota1"),
				res.getInt("nota2"), res.getInt("promedio"), res.getString("estado_cursada"), res.getInt("ciclo_lectivo"));
	}
}
