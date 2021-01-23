package base.util;

public class ResponseUtil {
	public static void addMessage(int res) {
		actionMessage("Se agrego el elemento", res);
	}
	
	public static void editMessage(int res) {
		actionMessage("Se realizo la modificacion", res);
	}
	
	public static void deleteMessage(int res) {
		actionMessage("Se elimino el elemento", res);
	}
	
	public static void actionMessage(String message, int res) {
		if(res == 1) {
			System.out.println(message);
		} else{
			System.err.println("No " + message.toLowerCase());
		}
	}
	
	public static void numberOfModifiedElements(int res) {
		if(res == 0) {
			System.err.println("No se realizaron cambios");
		} else if (res == 1) {
			System.out.println("Se modifico 1 elemento");
		} else {
			System.out.println("Se modificaron " + res + " elementos");
		}
	}
}
