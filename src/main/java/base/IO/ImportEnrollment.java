package base.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import base.model.Enrollment;

public class ImportEnrollment {
	public static Enrollment importData() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader("enrollment.txt"));
		Map<String, String> valueMap = new HashMap<String, String>();
		String line = bufferedReader.readLine();
		while (line != null) {
			String key = line.substring(0, line.indexOf(':'));
			String value = line.substring(line.indexOf(' ') + 1);
			valueMap.put(key, value);
			line = bufferedReader.readLine();
		}
		Enrollment enrollment = new Enrollment(Integer.parseInt(valueMap.get("idCourse")),
				Integer.parseInt(valueMap.get("idStudent")), valueMap.get("enrollmentState"),
				Integer.parseInt(valueMap.get("idTeacher")), valueMap.get("division"), 
				Integer.parseInt(valueMap.get("grade1")), Integer.parseInt(valueMap.get("grade2")),
				valueMap.get("courseState"), Integer.parseInt(valueMap.get("year")));
		bufferedReader.close();
		return enrollment;
	}
}
