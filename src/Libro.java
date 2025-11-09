public class Libro
{
    String isbn;
    String titulo;
    String autor;
    Integer cantidad_biblioteca;
    Integer cantidad_disponible;
    String Imagen;

    public Libro(String isbn, String titulo, String autor, Integer cantidad_biblioteca, Integer cantidad_disponible, String imagen) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.cantidad_biblioteca = cantidad_biblioteca;
        this.cantidad_disponible = cantidad_disponible;
        Imagen = imagen;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getCantidad_biblioteca() {
        return cantidad_biblioteca;
    }

    public void setCantidad_biblioteca(Integer cantidad_biblioteca) {
        this.cantidad_biblioteca = cantidad_biblioteca;
    }

    public Integer getCantidad_disponible() {
        return cantidad_disponible;
    }

    public void setCantidad_disponible(Integer cantidad_disponible) {
        cantidad_disponible = cantidad_disponible;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public boolean validar_cantidad_biblioteca(Integer cantidad_biblioteca){
        if (cantidad_biblioteca <= 0) {
            System.out.println("Error: La cantidad debe ser mayor a 0.");
            return false;
        }
        return true;
    }

    public boolean validar_cantidad_disponible(Integer cantidad_disponible, Integer cantidad_biblioteca){
        if (cantidad_disponible <= 0 || cantidad_disponible > cantidad_biblioteca) {
            System.out.println("Error: debe ser > 0 y <= cantidad en biblioteca");
            return false;
        }
        return true;
    }

}
