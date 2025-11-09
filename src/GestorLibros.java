import java.util.ArrayList;

public class GestorLibros {
    private ArrayList<Libro> libros;

    public GestorLibros(ArrayList<Libro> libros) {
        this.libros = new ArrayList<>();
    }

    public boolean agregarLibro(Libro libro) {
        for (Libro libroAux : libros){
            if (libroAux.getIsbn().equals(libro.getIsbn())){
                System.out.println("Error: ISBN ya existe");
                return false;
            }
        }

        if (libro.getCantidad_biblioteca() <= 0) {
            System.out.println("Error: Cantidad en biblioteca debe ser mayor a 0");
            return false;
        }

        if (libro.getCantidad_disponible() <= 0 || libro.getCantidad_disponible() > libro.getCantidad_biblioteca()) {
            System.out.println("Error: Cantidad disponible debe ser > 0 y <= cantidad biblioteca");
            return false;
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
        System.out.println("Error: Libro no existe");
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
