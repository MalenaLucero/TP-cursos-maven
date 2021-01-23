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
import base.model.Teacher;

public class TeacherDAO {
	public static List<Teacher> getAll(Connection connection) throws SQLException {
		String listString = "SELECT * FROM docente";
		PreparedStatement listTeachers = connection.prepareStatement(listString);
		ResultSet res = listTeachers.executeQuery();
		return generateTeacherList(res);
	}
	
	public static Teacher findById(Connection connection, int id) throws SQLException {
		String listString = "SELECT * FROM docente WHERE id= ?";
		PreparedStatement findTeacher = connection.prepareStatement(listString);
		findTeacher.setInt(1, id);
		ResultSet res = findTeacher.executeQuery();
		return res.next() ? generateTeacher(res) : null;
	}
	
	public static Teacher findByNameLastname(Connection connection, String name, String lastname) throws SQLException {
		String listString = "SELECT * FROM docente WHERE nombre = ? AND apellido = ?";
		PreparedStatement findTeachers = connection.prepareStatement(listString);
		findTeachers.setString(1, name);
		findTeachers.setString(2, lastname);
		ResultSet res = findTeachers.executeQuery();
		return res.next() ? generateTeacher(res) : null;
	}
	
	public static int insert(Teacher teacher, Connection connection) throws SQLException {
		String insertString = "INSERT INTO docente (nombre, apellido, nombre_alternativo1, nombre_alternativo2, " + 
								"descripcion, imagen, fecha_nacimiento) values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement addTeacher = connection.prepareStatement(insertString);
		addTeacher.setString(1, teacher.getName());
		addTeacher.setString(2, teacher.getLastname());
		addTeacher.setString(3, teacher.getAlternativeName1());
		addTeacher.setString(4, teacher.getAlternativeName2());
		addTeacher.setString(5, teacher.getDescription());
		addTeacher.setString(6, teacher.getImage());
		Helper.setPossibleNullDate(addTeacher, 7, teacher.getBirthdate());
		return addTeacher.executeUpdate();
	}
	
	public static int update(Connection connection, Teacher teacher) throws SQLException {
		String editString = "UPDATE docente SET nombre = ?, apellido = ?, nombre_alternativo1 = ?,"
							+ "nombre_alternativo2 = ?, descripcion = ?, imagen = ?, "
							+ "fecha_nacimiento = ? WHERE id = ?";
		PreparedStatement editTeacher = connection.prepareStatement(editString);
		editTeacher.setString(1, teacher.getName());
		editTeacher.setString(2, teacher.getLastname());
		editTeacher.setString(3, teacher.getAlternativeName1());
		editTeacher.setString(4, teacher.getAlternativeName2());
		editTeacher.setString(5, teacher.getDescription());
		editTeacher.setString(6, teacher.getImage());
		Helper.setPossibleNullDate(editTeacher, 7, teacher.getBirthdate());
		editTeacher.setInt(8, teacher.getId());
		return editTeacher.executeUpdate();
	}
	
	public static int delete(int id, Connection connection) throws SQLException {
		String deleteString = "DELETE FROM docente WHERE id = ?";
		PreparedStatement deleteEnrollment = connection.prepareStatement(deleteString);
		deleteEnrollment.setInt(1, id);
		return deleteEnrollment.executeUpdate();
	}
	
	public static List<Map<String, Object>> getCoursesByTeacher(Connection connection, int id_teacher) throws SQLException {
		String coursesString = "SELECT d.*, c.nombre AS nombre_curso, i.id_curso, i.comision, i.ciclo_lectivo " + 
							"FROM inscripcion i, curso c, docente d " + 
							"WHERE i.id_docente = d.id and i.id_curso = c.id and i.id_docente = ? " + 
							"AND i.ciclo_lectivo = 2020 GROUP BY i.id_curso ORDER BY c.nombre";
		PreparedStatement query = connection.prepareStatement(coursesString);
		query.setInt(1, id_teacher);
		ResultSet res = query.executeQuery();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while(res.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			Teacher teacher = generateTeacher(res);
			Course course = new Course(res.getString("nombre_curso"));
			Enrollment enrollment = new Enrollment(res.getInt("id_curso"),
					res.getString("comision"), res.getInt("ciclo_lectivo"));
			map.put("teacher", teacher);
			map.put("course", course);
			map.put("enrollment", enrollment);
			list.add(map);
		}
		return list;
	}
	
	public static List<Teacher> getBySimilarity(Connection connection, String searchString) throws SQLException {
		String string = "SELECT * FROM docente WHERE nombre LIKE ? or apellido LIKE ? " + 
						"OR nombre_alternativo1 LIKE ? OR nombre_alternativo2 LIKE ?";
		PreparedStatement listTeachers = connection.prepareStatement(string);
		listTeachers.setString(1, "%" + searchString + "%");
		listTeachers.setString(2, "%" + searchString + "%");
		listTeachers.setString(3, "%" + searchString + "%");
		listTeachers.setString(4, "%" + searchString + "%");
		ResultSet res = listTeachers.executeQuery();
		return generateTeacherList(res);
	}
	
	public static List<Teacher> getMonthsBirthdays(Connection connection, int month) throws SQLException{
		String string = "SELECT * FROM docente WHERE MONTH(fecha_nacimiento) = ? ORDER BY DAY(fecha_nacimiento)";
		PreparedStatement listTeachers = connection.prepareStatement(string);
		listTeachers.setInt(1, month);
		ResultSet res = listTeachers.executeQuery();
		return generateTeacherList(res);
	}
	
	private static Teacher generateTeacher(ResultSet res) throws SQLException {
		Teacher teacher = new Teacher(res.getInt("id"), res.getString("nombre"), res.getString("apellido"),
				res.getString("nombre_alternativo1"), res.getString("nombre_alternativo2"),
				res.getString("descripcion"), res.getString("imagen"), res.getDate("fecha_nacimiento"));
		return teacher;
	}
	
	private static List<Teacher> generateTeacherList(ResultSet res) throws SQLException {
		List<Teacher> teachers = new ArrayList<Teacher>();
		while(res.next()) {
			teachers.add(generateTeacher(res));
		}
		return teachers;
	}
}
