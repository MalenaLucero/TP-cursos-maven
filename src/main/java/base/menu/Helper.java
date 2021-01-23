package base.menu;

import java.util.Scanner;

import base.util.InputUtil;
import base.util.PrintUtil;

public class Helper {
	public static boolean confirmEditMessage(Scanner sc, String item, String value) {
		String message = String.format("¿Desea editar el campo %s? (y/n) Valor actual: %s", item, value);
		PrintUtil.printMessage(message);
		String res = sc.next();
		if(res.equalsIgnoreCase("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean confirmOptionalField(Scanner sc, String field) {
		String res = InputUtil.inputSingleWord(sc, "¿Desea cargar el campo opcional " + field + "? (y/n)");
		if(res.equalsIgnoreCase("y")) {
			return true;
		} else {
			return false;
		}
	}
}
