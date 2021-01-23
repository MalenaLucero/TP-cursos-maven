package base.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.controller.GradeController;
import base.util.InputUtil;
import base.util.PrintUtil;

public class GradeMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU DE NOTAS ------");
		System.out.println("1. Ver notas de alumno por curso");
		System.out.println("2. Ver todas las notas de un alumno");
		System.out.println("3. Ver todas las notas de un curso");
		System.out.println("4. Ver mejores promedios del instituto");
		System.out.println("5. Ver mejores promedios por curso");
		System.out.println("6. Generar boletin de alumno");
		System.out.println("0. Volver al menu principal");
		return InputUtil.inputIntMenuOption(sc);
	}
	
	public static void chooseMenuOption(Scanner sc, Connection connection, int option) throws SQLException {
		switch(option) {
		case 1:
			int id_student = InputUtil.inputInt(sc, "Ingrese ID del alumno");
			int id_course = InputUtil.inputInt(sc, "Ingrese ID del curso");
			GradeController.findByStudentIdAndCourseId(connection, id_student, id_course);
			break;
		case 2: 
			int idStudent = InputUtil.inputInt(sc, "Ingrese ID del alumno");
			GradeController.findByStudentId(connection, idStudent);
			break;
		case 3:
			int idCourse = InputUtil.inputInt(sc, "Ingrese ID del curso");
			GradeController.findByCourseId(connection, idCourse);
			break;
		case 4:
			GradeController.findOverallBestAverage(connection);
			break;
		case 5:
			int courseId = InputUtil.inputInt(sc, "Ingrese ID del curso");
			GradeController.findBestAverageByCourseId(connection, courseId);
			break;
		case 6: 
			int studentId = InputUtil.inputInt(sc, "Ingrese ID del alumno");
			int year = InputUtil.inputInt(sc, "Ingrese el año de cursada");
			GradeController.exportGradesBooklet(connection, studentId, year);
			break;
		default:
			PrintUtil.invalidOptionMessage();
			break;
		}
	}
}
