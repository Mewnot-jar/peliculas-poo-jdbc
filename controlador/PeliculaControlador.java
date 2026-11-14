package controlador;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import vista.Ventana;

public class PeliculaControlador {

    private Ventana vista;

    public PeliculaControlador(Ventana vista){
        this.vista = vista;
        vista.getBtnConsultar().addActionListener(e->consultarPeliculas());
    }
    
    private void consultarPeliculas(){
        String query = "SELECT * FROM pelicula";
        try
        (
            Connection con = Conexion.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
        ){
            vista.getModeloTabla().setRowCount(0);
            while(rs.next()){
                Object[] fila = {
                    rs.getInt("IdPelicula"),
                    rs.getString("NombrePelicula"),
                    rs.getString("Categoria"),
                    rs.getInt("PrecioEntrada"),
                    rs.getString("FechaEstreno"),
                    rs.getInt("EnCartelera")
                };
                vista.getModeloTabla().addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
