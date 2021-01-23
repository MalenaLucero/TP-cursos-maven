package base.util;

public class PrintUtil {
	public static void printMessage(String message) {
		System.out.println();
		System.out.println(message);
	}
	
	public static void invalidOptionMessage() {
		System.out.println();
		System.out.println("Opcion invalida. Ingresela de nuevo.");
	}
	
	public static void printIfNotBlank(String commonMessage, String blank) {
		if(!StringUtil.isBlank(blank)) {
			System.out.println(commonMessage + blank);
		}
	}
	
	public static void printExceptionMessage(String customMessage, String exceptionMessage) {
		System.err.println(customMessage);
		System.out.println("Error: " + exceptionMessage);
	}
}
