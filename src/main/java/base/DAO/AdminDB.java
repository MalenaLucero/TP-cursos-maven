package base.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminDB {
	public static Connection obtenerConexion() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.cj.jdbc.Driver");
		//root: usuario
		//tercer parametro: contraseña
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cursos?serverTimezone=UTC", "root", "");
	}
}
