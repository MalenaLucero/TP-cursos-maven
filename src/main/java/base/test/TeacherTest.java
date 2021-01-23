package base.test;

import java.sql.Connection;
import java.sql.SQLException;

import base.DAO.TeacherDAO;
import base.controller.TeacherController;
import base.model.Teacher;
import base.util.PrintUtil;

public class TeacherTest {
	public static void testCrud(Connection connection) throws SQLException {
		PrintUtil.printMessage("Pruebas en el CRUD de docentes");
		TeacherController.findNextBirthday(connection);
		TeacherController.findCurrentMonthBirthdays(connection);
		TeacherController.findAll(connection);
		TeacherController.findById(connection, 3);
		TeacherController.findByLastname(connection, "Yoongi", "Min");
		TeacherController.findCompleteProfile(connection, 3);
		Teacher addTeacher = new Teacher("nombre", "apellido");
		TeacherController.insert(connection, addTeacher);
		Teacher editTeacher = TeacherDAO.findByNameLastname(connection, addTeacher.getName(), addTeacher.getLastname());
		editTeacher.setName("NOMBRE");
		TeacherController.update(connection, editTeacher);
		TeacherController.delete(connection, editTeacher.getId());
		TeacherController.findCoursesByTeacherId(connection, 3);
		TeacherController.findBySimilarity(connection, "민");
		TeacherController.exportPaycheck(connection, 3);
	}
}
