package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String Url = "jdbc:mysql://localhost:3306/cinepelicula";
    private static String usuario = "root";
    private static String pass = "teodiowindows123";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(Url, usuario, pass);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            throw e;
        }
    }
}
