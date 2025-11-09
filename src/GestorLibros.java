import java.util.ArrayList;

public class GestorLibros {
    private ArrayList<Libro> libros;

    public GestorLibros(ArrayList<Libro> libros) {
        this.libros = new ArrayList<>();
    }

    public boolean agregarLibro(Libro libro) {
        for (Libro libroAux : libros){
            if (libroAux.getIsbn().equals(libro.getIsbn())){
                return false;
            }
        }
        libros.add(libro);
        return true;
    }

    public boolean eliminarLibro(String isbn) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getIsbn().equals(isbn)) {
                libros.remove(i);
                return true;
            }
        }
        return false;
    }

    public Libro buscarLibro(String isbn) {
        for (Libro libro : libros) {
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
}
