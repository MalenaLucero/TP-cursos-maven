package base.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import base.DAO.TeacherDAO;
import base.controller.TeacherController;
import base.model.Teacher;
import base.util.InputUtil;
import base.util.PrintUtil;
import base.util.StringUtil;

public class TeacherMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU DE DOCENTES ------");
		System.out.println("1. Listar docentes");
		System.out.println("2. Buscar docente por ID");
		System.out.println("3. Buscar docente por nombre y apellido");
		System.out.println("4. Buscar docente por similitud");
		System.out.println("5. Ver perfil completo de docente");
		System.out.println("6. Listar materias por docente");
		System.out.println("7. Agregar docente");
		System.out.println("8. Modificar docente");
		System.out.println("9. Eliminar docente");
		System.out.println("10. Exportar recibo de sueldo de docente");
		System.out.println("0. Volver al menu principal");
		return InputUtil.inputIntMenuOption(sc);
	}
	
	public static void chooseMenuOption(Scanner sc, Connection connection, int option) throws SQLException, ParseException {
		switch(option) {
		case 1:
			TeacherController.findAll(connection);
			break;
		case 2:
			int id = InputUtil.inputInt(sc, "Ingrese ID del docente:");
			TeacherController.findById(connection, id);
			break;
		case 3:
			String name = InputUtil.inputLine(sc, "Ingrese nombre:");
			String lastname = InputUtil.inputLine(sc, "Ingrese apellido:");
			TeacherController.findByLastname(connection, name, lastname);
			break;
		case 4:
			String searchString = InputUtil.inputSingleWord(sc, "Ingrese palabra:");
			TeacherController.findBySimilarity(connection, searchString);
			break;
		case 5:
			int profile = InputUtil.inputInt(sc, "Ingrese ID del docente:");
			TeacherController.findCompleteProfile(connection, profile);
			break;
		case 6:
			int idCourses = InputUtil.inputInt(sc, "Ingrese ID del docente:");
			TeacherController.findCoursesByTeacherId(connection, idCourses);
			break;
		case 7:
			Teacher teacher = createTeacher(sc);
			TeacherController.insert(connection, teacher);
			break;
		case 8:
			Teacher editTeacher = editTeacher(sc, connection);
			TeacherController.update(connection, editTeacher);
			break;
		case 9:
			int deleteId = InputUtil.inputInt(sc, "Ingrese ID del docente:");
			TeacherController.delete(connection, deleteId);
			break;
		case 10:
			int teacherPaycheckId = InputUtil.inputInt(sc, "Ingrese ID del docente:");
			TeacherController.exportPaycheck(connection, teacherPaycheckId);
			break;
		default:
			PrintUtil.invalidOptionMessage();
			break;
		}
	}
	
	private static Teacher createTeacher(Scanner sc) throws ParseException {
		String name = InputUtil.inputLine(sc, "Ingrese nombre");
		String lastname = InputUtil.inputLine(sc, "Ingrese apellido:");
		String alternative_name1 = null;
		if(Helper.confirmOptionalField(sc, "primer nombre alternativo")) {
			alternative_name1 = InputUtil.inputLine(sc, "Ingrese primer nombre alternativo:");
		}
		String alternative_name2 = null;
		if(Helper.confirmOptionalField(sc, "segundo nombre alternativo")) {
			alternative_name2 = InputUtil.inputLine(sc, "Ingrese segundo nombre alternativo:");
		}
		String description = null;
		if(Helper.confirmOptionalField(sc, "descripcion")) {
			description = InputUtil.inputLine(sc, "Ingrese descripcion:");
		}
		String image = null;
		if(Helper.confirmOptionalField(sc, "url de imagen")) {
			image = InputUtil.inputLine(sc, "Ingrese url de la imagen:");
		}
		Date birthdate = null;
		if(Helper.confirmOptionalField(sc, "fecha de nacimiento")) {
			birthdate = InputUtil.inputDate(sc, "Ingrese fecha de nacimiento", "yyyy-MM-dd");
		}
		return new Teacher(name, lastname, alternative_name1, alternative_name2, description, image, birthdate);
	}
	
	private static Teacher editTeacher(Scanner sc, Connection connection) throws SQLException, ParseException {
		int id = InputUtil.inputInt(sc, "Ingrese ID del docente a editar:");
		Teacher teacher = TeacherDAO.findById(connection, id);
		if(Helper.confirmEditMessage(sc, "nombre", teacher.getName())) {
			String name = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setName(name);
		}
		if(Helper.confirmEditMessage(sc, "apellido", teacher.getLastname())) {
			String lastname = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setLastname(lastname);
		}
		if(Helper.confirmEditMessage(sc, "primer nombre alternativo", teacher.getAlternativeName1())) {
			String alternative_name1 = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setAlternativeName1(alternative_name1);
		}
		if(Helper.confirmEditMessage(sc, "segundo nombre alternativo", teacher.getAlternativeName2())) {
			String alternative_name2 = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setAlternativeName2(alternative_name2);
		}
		if(Helper.confirmEditMessage(sc, "descripcion", teacher.getDescription())) {
			String description = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setDescription(description);
		}
		if(Helper.confirmEditMessage(sc, "url de imagen", teacher.getImage())) {
			String image = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			teacher.setImage(image);
		}
		if(Helper.confirmEditMessage(sc, "fecha de nacimiento", StringUtil.dateToString(teacher.getBirthdate()))) {
			Date birthdate = InputUtil.inputDate(sc, "Ingrese fecha de nacimiento", "yyyy-MM-dd");
			teacher.setBirthdate(birthdate);
		}
		return teacher;
	}
}
