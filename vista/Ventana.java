package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import controlador.Conexion;

public class Ventana extends Frame {

    // Componentes de la interfaz gráfica
    private Label lblTitulo, lblIdPelicula, lblNombrePelicula, lblCategoria, lblPrecioEntrada, lblFechaEstreno;
    private TextField txtIdPelicula, txtNombrePelicula, txtPrecioEntrada, txtFechaEstreno;
    private Choice choiceCategoria;
    private Checkbox chkEnCartelera;
    private Button btnAgregar, btnConsultar, btnModificar, btnEliminar;
    private JTable tablaRegistrosPelicula;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;

    // Constructor de la ventana
    public Ventana() {
        setLayout(null);
        setSize(900, 400);
        setTitle("Gestión de Películas - MVC");
        setBackground(Color.LIGHT_GRAY);
        setLocationRelativeTo(null);
        configurarCamposDeEntradas();
        configurarBotonesDeEventos();
        configurarTablaDeDatos();
        probarConexion();
    }
    private void probarConexion() {
        try (Connection con = Conexion.getConnection()) {
            System.out.println("Conectado a MySQL");
            System.out.println(con);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }

    // Método de configuración para los campos de entrada
    private void configurarCamposDeEntradas() {
        lblTitulo = new Label("Sistema de Gestión de Películas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(50, 30, 500, 30);
        add(lblTitulo);

        lblIdPelicula = new Label("ID Película:");
        lblIdPelicula.setBounds(50, 80, 100, 20);
        add(lblIdPelicula);

        txtIdPelicula = new TextField();
        txtIdPelicula.setBounds(180, 80, 150, 20);
        add(txtIdPelicula);

        lblNombrePelicula = new Label("Nombre:");
        lblNombrePelicula.setBounds(50, 110, 100, 20);
        add(lblNombrePelicula);

        txtNombrePelicula = new TextField();
        txtNombrePelicula.setBounds(180, 110, 150, 20);
        add(txtNombrePelicula);

        lblCategoria = new Label("Categoría:");
        lblCategoria.setBounds(50, 140, 100, 20);
        add(lblCategoria);

        choiceCategoria = new Choice();
        choiceCategoria.add("Acción");
        choiceCategoria.add("Comedia");
        choiceCategoria.add("Drama");
        choiceCategoria.add("Terror");
        choiceCategoria.add("Ciencia Ficción");
        choiceCategoria.setBounds(180, 140, 150, 20);
        add(choiceCategoria);

        lblPrecioEntrada = new Label("Precio Entrada:");
        lblPrecioEntrada.setBounds(50, 170, 120, 20);
        add(lblPrecioEntrada);

        txtPrecioEntrada = new TextField();
        txtPrecioEntrada.setBounds(180, 170, 150, 20);
        add(txtPrecioEntrada);

        lblFechaEstreno = new Label("Fecha Estreno:");
        lblFechaEstreno.setBounds(50, 200, 120, 20);
        add(lblFechaEstreno);

        txtFechaEstreno = new TextField();
        txtFechaEstreno.setBounds(180, 200, 150, 20);
        add(txtFechaEstreno);

        chkEnCartelera = new Checkbox("En Cartelera");
        chkEnCartelera.setBounds(180, 230, 150, 20);
        add(chkEnCartelera);
    }

    // Método para configurar la tabla de datos
    private void configurarTablaDeDatos() {
        String[] columnas = {"ID", "Nombre", "Categoría", "Precio", "Fecha Estreno", "En Cartelera"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaRegistrosPelicula = new JTable(modeloTabla);
        scrollPane = new JScrollPane(tablaRegistrosPelicula);
        scrollPane.setBounds(350, 80, 500, 300);
        add(scrollPane);
    }

    // Método para configurar los botones y sus eventos
    private void configurarBotonesDeEventos() {
        btnAgregar = new Button("Agregar");
        btnAgregar.setBounds(50, 270, 100, 30);
        add(btnAgregar);

        btnConsultar = new Button("Consultar");
        btnConsultar.setBounds(50, 310, 100, 30);
        add(btnConsultar);

        btnModificar = new Button("Modificar");
        btnModificar.setBounds(180, 310, 100, 30);
        add(btnModificar);

        btnEliminar = new Button("Eliminar");
        btnEliminar.setBounds(180, 270, 100, 30);
        add(btnEliminar);
    }
    public Button getBtnAgregar(){
        return btnAgregar;
    }
    public Button getBtnConsultar(){
        return btnConsultar;
    }
    public Button getBtnModificar(){
        return btnModificar;
    }
    public Button getBtnEliminar(){
        return btnEliminar;
    }
    public TextField getTxtIdPelicula(){
        return txtIdPelicula;
    }
    public TextField getTxtNombrePelicula(){
        return txtNombrePelicula;
    }
    public Choice getChoiceCategoria(){
        return choiceCategoria;
    }
    public TextField getTxtPrecioEntrada(){
        return txtPrecioEntrada;
    }
    public TextField getTxtFechaEstreno(){
        return txtFechaEstreno;
    }
    public Checkbox getChkEnCartelera(){
        return chkEnCartelera;
    }
    public DefaultTableModel getModeloTabla(){
        return modeloTabla;
    }
    public JTable getTablaRegistrosPelicula() {
        return tablaRegistrosPelicula;
    }
    public void limpiarTodo(){
        txtIdPelicula.setText("");
        txtNombrePelicula.setText("");
        choiceCategoria.select(0);
        txtPrecioEntrada.setText("");
        txtFechaEstreno.setText("");
        chkEnCartelera.setState(false);
    }
}
