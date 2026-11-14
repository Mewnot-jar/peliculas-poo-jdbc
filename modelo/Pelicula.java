package modelo;

public class Pelicula {            
    private int idPelicula;
    private String Nombrepelicula;  
    private String categoria;
    private double precioEntrada;
    private String fechaEstreno;    
    private boolean enCartelera;

    public Pelicula (int idPelicula, String Nombrepelicula, String categoria, double precioEntrada, String fechaEstreno, boolean enCartelera) {
        this.idPelicula = idPelicula;
        this.Nombrepelicula = Nombrepelicula;
        this.categoria = categoria;
        this.precioEntrada = precioEntrada;
        this.fechaEstreno = fechaEstreno;
        this.enCartelera = enCartelera;
    }

    public int getIdPelicula() {
        return idPelicula;
    }
    public void setIdPelicula(int idPelicula) {
        this.idPelicula = idPelicula;
    }
    public String getNombrepelicula() {
        return Nombrepelicula;
    }

    public void setNombrepelicula(String Nombrepelicula) {
        this.Nombrepelicula = Nombrepelicula;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public double getPrecioEntrada() {
        return precioEntrada;
    }
    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }
    public String getFechaEstreno() {
        return fechaEstreno;
    }
    public void setFechaEstreno(String fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
    public boolean isEnCartelera() {
        return enCartelera;
    }
    public void setEnCartelera(boolean enCartelera) {
        this.enCartelera = enCartelera;
    }
    public String resumenPelicula() {
        return "ID: " + idPelicula + ", Nombre: " + Nombrepelicula + ", Categoría: " + categoria +
               ", Precio Entrada: " + precioEntrada + ", Fecha Estreno: " + fechaEstreno +
               ", En Cartelera: " + (enCartelera ? "Sí" : "No");
    }
}