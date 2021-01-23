package base.menu;

import java.util.Scanner;

import base.util.InputUtil;

public class MainMenu {
	public static int showMenuAndInput(Scanner sc) {
		System.out.println();
		System.out.println("------ MENU PRINCIPAL ------");
		System.out.println("1. Cursos");
		System.out.println("2. Alumnos");
		System.out.println("3. Docentes");
		System.out.println("4. Inscripciones");
		System.out.println("5. Estado de inscripciones");
		System.out.println("6. Notas");
		System.out.println("99. Correr tests");
		System.out.println("0. Salir");
		return InputUtil.inputIntMenuOption(sc);
	}
}
