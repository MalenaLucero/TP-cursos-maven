package base.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import base.DAO.TeacherDAO;
import base.IO.ExportPaycheck;
import base.externalAPI.KanjiAPI;
import base.model.Course;
import base.model.Enrollment;
import base.model.Kanji;
import base.model.Teacher;
import base.util.DateUtil;
import base.util.PrintUtil;
import base.util.ResponseUtil;
import base.util.StringUtil;

public class TeacherController{
	public static void findAll(Connection connection) throws SQLException {
		System.out.println("Listado de docentes");
		List<Teacher> teachers = TeacherDAO.getAll(connection);
		printTeachers(teachers);
	}
	
	public static void findById(Connection connection, int id) throws SQLException {
		System.out.println("Buscar docente por ID");
		Teacher teacher = TeacherDAO.findById(connection, id);
		printTeacher(teacher);
	}
	
	public static void findByLastname(Connection connection, String name, String lastname) throws SQLException {
		System.out.println("Buscar docente por nombre y apellido");
		Teacher teacher = TeacherDAO.findByNameLastname(connection, name, lastname);
		printTeacher(teacher);
	}
	
	public static void insert(Connection connection, Teacher teacher) throws SQLException {
		System.out.println("Agregar docente");
		int res = TeacherDAO.insert(teacher, connection);
		ResponseUtil.addMessage(res);
	}

	public static void update(Connection connection, Teacher teacher) throws SQLException {
		System.out.println("Editar docente");
		int res = TeacherDAO.update(connection, teacher);
		ResponseUtil.editMessage(res);
	}
	
	public static void delete(Connection connection, int id) throws SQLException {
		System.out.println("Eliminar docente");
		int res = TeacherDAO.delete(id, connection);
		ResponseUtil.deleteMessage(res);
	}
	
	public static void findCoursesByTeacherId(Connection connection, int id_teacher) throws SQLException {
		System.out.println("Cursos del docente con ID " + id_teacher);
		List<Map<String, Object>> list = TeacherDAO.getCoursesByTeacher(connection, id_teacher);
		System.out.println(list.get(0).get("teacher"));
		for(Map<String, Object> map: list) {
			Course course = (Course) map.get("course");
			Enrollment enrollment = (Enrollment) map.get("enrollment");
			System.out.print("Materia: " + course.getName());
			System.out.print(", Comision: " + enrollment.getDivision());
			System.out.println();
		}
	}
	
	public static void findCompleteProfile(Connection connection, int id) throws SQLException {
		Teacher teacher = TeacherDAO.findById(connection, id);
		System.out.println("ID " + teacher.getId());
		System.out.println("Nombre: " + teacher.getName());
		System.out.println("Apellido: " + teacher.getLastname());
		PrintUtil.printIfNotBlank("Nombre alternativo: ", teacher.getAlternativeName1());
		PrintUtil.printIfNotBlank("Segundo nombre alternativo: ", teacher.getAlternativeName2());
		System.out.println("Buscando significado de los kanjis en API externa...");
		if(teacher.getAlternativeName2() == null) {
			findKanjiMeanigs(teacher.getAlternativeName1());
		} else {
			findKanjiMeanigs(teacher.getAlternativeName2());
		}
		PrintUtil.printIfNotBlank("Fecha de nacimiento: ", StringUtil.dateToString(teacher.getBirthdate()));
		if(teacher.getBirthdate() != null) {
			System.out.println("Edad: " + calculateAge(teacher.getBirthdate()) + " años");
		}
		PrintUtil.printIfNotBlank("Foto de perfil: ", teacher.getImage());
		if(!StringUtil.isBlank(teacher.getDescription())) {
			System.out.println("Descripcion:");
			System.out.println(teacher.getDescription());
		}
	}
	
	private static void findKanjiMeanigs(String kanjiName) {
		for(int i = 0; i < kanjiName.length(); i++) {
			String kanjiToSearch = Character.toString(kanjiName.charAt(i));
			Kanji kanji = KanjiAPI.getKanjiData(kanjiToSearch);
			System.out.println("Kanji: " + kanjiToSearch);
			if(kanji != null) {
				System.out.println("Significado: " + kanji.getMeanings());
			}
		}
	}
	
