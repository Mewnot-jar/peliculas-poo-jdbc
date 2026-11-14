package vista;

import controlador.PeliculaControlador;

public class Main {
    public static void main(String[] args) {
        Ventana miVentana = new Ventana();
        new PeliculaControlador(miVentana);
        miVentana.setVisible(true);        
    }
}
