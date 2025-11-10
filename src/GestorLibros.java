import java.util.ArrayList;

public class GestorLibros {
    private ArrayList<Libro> libros;

    public GestorLibros(ArrayList<Libro> libros) {
        this.libros = (libros != null) ? libros : new ArrayList<>();
    }

    public GestorLibros() { this.libros = new ArrayList<>(); }

    public boolean agregarLibro(Libro libro) {
        if (libro == null) { System.out.println("Error: Libro nulo"); return false; }
        String nuevoIsbn = normalizarIsbn(libro.getIsbn());
        if (nuevoIsbn.isEmpty()) { System.out.println("Error: ISBN vacío"); return false; }
        for (Libro libroAux : libros) {
            if (normalizarIsbn(libroAux.getIsbn()).equals(nuevoIsbn)) {
                System.out.println("Error: ISBN ya existe"); return false;
            }
        }
        if (libro.getCantidad_biblioteca() <= 0) {
            System.out.println("Error: Cantidad en biblioteca debe ser mayor a 0"); return false;
        }
        if (libro.getCantidad_disponible() <= 0 ||
                libro.getCantidad_disponible() > libro.getCantidad_biblioteca()) {
            System.out.println("Error: Cantidad disponible debe ser > 0 y <= cantidad biblioteca");
            return false;
        }
        libros.add(libro);
        return true;
    }

    public boolean eliminarLibro(String isbn) {
        String objetivo = normalizarIsbn(isbn);
        for (int i = 0; i < libros.size(); i++) {
            if (normalizarIsbn(libros.get(i).getIsbn()).equals(objetivo)) {
                libros.remove(i);
                return true;
            }
        }
        System.out.println("Error: Libro no existe");
        return false;
    }

    public Libro buscarPorIsbn(String isbn) {
        String objetivo = normalizarIsbn(isbn);
        if (objetivo.isEmpty()) return null;
        for (Libro l : libros) {
            if (normalizarIsbn(l.getIsbn()).equals(objetivo)) return l;
        }
        return null;
    }

    public ArrayList<Libro> getLibros() { return libros; }

    private String normalizarIsbn(String s) {
        if (s == null) return "";
        return s.replaceAll("-", "").toUpperCase().trim();
    }
}
