package base.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.model.Course;
import base.model.Enrollment;
import base.model.Student;
import base.util.DateUtil;
import base.util.MathUtil;

public class ExportGradesBooklet {
	public static void generate(List<Map<String, Object>> gradesList, int year) throws IOException{
		Student student = (Student)gradesList.get(0).get("student");
		String fileName = "boletin" + student.getNameAndLastnameNoSpace() + DateUtil.getTimestamp() + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		appendLine(writer, "BOLETIN DE CALIFICACIONES");
		writer.newLine();
		appendLine(writer, "Alumno: " + student.getFullName());
		appendLine(writer, "Ciclo lectivo: " + year);
		writer.newLine();
		
		String[] subjects = generateSubjectsArray(gradesList);
		int[] grade1Array = generateIntArrays(gradesList).get("grade1");
		int[] grade2Array = generateIntArrays(gradesList).get("grade2");
		int[] averageArray = generateIntArrays(gradesList).get("averageGrade");
		
		appendLine(writer, generateSubjectsString(subjects));
		appendLine(writer, "1° Cuatrimestre" + generateGradeString(subjects, grade1Array));
		appendLine(writer, "2° Cuatrimestre" + generateGradeString(subjects, grade2Array));
	    appendLine(writer, "Nota final     " + generateGradeString(subjects, averageArray));
	    writer.newLine();
	    appendLine(writer, "Promedio general: " + MathUtil.calculateAverage(averageArray));
	    writer.newLine();
	    writer.newLine();
	    appendLine(writer, "Fecha de generacion: " + DateUtil.getCurrentDateString());
	    writer.close();
	    System.out.println("Boletin exportado como " + fileName);
	}
	
	private static void appendLine(BufferedWriter writer, String line) throws IOException {
		writer.append(line);
		writer.newLine();
	}
	
	private static String[] generateSubjectsArray(List<Map<String, Object>> gradesList) {
		String[] subjects = new String[gradesList.size()];
		for(int i = 0; i < gradesList.size(); i++) {
			Course course = (Course) gradesList.get(i).get("course");
			subjects[i] = course.getName();
		}
		return subjects;
	}
	
	private static Map<String, int[]> generateIntArrays(List<Map<String, Object>> gradesList) {
		int[] grade1Array = new int[gradesList.size()];
		int[] grade2Array = new int[gradesList.size()];
		int[] averageArray = new int[gradesList.size()];
		Map<String, int[]> gradeMap = new HashMap<String, int[]>();
		for(int i = 0; i < gradesList.size(); i++) {
			Enrollment enrollment = (Enrollment) gradesList.get(i).get("enrollment");
			grade1Array[i] = enrollment.getGrade1();
			grade2Array[i] = enrollment.getGrade2();
			averageArray[i] = enrollment.getAverageGrade();
		}
		gradeMap.put("grade1", grade1Array);
		gradeMap.put("grade2", grade2Array);
		gradeMap.put("averageGrade", averageArray);
		return gradeMap;
	}
	
	private static String generateSubjectsString(String[] subjects) {
		String subjectsList = "               ";
		for(String subject: subjects) {
			subjectsList += (" | " + subject);
		}
		return subjectsList;
	}
	
	private static String generateGradeString(String[] subjects, int[] gradeArray) {
		String gradeString = "";
		for (int i = 0; i < gradeArray.length; i++) {
			int blanks = (subjects[i].length() - 1) / 2;
			String blankSpace1 = "";
			for (int j = 0; j < blanks; j++) {
				blankSpace1 += " ";
			}
			String blankSpace2 = (subjects[i].length() - 1) % 2 != 0 ? blankSpace1 + " " : blankSpace1;
			if(gradeArray[i] == 10) {
				gradeString += (" |" + blankSpace1 + gradeArray[i] + blankSpace2);
			} else {
				gradeString += (" | " + blankSpace1 + gradeArray[i] + blankSpace2);
			}
		}
		return gradeString;
	}
}
