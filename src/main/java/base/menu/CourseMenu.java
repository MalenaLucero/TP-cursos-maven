package base.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.DAO.CourseDAO;
import base.controller.CourseController;
import base.model.Course;
import base.util.InputUtil;
import base.util.PrintUtil;

public class CourseMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU DE CURSOS ------");
		System.out.println("1. Listar cursos");
		System.out.println("2. Buscar curso por ID");
		System.out.println("3. Buscar curso por nombre");
		System.out.println("4. Agregar curso");
		System.out.println("5. Editar curso");
		System.out.println("6. Eliminar curso");
		System.out.println("0. Volver al menu principal");
		return InputUtil.inputIntMenuOption(sc);
	}
	
	public static void chooseMenuOption(Scanner sc, Connection connection, int option) throws SQLException {
		switch(option) {
		case 1:
			CourseController.findAll(connection);
			break;
		case 2:
			int id = InputUtil.inputInt(sc, "Ingrese ID del curso:");
			CourseController.findById(connection, id);
			break;
		case 3:
			String name = InputUtil.inputLine(sc, "Ingrese nombre del curso:");
			CourseController.findByName(connection, name);
			break;
		case 4:
			Course course = createCourse(sc);
			CourseController.insert(connection, course);
			break;
		case 5:
			Course editCourse = editCourse(sc, connection);
			CourseController.update(connection, editCourse);
			break;
		case 6:
			int deleteId = InputUtil.inputInt(sc, "Ingrese ID del curso:");
			CourseController.delete(connection, deleteId);
			break;
		default:
			PrintUtil.invalidOptionMessage();
			break;
		}
	}

	private static Course createCourse(Scanner sc) {
		String name = InputUtil.inputLine(sc, "Ingrese el nombre del curso");
		int idCatedra = InputUtil.inputInt(sc, "Ingresar ID de catedra (o 0 si se desconoce)");
		return new Course(name, idCatedra);
	}
	
	private static Course editCourse(Scanner sc, Connection connection) throws SQLException {
		int id = InputUtil.inputInt(sc, "Ingrese ID del curso a editar:");
		Course course = CourseDAO.findById(connection, id);
		if(Helper.confirmEditMessage(sc, "nombre", course.getName())) {
			String name = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			course.setName(name);
		}
		if(Helper.confirmEditMessage(sc, "catedra", Integer.toString(course.getCatedra()))) {
			int catedra = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			course.setCatedra(catedra);
		}
		return course;
	}
}
