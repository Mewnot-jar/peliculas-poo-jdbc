package test;

import controlador.Conexion;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class ConexionTest {
    @Test
    public void testConexionNoEsNula() throws SQLException {
        try (Connection con = Conexion.getConnection()){
            assertNotNull("La conexion devolvio null", con);
        }
    }
    @Test
    public void textConexionAbiertaYValida() throws SQLException{
        try (Connection con = Conexion.getConnection()){
            assertFalse("La conexion esta cerrada", con.isClosed());
            assertTrue("La conexion no respondio en 2 segundos", con.isValid(2));
        }
    }
    @Test
    public void testTablaPeliculaExiste() throws SQLException{
        try (Connection con = Conexion.getConnection()){
            DatabaseMetaData meta = con.getMetaData();
            try (ResultSet rs = meta.getTables("cinepelicula", null, "pelicula", null)){
                assertTrue("No se encontro la tabla 'pelicula'", rs.next());
            }
        }
    }
    @Test
    public void testConexionSeCierra() throws SQLException{
        Connection con;
        try (Connection c = Conexion.getConnection()){
            con = c;
        }
        assertTrue("La conexion quedo abierta", con.isClosed());
    }
    @Test(expected = SQLException.class)
    public void testCredencialesIncorrectasExcp() throws SQLException{
        DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/cinepelicula",
            "root",
            "claveIncorrecta"
        );
    }
}
