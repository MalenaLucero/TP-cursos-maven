package base.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static int getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONDAY) + 1;
	}
	
	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getCurrentDay() {
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}
	
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static String getCurrentDateString() {
		return String.format("%s-%s-%s", getCurrentDay(), getCurrentMonth(), getCurrentYear());
	}
	
	public static long getTimestamp() {
		return new Date().getTime();
	}
	
	public static String getMonthString(int number) {
		String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
		return months[number - 1];
	}
}
