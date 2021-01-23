package base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class InputUtil {
	public static int inputInt(Scanner sc, String message) {
		PrintUtil.printMessage(message);
		int input = -1;
		while(input < 0) {
			try {
				input = sc.nextInt();
			} catch(Exception e) {
				System.err.println("Datos ingresados invalidos.");
				sc.next();
				input = inputInt(sc, message);
			}
		}
		return input;
	}
	
	public static int inputIntMenuOption(Scanner sc) {
		return inputInt(sc, "Ingrese una opcion");
	}
	
	public static String inputSingleWord(Scanner sc, String message) {
		PrintUtil.printMessage(message);
		return sc.next();
	}
	
	public static String inputLine(Scanner sc, String message) {
		PrintUtil.printMessage(message);
		String input = null;
		while(input == null || input.length() == 0) {
			input = sc.nextLine();
		}
		return input;
	}
	
	public static Date inputDate(Scanner sc, String message, String format) {
		String input = InputUtil.inputSingleWord(sc, message + " en formato " + format);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(input);
		} catch (ParseException e) {
			System.err.println("Formato invalido de fecha");
			date = inputDate(sc, message, format);
		}
		return date;
	}
}
