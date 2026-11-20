package controlador;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

import modelo.Pelicula;
import vista.Ventana;

public class PeliculaControlador {

    private Ventana vista;

    public PeliculaControlador(Ventana vista){
        this.vista = vista;
        vista.getBtnConsultar().addActionListener(e->consultarPelicula());
        vista.getBtnAgregar().addActionListener(e->agregarPelicula());
        vista.getBtnModificar().addActionListener(e->actualizarPelicula());
        vista.getBtnEliminar().addActionListener(e->eliminarPelicula());
        mostrarPeliculas();
    }
    
    private void mostrarPeliculas(){
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
                    (rs.getInt("EnCartelera") == 1) ? "Si" : "No"
                };
                vista.getModeloTabla().addRow(fila);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void agregarPelicula(){
        if (vista.getTxtNombrePelicula().getText().isEmpty() ||
            vista.getTxtPrecioEntrada().getText().isEmpty() ||
            vista.getTxtFechaEstreno().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pueden haber campos vacios.");
            return;
        }
        try {
            if (Double.parseDouble(vista.getTxtPrecioEntrada().getText().trim()) <= 0){
                JOptionPane.showMessageDialog(null, "El precio debe ser mayor a 0.");
                return;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "El precio no puede ser una letra.");
            return;
        }
        try {
            Pelicula pelicula1 = new Pelicula(
                (int) (System.currentTimeMillis() / 1000),
                vista.getTxtNombrePelicula().getText().trim(), 
                vista.getChoiceCategoria().getSelectedItem().trim(),
                Double.parseDouble(vista.getTxtPrecioEntrada().getText().trim()),
                vista.getTxtFechaEstreno().getText().trim(),
                vista.getChkEnCartelera().getState()
            );
            String query = "INSERT INTO pelicula (idPelicula, NombrePelicula, Categoria, PrecioEntrada, FechaEstreno, EnCartelera)" +
                            "VALUES (?, ?, ?, ?, ?, ?);";
            try(Connection con = Conexion.getConnection();
                PreparedStatement ps = con.prepareStatement(query)){
                ps.setInt(1, pelicula1.getIdPelicula());
                ps.setString(2, pelicula1.getNombrepelicula());
                ps.setString(3, pelicula1.getCategoria());
                ps.setDouble(4, pelicula1.getPrecioEntrada());
                ps.setString(5, pelicula1.getFechaEstreno());
                ps.setInt(6, (pelicula1.isEnCartelera()) ? 1 : 0);

                int filas = ps.executeUpdate();

                if(filas > 0){
                    JOptionPane.showMessageDialog(null, "Pelicula Agregada");
                    mostrarPeliculas();
                    vista.limpiarTodo();
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
            System.out.println(pelicula1.resumenPelicula());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void actualizarPelicula(){
        if(vista.getTxtIdPelicula().getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecciona una pelicula.");
            return;
        }
        if (vista.getTxtNombrePelicula().getText().isEmpty() ||
            vista.getTxtPrecioEntrada().getText().isEmpty() ||
            vista.getTxtFechaEstreno().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No pueden haber campos vacios.");
            return;
        }
        if (Double.parseDouble(vista.getTxtPrecioEntrada().getText().trim()) <= 0){
            JOptionPane.showMessageDialog(null, "El precio debe ser mayor a 0.");
            return;
        }
        String query = "UPDATE pelicula SET NombrePelicula = ?, Categoria = ?, PrecioEntrada = ?, FechaEstreno = ?, EnCartelera = ? " +
                        "WHERE idPelicula = ?";
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setString(1, vista.getTxtNombrePelicula().getText().trim());
            ps.setString(2, vista.getChoiceCategoria().getSelectedItem());
            ps.setDouble(3, Double.parseDouble(vista.getTxtPrecioEntrada().getText().trim()));
            ps.setString(4, vista.getTxtFechaEstreno().getText().trim());
            ps.setInt(5, (vista.getChkEnCartelera().getState()) ? 1 : 0);
            ps.setInt(6, Integer.parseInt(vista.getTxtIdPelicula().getText().trim()));
            int filas = ps.executeUpdate();

            if(filas > 0){
                JOptionPane.showMessageDialog(null, "Pelicula Modificada");
                mostrarPeliculas();
                vista.limpiarTodo();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }       
    }
    private void consultarPelicula(){
        int fila = vista.getTablaRegistrosPelicula().getSelectedRow();
        if (fila == -1){
            JOptionPane.showMessageDialog(null, "Selecciona una pelicula.");
            return;
        }
        int id = (int) vista.getModeloTabla().getValueAt(fila, 0);
        String query = "SELECT * FROM pelicula WHERE IdPelicula = ?";
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    vista.getTxtIdPelicula().setText(String.valueOf(rs.getInt("IdPelicula")));
                    vista.getTxtNombrePelicula().setText(rs.getString("NombrePelicula"));
                    vista.getChoiceCategoria().select(rs.getString("Categoria"));
                    vista.getTxtPrecioEntrada().setText(String.valueOf(rs.getDouble("PrecioEntrada")));
                    vista.getTxtFechaEstreno().setText(rs.getString("FechaEstreno"));
                    vista.getChkEnCartelera().setState((rs.getInt("EnCartelera") == 1 ? true : false));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private void eliminarPelicula(){
        if(vista.getTxtIdPelicula().getText().equals("")){
            JOptionPane.showMessageDialog(null, "Selecciona una pelicula.");
            return;
        }
        String query = "DELETE FROM pelicula WHERE IdPelicula = ?";
        try(Connection con = Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(query)){
            ps.setInt(1, Integer.parseInt(vista.getTxtIdPelicula().getText().trim()));
            int filas = ps.executeUpdate();

            if(filas > 0){
                JOptionPane.showMessageDialog(null, "Pelicula Eliminada");
                mostrarPeliculas();
                vista.limpiarTodo();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }       
    }
}
