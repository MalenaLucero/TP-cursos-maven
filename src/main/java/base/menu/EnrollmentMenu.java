package base.menu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import base.DAO.EnrollmentDAO;
import base.controller.EnrollmentController;
import base.enums.EnrollmentState;
import base.model.Enrollment;
import base.util.InputUtil;
import base.util.PrintUtil;

public class EnrollmentMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU DE INSCRIPCION ------");
		System.out.println("1. Listar inscripciones");
		System.out.println("2. Buscar inscripcion por ID");
		System.out.println("3. Buscar inscripcion por curso y alumno");
		System.out.println("4. Agregar inscripcion");
		System.out.println("5. Modificar inscripcion");
		System.out.println("6. Eliminar inscripcion");
		System.out.println("7. Listar alumnos por curso");
		System.out.println("8. Listar alumnos por curso y comision");
		System.out.println("9. Importar inscripcion");
		System.out.println("0. Volver al menu principal");
		return InputUtil.inputIntMenuOption(sc);
	}
	
	public static void chooseMenuOption(Scanner sc, Connection connection, int option) throws SQLException {
		switch(option) {
		case 1:
			EnrollmentController.findAll(connection);
			break;
		case 2:
			int id = InputUtil.inputInt(sc, "Ingrese ID de la inscripcion:");
			EnrollmentController.findById(connection, id);
			break;
		case 3:
			int id_course = InputUtil.inputInt(sc, "Ingrese ID del curso:");
			int id_student = InputUtil.inputInt(sc, "Ingrese ID del alumno:");
			EnrollmentController.findByCourseIdAndStudentId(connection, id_course, id_student);
			break;
		case 4:
			Enrollment enrollment = createEnrollment(sc);
			EnrollmentController.insert(connection, enrollment);
			break;
		case 5:
			Enrollment editEnrollment = editEnrollment(sc, connection);
			EnrollmentController.update(connection, editEnrollment);
			break;
		case 6:
			int deleteId = InputUtil.inputInt(sc, "Ingrese ID de la inscripcion:");
			EnrollmentController.delete(connection, deleteId);
			break;
		case 7:
			int courseId = InputUtil.inputInt(sc, "Ingrese ID del curso:");
			EnrollmentController.findStudentsByCourseId(connection, courseId);
			break;
		case 8:
			int idCourse = InputUtil.inputInt(sc, "Ingrese ID del curso:");
			String division = InputUtil.inputSingleWord(sc, "Ingrese la comision");
			EnrollmentController.findStudentsByCourseIdAndDivision(connection, idCourse, division);
			break;
		case 9:
			EnrollmentController.insertImport(connection);
			break;
		default:
			PrintUtil.invalidOptionMessage();
			break;
		}
	}

	private static Enrollment createEnrollment(Scanner sc) {
		int id_course = InputUtil.inputInt(sc, "Ingrese ID del curso");
		int id_student = InputUtil.inputInt(sc, "Ingrese ID del alumno");
		Enrollment enrollment = new Enrollment(id_course, id_student);
		if(Helper.confirmOptionalField(sc, "comision y docente")) {
			String division = InputUtil.inputSingleWord(sc, "Ingresar la comision");
			enrollment.setDivision(division.toUpperCase());
			int id_docente = InputUtil.inputInt(sc, "Ingresar ID del docente");
			enrollment.setIdTeacher(id_docente);
		}
		if(Helper.confirmOptionalField(sc, "estado de inscripcion (valor por default: activo)")) {
			int state = InputUtil.inputInt(sc, "Ingrese 1 para cargar estado de inscripcion como CANCELADO");
			if(state == 1) enrollment.setEnrollmentState(EnrollmentState.cancelado.getValue());
		}
		if(Helper.confirmOptionalField(sc, "notas")) {
			int grade1 = InputUtil.inputInt(sc, "Ingrese la nota 1");
			int grade2 = InputUtil.inputInt(sc, "Ingrese la nota 2");
			enrollment.setGrade1(grade1);
			enrollment.setGrade2(grade2);
		}
		if(Helper.confirmOptionalField(sc, "ciclo lectivo (valor por default: 2021)")) {
			int year = InputUtil.inputInt(sc, "Ingrese el ciclo lectivo");
			enrollment.setYear(year);
		}
		return enrollment;
	}

	private static Enrollment editEnrollment(Scanner sc, Connection connection) throws SQLException {
		int id = InputUtil.inputInt(sc, "Ingrese ID de la inscripcion a editar:");
		Enrollment enrollment = EnrollmentDAO.findById(connection, id);
		if(Helper.confirmEditMessage(sc, "curso", Integer.toString(enrollment.getIdCourse()))) {
			int id_course = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setIdCourse(id_course);
		}
		if(Helper.confirmEditMessage(sc, "alumno", Integer.toString(enrollment.getIdStudent()))) {
			int id_student = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setIdStudent(id_student);
		}
		if(Helper.confirmEditMessage(sc, "estado de inscripcion", enrollment.getEnrollmentState())) {
			String state = InputUtil.inputSingleWord(sc, "Ingrese el nuevo valor");
			enrollment.setEnrollmentState(state.toLowerCase());
		}
		if(Helper.confirmEditMessage(sc, "docente", Integer.toString(enrollment.getIdTeacher()))) {
			int id_teacher = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setIdTeacher(id_teacher);
		}
		if(Helper.confirmEditMessage(sc, "comision", enrollment.getDivision())) {
			String division = InputUtil.inputLine(sc, "Ingrese el nuevo valor:");
			enrollment.setDivision(division.toUpperCase());
		}
		if(Helper.confirmEditMessage(sc, "nota 1", Integer.toString(enrollment.getGrade1()))) {
			int grade1 = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setGrade1(grade1);
		}
		if(Helper.confirmEditMessage(sc, "nota 2", Integer.toString(enrollment.getGrade2()))) {
			int grade2 = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setGrade2(grade2);
		}
		if(Helper.confirmEditMessage(sc, "ciclo lectivo", Integer.toString(enrollment.getYear()))) {
			int year = InputUtil.inputInt(sc, "Ingrese el nuevo valor");
			enrollment.setYear(year);
		}
		return enrollment;
	}
}
