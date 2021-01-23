package base.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.controller.EnrollmentStateController;
import base.enums.EnrollmentState;
import base.util.InputUtil;
import base.util.PrintUtil;

public class EnrollmentStateMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU DE ESTADO DE INSCRIPCIONES ------");
		System.out.println("1. Activar inscripcion por ID");
		System.out.println("2. Cancelar inscripcion por ID");
		System.out.println("3. Activar inscripciones por alumno");
		System.out.println("4. Cancelar inscripciones por alumno");
		System.out.println("5. Activar inscripciones por comision");
		System.out.println("6. Cancelar inscripciones por comision");
		System.out.println("7. Activar inscripciones por curso");
		System.out.println("8. Cancelar inscripciones por curso");
		System.out.println("0. Volver al menu principal");
		return InputUtil.inputIntMenuOption(sc);
	}
	
	public static void chooseMenuOption(Scanner sc, Connection connection, int option) throws SQLException {
		switch(option) {
		case 1:
			int id = InputUtil.inputInt(sc, "Ingrese ID de la inscripcion:");
			EnrollmentStateController.changeByEnrollment(id, connection, EnrollmentState.activo);
			break;
		case 2:
			int id_enrollment = InputUtil.inputInt(sc, "Ingrese ID de la inscripcion:");
			EnrollmentStateController.changeByEnrollment(id_enrollment, connection, EnrollmentState.cancelado);
			break;
		case 3:
			int id_student = InputUtil.inputInt(sc, "Ingrese ID del alumno");
			EnrollmentStateController.changeByStudentId(connection, id_student, EnrollmentState.activo);
			break;
		case 4:
			int idStudent = InputUtil.inputInt(sc, "Ingrese ID del alumno");
			EnrollmentStateController.changeByStudentId(connection, idStudent, EnrollmentState.cancelado);
		case 5:
			int id_course = InputUtil.inputInt(sc, "Ingrese ID del curso");
			String division = InputUtil.inputSingleWord(sc, "Ingrese comision");
			EnrollmentStateController.changeByCourseIdAndDivision(connection, id_course, division, EnrollmentState.activo);
			break;
		case 6:
			int id_course1 = InputUtil.inputInt(sc, "Ingrese ID del curso");
			String division1 = InputUtil.inputSingleWord(sc, "Ingrese comision");
			EnrollmentStateController.changeByCourseIdAndDivision(connection, id_course1, division1, EnrollmentState.cancelado);
			break;
		case 7:
			int courseId = InputUtil.inputInt(sc, "Ingrese ID del curso");
			EnrollmentStateController.changeByCourseId(connection, courseId, EnrollmentState.activo);
			break;
		case 8:
			int idCourse = InputUtil.inputInt(sc, "Ingrese ID del curso");
			EnrollmentStateController.changeByCourseId(connection, idCourse, EnrollmentState.cancelado);
			break;
		default:
			PrintUtil.invalidOptionMessage();
			break;
		}
	}

	
}
