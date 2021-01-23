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

public class GradeDAO {
	private static final String SELECT = "SELECT i.*, a.nombre, a.apellido, a.nombre_alternativo, " +
										"c.nombre AS nombre_curso, c.id_catedra " + 
										"FROM inscripcion i, alumno a, curso c " + 
										"WHERE i.id_alumno = a.id AND i.id_curso = c.id ";
	
	public static Map<String, Object> getByStudentAndCourse(Connection connection, int id_student, int id_course) throws SQLException {
		String string = SELECT + "AND i.id_alumno = ? AND i.id_curso = ?";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id_student);
		query.setInt(2, id_course);
		ResultSet res = query.executeQuery();
		Map<String, Object> data = null;
		if(res.next()) {
			data = createDataMap(res);
		}
		return data;
	}
	
	public static List<Map<String, Object>> getByStudent(Connection connection, int id_student) throws SQLException {
		String string = SELECT + "AND i.id_alumno = ?";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id_student);
		ResultSet res = query.executeQuery();
		return generateMapList(res);
	}
	
	public static List<Map<String, Object>> findByStudentIdAndYear(Connection connection, int id_student, int year) throws SQLException {
		String string = SELECT + "AND i.id_alumno = ? AND i.ciclo_lectivo = ?";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id_student);
		query.setInt(2, year);
		ResultSet res = query.executeQuery();
		return generateMapList(res);
	}
	
	public static List<Map<String, Object>> getByCourse(Connection connection, int id_course) throws SQLException {
		String string = SELECT + "AND i.id_curso = ?";
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id_course);
		ResultSet res = query.executeQuery();
		return generateMapList(res);
	}
	
	public static List<Map<String, Object>> getOverallBestAverage(Connection connection) throws SQLException {
		String string = SELECT + "AND promedio = (SELECT MAX(promedio) FROM inscripcion) "
								+ "AND i.ciclo_lectivo = 2020"; 
		PreparedStatement query = connection.prepareStatement(string);
		ResultSet res = query.executeQuery();
		return generateMapList(res);
	}
	
	public static List<Map<String, Object>> getBestAverageByCourse(Connection connection, int id_course) throws SQLException {
		String string = SELECT + "AND promedio = (SELECT MAX(promedio) FROM inscripcion WHERE id_curso = ?) "
								+ "AND i.ciclo_lectivo = 2020 AND i.id_curso = ?"; 
		PreparedStatement query = connection.prepareStatement(string);
		query.setInt(1, id_course);
		query.setInt(2, id_course);
		ResultSet res = query.executeQuery();
		return generateMapList(res);
	}
	
	private static List<Map<String, Object>> generateMapList(ResultSet res) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		while(res.next()) {
			Map<String, Object> data = createDataMap(res);
			list.add(data);
		}
		return list;
	}
	
	private static Map<String, Object> createDataMap(ResultSet res) throws SQLException{
		Map<String, Object> data = new HashMap<String, Object>();
		Student student = new Student(res.getInt("id_alumno"), res.getString("nombre"), res.getString("apellido"), res.getString("nombre_alternativo"));
		Course course = new Course(res.getInt("id_curso"), res.getString("nombre_curso"), res.getInt("id_catedra"));
		Enrollment enrollment = new Enrollment(res.getInt("id"), res.getInt("id_curso"), res.getInt("id_alumno"),
				res.getString("estado_inscripcion"), res.getInt("id_docente"), res.getString("comision"), res.getInt("nota1"),
				res.getInt("nota2"), res.getInt("promedio"), res.getString("estado_cursada"), res.getInt("ciclo_lectivo"));
		data.put("student", student);
		data.put("course", course);
		data.put("enrollment", enrollment);
		return data;
	}
}
