package base.IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import base.model.Course;
import base.model.Teacher;
import base.util.DateUtil;

public class ExportPaycheck {
	private final static int CELL_WIDTH = 25;
	private final static int SUBJECT_PAY = 5700;
	
	public static void generate(List<Map<String, Object>> list) throws IOException {
		Teacher teacher = (Teacher) list.get(0).get("teacher");
		String fileName = "sueldo" + teacher.getNameAndLastnameNoSpace() + DateUtil.getTimestamp() + ".txt";
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		appendLine(writer, "RECIBO DE SUELDO");
		writer.newLine();
		appendLine(writer, "Nombre: " + teacher.getFullName());
		appendLine(writer, "Categoria: docente");
		appendLine(writer, "Periodo de pago: " + DateUtil.getMonthString(DateUtil.getCurrentMonth()) + " " + DateUtil.getCurrentYear());
		writer.newLine();
		appendLine(writer, generateTableTitle());
		
		String[] subjects = getSubjectsArray(list);
		int totalPay = 0;
		for(int i = 0; i < subjects.length; i++) {
			appendLine(writer, generateSubjectRow(subjects[i]));
			totalPay += SUBJECT_PAY;
		}
		
		double[] totalDeduction = new double[1];
		appendLine(writer, generateDeductionRow("Jubilación", 11, totalPay, totalDeduction));
		appendLine(writer, generateDeductionRow("Ley 19.032 INSSJP", 3, totalPay, totalDeduction));
		appendLine(writer, generateDeductionRow("Obra social", 3, totalPay, totalDeduction));
		writer.newLine();
		appendLine(writer, "Total bruto: " + totalPay);
		appendLine(writer, "Total de descuentos: " + totalDeduction[0]);
		appendLine(writer, "Total neto: " + Double.toString((double)totalPay - totalDeduction[0]));
		writer.close();
		System.out.println("Recibo de sueldo exportado como " + fileName);
	}
	
	private static String generateDeductionRow(String type, int percentage, int total, double[] totalDeduction) {
		String column1 = generateTableCell(type);
		String column2 = generateTableCell("");
		String column3 = generateTableCell("");
		double deduction = (double)total / 100 * percentage; 
		totalDeduction[0] += deduction;
		String column4 = generateTableCell(Double.toString(deduction));
		return column1 + column2 + column3 + column4;
	}
	
	private static String generateSubjectRow(String subject) {
		String column1 = generateTableCell(subject);
		String column2 = generateTableCell(Integer.toString(SUBJECT_PAY));
		return column1 + column2;
	}
	
	private static String[] getSubjectsArray(List<Map<String, Object>> list) {
		String[] subjects = new String[list.size()];
		for(int i = 0; i < list.size(); i++) {
			Course course = (Course) list.get(i).get("course");
			subjects[i] = course.getName();
		}
		return subjects;
	}
	
	private static String generateTableTitle() {
		String column1 = generateTableCell("CONCEPTO");
		String column2 = generateTableCell("SUJETO A RETENCION");
		String column3 = generateTableCell("EXENTO");
		String column4 = generateTableCell("DESCUENTOS");
		return column1 + column2 + column3 + column4;
	}
	
	private static String generateTableCell(String title) {
		return title + generateBlankString(CELL_WIDTH - title.length());
	}
	
	private static String generateBlankString(int number) {
		String blankString = "";
		for (int i = 0; i < number; i++) {
			blankString += " ";
		}
		return blankString;
	}
	
	private static void appendLine(BufferedWriter writer, String line) throws IOException {
		writer.append(line);
		writer.newLine();
	}
}
