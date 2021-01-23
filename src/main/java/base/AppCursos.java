package base;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

import base.DAO.AdminDB;
import base.controller.BirthdayController;
import base.menu.CourseMenu;
import base.menu.EnrollmentMenu;
import base.menu.EnrollmentStateMenu;
import base.menu.GradeMenu;
import base.menu.TeacherMenu;
import base.menu.MainMenu;
import base.menu.StudentMenu;
import base.test.CourseTest;
import base.test.EnrollmentStateTest;
import base.test.EnrollmentTest;
import base.test.GradeTest;
import base.test.StudentTest;
import base.test.TeacherTest;
import base.util.PrintUtil;

//1. ABML de profes
//2. Inscripcion y cancelacion de inscripcion -> columna estado_inscripcion 1 Activo 0:Cancelado
// Inscripcion y cancelacion por Id de estudiante y Id de curso
//3. Agregar 2 notas a la tabla inscripcion (int)
//4. Agregar columna id_profesor en la inscripcion
//5. Agregar columna comision (varchar) en la inscripcion
//5. 2 Listados de inscripcion:
//a - esutiantes de un curso seleccionado (a eleccion)
//b - notas de un estudiante en un curso

public class AppCursos {
	public static void main(String[] args) {
		System.out.println("Bienvenido al sistema de cursos");
		Scanner sc = new Scanner(System.in);
		int option = MainMenu.showMenuAndInput(sc);
		
		try {
			PrintUtil.printMessage("Conectando a la base de datos...");
			Connection connection = AdminDB.obtenerConexion();
			System.out.println("Conexion establecida");
			
			while(option != 0) {
				try {
					switch(option) {
					case 1:
						course(sc, connection);
						break;
					case 2: 
						student(sc, connection);
						break;
					case 3:
						teacher(sc, connection);
						break;
					case 4:
						enrollment(sc, connection);
						break;
					case 5:
						enrollmentState(sc, connection);
						break;
					case 6:
						grade(sc, connection);
						break;
					case 99:
						runTests(connection);
						break;
					default:
						PrintUtil.invalidOptionMessage();
						break;
					}
				} catch(SQLException e) {
					PrintUtil.printExceptionMessage("Error al impactar en la base de datos", e.getMessage());
				} catch(Exception e) {
					PrintUtil.printExceptionMessage("Error inesperado", e.getMessage());
				}
				option = MainMenu.showMenuAndInput(sc);
			}
		} catch(Exception e) {
			PrintUtil.printExceptionMessage("No se pudo establecer la conexión", e.getMessage());
		}
		PrintUtil.printMessage("Programa finalizado");
	}

	private static void course(Scanner sc, Connection connection) throws SQLException {
		int option = CourseMenu.showMenuAndInput(sc);
		while(option != 0) {
			CourseMenu.chooseMenuOption(sc, connection, option);
			option = CourseMenu.showMenuAndInput(sc);
		}
	}
	
	private static void student(Scanner sc, Connection connection) throws SQLException {
		int option = StudentMenu.showMenuAndInput(sc);
		while(option != 0) {
			StudentMenu.chooseMenuOption(sc, connection, option);
			option = StudentMenu.showMenuAndInput(sc);
		}
	}
	
	private static void enrollment(Scanner sc, Connection connection) throws SQLException {
		int option = EnrollmentMenu.showMenuAndInput(sc);
		while(option != 0) {
			EnrollmentMenu.chooseMenuOption(sc, connection, option);
			option = EnrollmentMenu.showMenuAndInput(sc);
		}
	}
	
	private static void teacher(Scanner sc,Connection connection) throws SQLException, ParseException {
		BirthdayController.showBirthdays(connection);
		int option = TeacherMenu.showMenuAndInput(sc);
		while(option != 0) {
			TeacherMenu.chooseMenuOption(sc, connection, option);
			option = TeacherMenu.showMenuAndInput(sc);
		}
	}
	
	private static void enrollmentState(Scanner sc, Connection connection) throws SQLException {
		int option = EnrollmentStateMenu.showMenuAndInput(sc);
		while(option != 0) {
			EnrollmentStateMenu.chooseMenuOption(sc, connection, option);
			option = EnrollmentStateMenu.showMenuAndInput(sc);
		}
	}
	
	private static void grade(Scanner sc, Connection connection) throws SQLException {
		int option = GradeMenu.showMenuAndInput(sc);
		while(option != 0) {
			GradeMenu.chooseMenuOption(sc, connection, option);
			option = GradeMenu.showMenuAndInput(sc);
		}
	}
	
	private static void runTests(Connection connection) throws SQLException {
		CourseTest.testCrud(connection);
		StudentTest.testCrud(connection);
		TeacherTest.testCrud(connection);
		EnrollmentTest.testCrud(connection);
		EnrollmentTest.testStudentsSearch(connection);
		EnrollmentStateTest.testStateChange(connection);
		GradeTest.test(connection);
	}
}