	public static void findBySimilarity(Connection connection, String searchString) throws SQLException {
		System.out.println("Docentes con nombre similar a " + searchString);
		List<Teacher> teachers = TeacherDAO.getBySimilarity(connection, searchString);
		printTeachers(teachers);
	}
	
	public static void exportPaycheck(Connection connection, int teacherId) throws SQLException {
		try {
			Teacher teacher = TeacherDAO.findById(connection, teacherId);
			if(teacher != null) {
				ExportPaycheck.generate(TeacherDAO.getCoursesByTeacher(connection, teacherId));
			} else {
				System.err.println("Docente no encontrado");
			}
			
		} catch (IOException e) {
			PrintUtil.printExceptionMessage("Error al generar el recibo de sueldo", e.getMessage());
		} 
	}
	
	public static void findNextBirthday(Connection connection) throws SQLException {
		PrintUtil.printMessage("Proximo cumpleaños:");
		int currentDay = DateUtil.getCurrentDay();
		int currentMonth = DateUtil.getCurrentMonth();
		List<Teacher> teachers = TeacherDAO.getMonthsBirthdays(connection, currentMonth);
		List<Teacher> nextBirthdays = new ArrayList<Teacher>();
		for(Teacher teacher: teachers) {
			if(currentDay <= DateUtil.getDay(teacher.getBirthdate())) {
				nextBirthdays.add(teacher);
			}
		}
		if(nextBirthdays.size() > 0) {
			printTeacherBirthday(nextBirthdays.get(0));
		} else if(nextBirthdays.size() == 0) {
			List<Teacher> nextMonths = findNextMonthsBirthdays(connection, currentMonth);
			printTeacherBirthday(nextMonths.get(0));
		}
	}
	
	public static void findCurrentMonthBirthdays(Connection connection) throws SQLException {
		PrintUtil.printMessage("Docentes que cumplen años este mes:");
		int currentMonth = DateUtil.getCurrentMonth();
		List<Teacher> teachers = TeacherDAO.getMonthsBirthdays(connection, currentMonth);
		if(teachers.size() == 0) {
			System.err.println("No hay cumpleaños este mes");
		} else {
			for(Teacher teacher: teachers) {
				printTeacherBirthday(teacher);
			}
		}
	}
	
	private static void printTeacher(Teacher teacher) {
		if(teacher == null) {
			System.err.println("No se encontro el docente");
		} else {
			System.out.println(teacher);
		}
	}
	
	private static void printTeachers(List<Teacher> teachers) {
		if(teachers.size() == 0) {
			System.err.println("No se encontraron docentes");
		} else {
			for(Teacher teacher: teachers) {
				System.out.println(teacher);
			}
		}
	}
	
	private static void printTeacherBirthday(Teacher teacher) {
		System.out.print(teacher.getName() + " ");
		System.out.print(teacher.getLastname() + " ");
		System.out.print(" Dia: " + DateUtil.getDay(teacher.getBirthdate()));
		System.out.println("-" + DateUtil.getMonth(teacher.getBirthdate()));
	}
	
	private static List<Teacher> findNextMonthsBirthdays(Connection connection, int month) throws SQLException {
		int nextMonth = month == 12 ? 1 : month + 1;
		List<Teacher> teachers = TeacherDAO.getMonthsBirthdays(connection, nextMonth);
		if(teachers.size() == 0) {
			return findNextMonthsBirthdays(connection, nextMonth);
		} else {
			return teachers;
		}
	}
	
	private static int calculateAge(Date birthdate) {
		if((DateUtil.getMonth(birthdate) < DateUtil.getCurrentMonth()) ||
			(DateUtil.getMonth(birthdate) == DateUtil.getCurrentMonth()
			&& DateUtil.getDay(birthdate) <= DateUtil.getCurrentDay())) {
			return DateUtil.getCurrentYear() - DateUtil.getYear(birthdate);
		} else {
			return DateUtil.getCurrentYear() - DateUtil.getYear(birthdate) - 1;
		}
	}
}
